/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.hgdb.mercury.client.cxf.WsClientRoot;
import pl.slawas.entities.NameValuePair;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.DtoObject;
import pro.ibpm.mercury.dto.paging.PagedResult;
import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.entities.MIdModifier;
import pro.ibpm.mercury.entities.beans.NameValuePairWSC;
import pro.ibpm.mercury.entities.helpers.EntityHelper;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.MDictLogic;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.registry.RegistrySupport;
import pro.ibpm.mercury.ws.server.api.actions.IActionNVP;
import pro.ibpm.mercury.ws.server.api.actions.IActionRoot;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithNameValuePairDto;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithNameValuePairDtos;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithNameValuePairWSCDtos;

/**
 * 
 * WsClientDictLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 * @param <E>
 * @param <Pk>
 * @param <Ws>
 */
@SuppressWarnings("serial")
public abstract class WsClientDictLogic<E extends MEntity & MIdModifier<Pk>, Pk, Ws extends IActionRoot & IActionNVP>
		extends WsClientRoot<E, Pk, Ws> implements MDictLogic<E, Pk>, IWsClientDictLogic<E, Pk> {

	/** Obiekt klasy encji. */
	private final Class<E> persistentClass;
	private final Class<? extends DtoObject> persistentClassDto;

	@SuppressWarnings("unchecked")
	public WsClientDictLogic() {
		Class<?> tmpClass = this.getClass();
		while (!(tmpClass.getGenericSuperclass() instanceof ParameterizedType)) {
			tmpClass = tmpClass.getSuperclass();
		}

		this.persistentClass = (Class<E>) ((ParameterizedType) tmpClass.getGenericSuperclass())
				.getActualTypeArguments()[0];

		String persistentClassDtoName = EntityHelper.DTO_PACKAGE_NAME + MercuryConfig.DOT
				+ persistentClass.getSimpleName() + EntityHelper.DTO_NAME_SUFFIX;
		try {
			persistentClassDto = (Class<? extends DtoObject>) Class.forName(persistentClassDtoName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public Class<E> getPersistentClass() {
		return persistentClass;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Class<? extends DtoObject> getPersistentClassDto() {
		return persistentClassDto;
	}

	@Deprecated
	@Override
	public String getSpringBeanName() {
		return RegistrySupport.getBeanName(getClass());
	}

	@Deprecated
	@Override
	public E find(Context context, Pk id, boolean forUpdate) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public E findReference(Context context, E entity) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<E> find(Context context, String namedQuery, Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public E findFirst(Context context, String namedQuery, Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<NameValuePair> loadNameValuePair(Context context, String namedQuery, Map<String, Object> sqlParams)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Deprecated
	@Override
	public List<NameValuePairWSC> loadNameValuePairWSC(Context context, String namedQuery,
			Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public NameValuePair loadNameValuePair(Context context, Object id, String additionalStaticCredentials)
			throws MercuryException {
		WsStatusWithNameValuePairDto dto = getService().loadNameValuePair(context, id, additionalStaticCredentials);
		return dto.getDto();
	}

	@Override
	public List<NameValuePair> searchNameValuePairByName(Context context, String searchText,
			String additionalStaticCredentials) throws MercuryException {
		WsStatusWithNameValuePairDtos dtos = getService().searchNameValuePairByName(context, searchText,
				additionalStaticCredentials);
		return dtos.getDtos() != null ? new ArrayList<NameValuePair>(dtos.getDtos()) : new ArrayList<NameValuePair>();
	}

	@Override
	public List<NameValuePairWSC> loadNameValuePairWSC(Context context) throws MercuryException {
		WsStatusWithNameValuePairWSCDtos dtos = getService().loadNameValuePairWSC(context);
		return dtos.getDtos() != null ? new ArrayList<NameValuePairWSC>(dtos.getDtos())
				: new ArrayList<NameValuePairWSC>();
	}

	@Override
	public E persistentClassNewInstance(Context context) {
		try {
			return persistentClass.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public IPagedResult<E, IPage> pagedResultNewInstance(Context context) {
		IPagedResult<E, IPage> newInstance = new PagedResult<E>();
		return newInstance;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Pk checkExists(Context context, Pk id) throws MercuryException {
		E existed = find(context, id, false);
		if (existed != null) {
			return EntityHelper.getPrimaryKey(existed);
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public E find(Context arg0, Pk arg1) throws MercuryException {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<E> findAll(Context arg0) throws MercuryException {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public E findFirst(Context arg0) throws MercuryException {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public E insert(Context arg0, E arg1) throws MercuryException {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<E> insertList(Context arg0, List<E> arg1) throws MercuryException {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Pk remove(Context arg0, E arg1) throws MercuryException {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<Pk> removeList(Context arg0, List<E> arg1) throws MercuryException {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public E update(Context arg0, E arg1) throws MercuryException {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<E> updateList(Context arg0, List<E> arg1) throws MercuryException {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<NameValuePair> loadNameValuePair(Context context) throws MercuryException {
		WsStatusWithNameValuePairWSCDtos dtos = getService().loadNameValuePairWSC(context);
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		if (dtos.getDtos() != null && !dtos.getDtos().isEmpty()) {
			for (NameValuePair dto : dtos.getDtos()) {
				result.add(dto);
			}
		}
		return result;
	}

}
