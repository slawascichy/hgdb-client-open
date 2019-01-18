package io.hgdb.mercury.client.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import pl.slawas.common.cache.CacheConstants;
import pl.slawas.common.cache.CacheProviderEnum;
import pl.slawas.common.cache.CacheProviderFactory;
import pl.slawas.common.cache.CacheUsage;
import pl.slawas.common.cache.IObjectCache;
import pl.slawas.common.cache.IObjectCacheProvider;
import pl.slawas.common.cache.IObjectCacheStatistics;
import pl.slawas.common.cache.ehcache.EhCache;
import pl.slawas.common.cache.ehcache.EhCacheConfig;
import pl.slawas.entities.NameValuePair;
import pl.slawas.helpers.Configurations;
import pl.slawas.twl4j.Logger;
import pl.slawas.twl4j.LoggerFactory;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.entities.MEntityCacheConfig;
import pro.ibpm.mercury.entities.cache.IStatsInfoRecord;
import pro.ibpm.mercury.entities.cache.StatsInfoRecord;
import pro.ibpm.mercury.entities.helpers.EntityHelper;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;

/**
 * 
 * CacheHelper - klasa pomocnicza obsługująca pamięć podręczną.
 * 
 * @author Sławomir Cichy &lt;slawas@slawas.pl&gt;
 * @version $Revision: 1.1 $
 * 
 */
public class CacheHelper {

	private static final Logger logger = LoggerFactory.getLogger(CacheHelper.class);

	/** Nazwa domyślnego regionu pamięci podręcznej */
	public static final String DEFAULT_REGION_NAME = "MercuryCore";
	public static final String DEFAULT_CACHE_USAGE = CacheUsage.TO_USE.name();
	/**
	 * Domyślny czas przechowywania elementów w pamięci podręcznej ustawiony na 24
	 * godziny.
	 */
	public static final int DEFAULT_TIME_TO_LIVE = 3600 * 24;
	public static final String DYNAMIC_PARAMETERS_FILE_PATH = CacheConstants.PROP_DYNAMIC_PARAMETERS_FILE_PATH;

	public static final String PROP_TIME_TO_LIVE = "cache.timeToLive";
	/**
	 * Definicja atrybutu zawierającego informację o pełnej ścieżce do konfiguracji
	 * pamięci podręcznej, który jest wykorzystywany do serwowanie wartości
	 * uniwersalnych atrybutów.
	 * 
	 * @see AttributeTypeParameter
	 */
	public static final String PROP_CONFIG_PATH = CacheConstants.PROP_CONFIG_PATH;
	public static final String PROP_PROVIDER = CacheConstants.PROP_PROVIDER;
	public static final String PROP_USE_DEFAULT_REGION = CacheConstants.PROP_USE_DEFAULT_REGION;
	private static EhCacheConfig cacheConfig;

	/**
	 * Nazwa provider-a pamięci podręcznej atrybutów, domyślna wartość
	 * {@link CacheProviderEnum#none#toString()}
	 */
	public static final String CACHE_PROVIDER = (MercuryConfig.getInstance().get(PROP_PROVIDER) == null ? "none"
			: MercuryConfig.getInstance().get(PROP_PROVIDER));

	private static void addCacheParam(EhCacheConfig cc, String propertyCode, String value) {
		if (cc.get(propertyCode) != null) {
			logger.warn("[CacheConfig] Parametr {} jest już ustawiony: '{}'!",
					new Object[] { propertyCode, cc.get(propertyCode) });

		} else {
			cc.put(propertyCode, value);
		}
	}

	static {
		/** Załadowanie parametrów obsługi pamięci podręcznych - START */
		cacheConfig = EhCacheConfig.getInstance();
		/* Parametry predefiniowane w głównym pliku konfiguracyjnym */
		List<NameValuePair> mercuryProperties = MercuryConfig.getInstance().getPropertyList();
		for (NameValuePair nvp : mercuryProperties) {
			String propertyCode = nvp.getName();
			String value = nvp.getValue();
			addCacheParam(cacheConfig, propertyCode, value);
		}
		/*
		 * Parametry z osobnego pliku konfiguracyjnego - nadpisują parametry z pliku
		 * głównego
		 */
		String cachePropertiesFileName = MercuryConfig.getInstance().get(DYNAMIC_PARAMETERS_FILE_PATH);
		if (StringUtils.isNotBlank(cachePropertiesFileName)) {
			Map<String, String> cacheProps = null;
			cacheProps = Configurations.loadHashtable(MercuryConfig.class, cachePropertiesFileName);
			if (cacheProps != null && !cacheProps.isEmpty()) {
				for (Entry<String, String> entry : cacheProps.entrySet()) {
					String propertyCode = (String) entry.getKey();
					String value = (String) entry.getValue();
					addCacheParam(cacheConfig, propertyCode, value);
				}
			}
		}
		/* Ustawienie parametru systemowego konfiguracji statycznej */
		EhCacheConfig.setSystemPropConfigPath(logger, cacheConfig);
		/** Załadowanie parametrów obsługi pamięci podręcznych - KONIEC */
	}

	private CacheHelper() {
	}

	/**
	 * Czyści pamięci podręczne powiązane z encją.
	 * 
	 * Na chwilę obecną pamięci podręczne zapytań NamedQuery powiązane z encją (
	 * MCase2Case.NamedQueryCacheName ) oraz pamięci podręczne encji
	 * pro.ibpm.mercury.entities.dict.Source .
	 * 
	 * @param entityFullName
	 *            pełna nazwa encji np pro.ibpm.mercury.entities.dict.Source
	 * @return
	 * @throws MercuryException
	 */
	public static synchronized void clearEntityCaches(String entityFullName) throws MercuryException {

		CacheProviderEnum lPprovider = getCacheProvider();
		if (lPprovider.isAllowed()) {
			IObjectCacheProvider<?> manager = CacheProviderFactory.getInstance(getCacheConfig().getPropertyList());
			/*
			 * czyszczenie pamięci podręcznej pro.ibpm.mercury.entities.dict.Source
			 */
			EhCache ch = (EhCache) manager.getCache(entityFullName);
			logger.trace("usuwam cache entityFullName={}", entityFullName);
			if (ch != null) {
				ch.getEhCache().clear();
			}
			/* czyszczenie cache MCase2Case.NamedQueryCacheName */
			try {

				Class<?> clazz = Class.forName(entityFullName);
				String interfaceName = EntityHelper.getSuperInterfaceName(clazz);
				if (StringUtils.isNotBlank(interfaceName)) {
					String namedQueryCacheName = interfaceName + MEntityCacheConfig.namedQueryCacheNamePostfix;
					ch = (EhCache) manager.getCache(namedQueryCacheName);
					logger.trace("usuwam cache namedQueryCacheName={}", namedQueryCacheName);
					if (ch != null) {
						ch.getEhCache().clear();
					}
				}
			} catch (ClassNotFoundException e) {
				throw new InternalErrorException(e);
			}
		}
	}

	/**
	 * Pobranie managera pamięci podręcznej.
	 * 
	 * @return
	 */
	private static CacheProviderEnum getCacheProvider() {
		CacheProviderEnum lPprovider = null;
		String pProvider = null;

		/* ustawienie parametrów cache'a */
		pProvider = MercuryConfig.getInstance().get(CacheConstants.PROP_PROVIDER);
		if (StringUtils.isNotBlank(pProvider)) {
			lPprovider = CacheProviderEnum.valueOf(pProvider);
		} else {
			lPprovider = CacheProviderEnum.none;
		}
		return lPprovider;
	}

	/**
	 * Pobiera statystyki dla wybranych cache.
	 * 
	 * @param manager
	 *            dostawca pamięci podręcznej
	 * @param cachesNames
	 *            nazwy cach-y dla których chcemy pobrać statystyki
	 * @return
	 */
	public static List<IStatsInfoRecord> getAllStatistics(IObjectCacheProvider<?> manager, String[] cachesNames) {
		List<IStatsInfoRecord> result = new ArrayList<IStatsInfoRecord>();
		if (cachesNames != null) {
			for (String key : cachesNames) {
				/* pobieram statystyki cache */
				IObjectCacheStatistics row = manager.getStatistics(key);
				IStatsInfoRecord record = transform2StatsInfoRecord(row);
				result.add(record);
			}
		}
		if (!result.isEmpty()) {
			Collections.sort(result, new CustomComparator());
		}
		return result;
	}

	public static void clearCache(String cacheName) {
		CacheProviderEnum lPprovider = getCacheProvider();
		if (lPprovider.isAllowed()) {
			IObjectCacheProvider<?> manager = CacheProviderFactory.getInstance(getCacheConfig().getPropertyList());
			manager.clearCache(cacheName);
		}
	}

	public static String[] getCacheNames() {
		CacheProviderEnum lPprovider = getCacheProvider();
		if (lPprovider.isAllowed()) {
			IObjectCacheProvider<?> manager = CacheProviderFactory.getInstance(getCacheConfig().getPropertyList());
			return manager.getCacheNames();
		}
		return new String[0];
	}

	public static IObjectCache getCache(String name) {
		CacheProviderEnum lPprovider = getCacheProvider();
		if (lPprovider.isAllowed()) {
			IObjectCacheProvider<?> manager = CacheProviderFactory.getInstance(getCacheConfig().getPropertyList());
			return manager.getCache(name);
		}
		return null;
	}

	public static StatsInfoRecord transform2StatsInfoRecord(final IObjectCacheStatistics cacheStatistics) {
		StatsInfoRecord result = new StatsInfoRecord();
		result.setCacheName(cacheStatistics.getAssociatedCacheName());
		result.setCacheHits(cacheStatistics.getCacheHits());
		result.setInMemoryHits(cacheStatistics.getInMemoryHits());
		result.setOnDiskHits(cacheStatistics.getOnDiskHits());
		result.setCacheMisses(cacheStatistics.getCacheMisses());
		result.setObjectCount(cacheStatistics.getObjectCount());
		result.setHitsRatio(cacheStatistics.getHitsRatio());
		result.setSize(cacheStatistics.getSize());
		result.setAssociatedManagerName(cacheStatistics.getAssociatedManagerName());
		result.setActive(cacheStatistics.isActive());
		return result;
	}

	/**
	 * @return the {@link #cacheConfig}
	 */
	public static EhCacheConfig getCacheConfig() {
		return cacheConfig;
	}

	/**
	 * @param cacheConfig
	 *            the {@link #cacheConfig} to set
	 */
	public static void setCacheConfig(EhCacheConfig cacheConfig) {
		CacheHelper.cacheConfig = cacheConfig;
	}

	private static class CustomComparator implements Comparator<IStatsInfoRecord> {
		@Override
		public int compare(IStatsInfoRecord o1, IStatsInfoRecord o2) {
			return o1.getAssociatedCacheName().compareTo(o2.getAssociatedCacheName());
		}
	}

}
