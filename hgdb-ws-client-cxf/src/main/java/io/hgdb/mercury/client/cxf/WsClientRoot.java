/**
 * 
 */
package io.hgdb.mercury.client.cxf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import io.hgdb.mercury.client.services.EntityPropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dao.services.IEntityPropertyMapper;
import pro.ibpm.mercury.dto.DtoObject;
import pro.ibpm.mercury.dto.StringsPagedResult;
import pro.ibpm.mercury.dto.paging.PagedResult;
import pro.ibpm.mercury.dto.paging.PagingInfo;
import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.helpers.EntityHelper;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.utils.property.config.ObjectMetaData;
import pro.ibpm.mercury.utils.property.config.ObjectMetaDataHelper;
import pro.ibpm.mercury.ws.server.api.actions.IActionRoot;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithBag;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithDto;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithDtos;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithStringsPagedResult;

/**
 * 
 * WsClientRootLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 * @param <E>
 * @param <Pk>
 * @param <Ws>
 */
public abstract class WsClientRoot<E extends MEntity, Pk, Ws extends IActionRoot> extends WsClient<Ws> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private IEntityPropertyMapper singleObjectInitializer = null;
	private IEntityPropertyMapper collectionObjectInitializer = null;
	private IEntityPropertyMapper singleObjectInitializerEager = null;
	private IEntityPropertyMapper collectionObjectInitializerEager = null;

	public abstract Class<E> getPersistentClass();

	public abstract Class<? extends DtoObject> getPersistentClassDto();

	public IEntityPropertyMapper getSingleObjectInitializer() throws MercuryException {
		if (singleObjectInitializer == null) {
			ObjectMetaData singleGetterObjectMetaData = ObjectMetaDataHelper.loadGetters(getPersistentClassDto(),
					/* propertyConfig */ null, /* eager */ false, /* forCollection */ EntityHelper.FOR_SINGLETON);
			ObjectMetaData singleSetterObjectMetaData = ObjectMetaDataHelper.loadSetters(getPersistentClass(),
					/* propertyConfig */ null, /* eager */ false, /* forCollection */ EntityHelper.FOR_SINGLETON);
			singleObjectInitializer = new EntityPropertyMapper();
			singleObjectInitializer.init(getPersistentClassDto(), getPersistentClass(), singleGetterObjectMetaData,
					singleSetterObjectMetaData);
		}
		return singleObjectInitializer;
	}

	public IEntityPropertyMapper getSingleObjectInitializerEager() throws MercuryException {
		if (singleObjectInitializerEager == null) {
			ObjectMetaData singleGetterObjectMetaData = ObjectMetaDataHelper.loadGetters(getPersistentClassDto(),
					/* propertyConfig */ null, /* eager */ true, /* forCollection */ EntityHelper.FOR_SINGLETON);
			ObjectMetaData singleSetterObjectMetaData = ObjectMetaDataHelper.loadSetters(getPersistentClass(),
					/* propertyConfig */ null, /* eager */ true, /* forCollection */ EntityHelper.FOR_SINGLETON);
			singleObjectInitializerEager = new EntityPropertyMapper();
			singleObjectInitializerEager.init(getPersistentClassDto(), getPersistentClass(), singleGetterObjectMetaData,
					singleSetterObjectMetaData);
		}
		return singleObjectInitializerEager;
	}

	public IEntityPropertyMapper getCollectionObjectInitializer() throws MercuryException {
		if (collectionObjectInitializer == null) {
			ObjectMetaData singleGetterObjectMetaData = ObjectMetaDataHelper.loadGetters(getPersistentClassDto(),
					/* propertyConfig */ null, /* eager */ false, /* forCollection */ EntityHelper.FOR_COLLECTION);
			ObjectMetaData singleSetterObjectMetaData = ObjectMetaDataHelper.loadSetters(getPersistentClass(),
					/* propertyConfig */ null, /* eager */ false, /* forCollection */ EntityHelper.FOR_COLLECTION);
			collectionObjectInitializer = new EntityPropertyMapper();
			collectionObjectInitializer.init(getPersistentClassDto(), getPersistentClass(), singleGetterObjectMetaData,
					singleSetterObjectMetaData);
		}
		return collectionObjectInitializer;
	}

	public IEntityPropertyMapper getCollectionObjectInitializerEager() throws MercuryException {
		if (collectionObjectInitializerEager == null) {
			ObjectMetaData singleGetterObjectMetaData = ObjectMetaDataHelper.loadGetters(getPersistentClassDto(),
					/* propertyConfig */ null, /* eager */ true, /* forCollection */ EntityHelper.FOR_COLLECTION);
			ObjectMetaData singleSetterObjectMetaData = ObjectMetaDataHelper.loadSetters(getPersistentClass(),
					/* propertyConfig */ null, /* eager */ true, /* forCollection */ EntityHelper.FOR_COLLECTION);
			collectionObjectInitializerEager = new EntityPropertyMapper();
			collectionObjectInitializerEager.init(getPersistentClassDto(), getPersistentClass(),
					singleGetterObjectMetaData, singleSetterObjectMetaData);
		}
		return collectionObjectInitializerEager;
	}

	protected Pk getId(final IWsStatus wsStatus, final E e) throws MercuryException {
		checkWsStatus(wsStatus);
		return EntityHelper.getPrimaryKey(e);
	}

	@SuppressWarnings("unchecked")
	protected List<Pk> getIds(final IWsStatus wsStatus, final Collection<E> eBag) throws MercuryException {
		checkWsStatus(wsStatus);
		final List<Pk> ids = new ArrayList<Pk>();
		for (final E e : eBag) {
			ids.add((Pk) EntityHelper.getPrimaryKey(e));
		}
		return ids;
	}

	/**
	 * Inicjalizacja obiektu
	 * 
	 * @param context
	 *            context z flagą informującą o tym ile danych ma być
	 *            zainicjalizowane, czy tylko wymagane (eager) czy wszystkie
	 *            możliwe. Domyślnie ustawiona jest na przesyłanie tylko danych
	 *            wymaganych czyli eager = 'true'.
	 * @param dtoObj
	 * @return
	 * @throws MercuryException
	 */
	@SuppressWarnings("unchecked")
	public <EDto> E initObj(Context context, EDto dtoObj) throws MercuryException {
		if (dtoObj == null) {
			return null;
		}
		E entity;
		boolean eager = (StringUtils.isNotBlank(context.getEager4omdBuilder())
				? Boolean.parseBoolean(context.getEager4omdBuilder())
				: true);
		if (eager) {
			entity = (E) this.getSingleObjectInitializerEager().mapObj(dtoObj);
		} else {
			entity = (E) this.getSingleObjectInitializer().mapObj(dtoObj);

		}
		return entity;
	}

	/**
	 * Inicjalizacja kolekcji encji
	 * 
	 * @param context
	 *            context z flagą informującą o tym ile danych ma być
	 *            zainicjalizowane, czy tylko wymagane (eager) czy wszystkie
	 *            możliwe. Domyślnie ustawiona jest na przesyłanie tylko danych
	 *            wymaganych czyli eager = 'true'.
	 * @param dtoList
	 * @return
	 * @throws MercuryException
	 */
	@SuppressWarnings("unchecked")
	public <EDto> EntityList<E, Pk> initCollection(Context context, Collection<EDto> dtoList) throws MercuryException {
		if (dtoList == null) {
			return null;
		}
		EntityList<E, Pk> returnList = new EntityList<E, Pk>();
		boolean eager = (StringUtils.isNotBlank(context.getEager4omdBuilder())
				? Boolean.parseBoolean(context.getEager4omdBuilder())
				: true);
		if (eager) {
			for (EDto entity : dtoList) {
				returnList.add((E) getCollectionObjectInitializerEager().mapObj(entity));
			}
		} else {
			for (EDto entity : dtoList) {
				returnList.add((E) getCollectionObjectInitializer().mapObj(entity));
			}
		}
		return returnList;

	}

	protected <T> E getEntity(Context context, final IWsStatusWithDto<T> wsStatusWithDto) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithDto)) {
			logger.debug("--> getEntity: wsStatusWithDto={}", new Object[] { wsStatusWithDto });
			try {
				return initObj(context, wsStatusWithDto.getDto());
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return null;
	}

	protected <T> Collection<E> getEntityBag(Context context, final IWsStatusWithBag<T> wsStatusWithBag)
			throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithBag)) {
			logger.debug("--> getEntityBag: wsStatusWithBag={}", new Object[] { wsStatusWithBag });
			try {
				return initCollection(context, wsStatusWithBag.getBag());
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return Collections.emptyList();
	}

	protected <T> EntityList<E, Pk> getEntityCollection(Context context, final IWsStatusWithDtos<T> wsStatusWithDtos)
			throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithDtos)) {
			logger.debug("--> getEntityCollection: wsStatusWithDtos={}", new Object[] { wsStatusWithDtos });
			try {
				return initCollection(context, wsStatusWithDtos.getDtos());
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return new EntityList<E, Pk>();
	}

	protected <Edto, Pdto extends IPagedResult<Edto, IPage>> IPagedResult<E, IPage> getPagedResult(Context context,
			final IWsStatusWithPagedResult<Edto, IPage, Pdto> wsStatusWithPagedResult) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithPagedResult)) {
			logger.debug("--> getPagedResult: wsStatusWithPagedResult={}", new Object[] { wsStatusWithPagedResult });
			try {
				/* czytamy rezultat z serwisu */
				Pdto pagedResultDto = wsStatusWithPagedResult.getPagedResult();
				Collection<Edto> dtos = pagedResultDto.getResult();
				Collection<E> eList = initCollection(context, dtos);
				/* Teraz trzeba utworzyć obiekt stronicowanego wyniku z encją */
				PagedResult<E> result = new PagedResult<E>(new PagingInfo(pagedResultDto));
				result.setResult(eList);
				return result;
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return null;
	}

	protected IPagedResult<String, IPage> getStringPagedResult(Context context,
			final WsStatusWithStringsPagedResult wsStatusWithPagedResult) throws MercuryException {
		if (checkWsStatus((IWsStatus) wsStatusWithPagedResult)) {
			logger.debug("--> getStringPagedResult: wsStatusWithPagedResult={}",
					new Object[] { wsStatusWithPagedResult });
			try {
				/* czytamy rezultat z serwisu */
				StringsPagedResult pagedResultDto = wsStatusWithPagedResult.getPagedResult();
				Collection<String> dtos = pagedResultDto.getResult();
				/* Teraz trzeba utworzyć obiekt stronicowanego wyniku z encją */
				PagedResult<String> result = new PagedResult<String>(new PagingInfo(pagedResultDto));
				result.setResult(dtos);
				return result;
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return null;

	}

	public E createNewInstance(Context context) {
		try {
			return getPersistentClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("-->createNewInstance:", e);
		}
		return null;
	}

}
