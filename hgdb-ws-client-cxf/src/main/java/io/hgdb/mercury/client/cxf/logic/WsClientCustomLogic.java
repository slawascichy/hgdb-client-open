/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic;

import java.lang.reflect.ParameterizedType;
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
import pro.ibpm.mercury.entities.data.MUser;
import pro.ibpm.mercury.entities.dict.MRole;
import pro.ibpm.mercury.entities.helpers.EntityHelper;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.MDictLogic;
import pro.ibpm.mercury.logic.MModifyInfoHelper;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.registry.RegistrySupport;
import pro.ibpm.mercury.ws.server.api.actions.IActionRoot;

/**
 * 
 * WsClientCustomLogic
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 * @param <E>
 * @param <Pk>
 * @param <Ws>
 */
@SuppressWarnings("serial")
public abstract class WsClientCustomLogic<E extends MEntity & MIdModifier<Pk>, Pk, Ws extends IActionRoot>
		extends WsClientRoot<E, Pk, Ws> implements MDictLogic<E, Pk>, IWsClientDictLogic<E, Pk> {

	/** Obiekt klasy encji. */
	private final Class<E> persistentClass;
	private final Class<? extends DtoObject> persistentClassDto;

	@SuppressWarnings("unchecked")
	public WsClientCustomLogic() {
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

	@Override
	public String getSpringBeanName() {
		return RegistrySupport.getBeanName(getClass());
	}

	@Override
	public E find(Context context, Pk id, boolean forUpdate) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public E findReference(Context context, E entity) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public List<E> find(Context context, String namedQuery, Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public E findFirst(Context context, String namedQuery, Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public NameValuePair loadNameValuePair(Context context, String id) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public List<NameValuePair> searchNameValuePairByName(Context context, String searchText) throws MercuryException {
		throw new LC025MethodNotSupportedException();
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
		return new PagedResult<>();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Pk checkExists(Context context, Pk id) throws MercuryException {
		E existed = find(context, id);
		if (existed != null) {
			return EntityHelper.getPrimaryKey(existed);
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public NameValuePair loadNameValuePair(Context context, Object id, String additionalStaticCredentials)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<NameValuePair> loadNameValuePairList(Context context, String additionalStaticCredentials)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<NameValuePair> searchNameValuePairByName(Context context, String searchText,
			String additionalStaticCredentials) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public NameValuePairWSC loadNameValuePairWSC(Context context, Object id, String additionalStaticCredentials)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<NameValuePairWSC> loadNameValuePairWSCList(Context context, String additionalStaticCredentials)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<NameValuePairWSC> searchNameValuePairWSCByCredentials(Context context, String searchText,
			String additionalStaticCredentials) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public E find(Context context, Pk id) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<E> findAll(Context context) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public E findFirst(Context context) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public E insert(Context context, E entityObject) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<E> insertList(Context context, List<E> entityObjects) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public E update(Context context, E entityObject) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<E> updateList(Context context, List<E> entityObjects) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Pk remove(Context context, E entityObject) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<Pk> removeList(Context context, List<E> entityObjects) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	public List<E> filter(Context context, Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	public <U extends MUser<U>, R extends MRole<R>> MModifyInfoHelper<U, R> getModifyInfoHelper() {
		throw new IllegalAccessError();
	}

}
