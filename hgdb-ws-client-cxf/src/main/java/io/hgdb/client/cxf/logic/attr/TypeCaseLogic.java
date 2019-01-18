/**
 * 
 */
package io.hgdb.client.cxf.logic.attr;

import java.util.Collection;
import java.util.List;

import io.hgdb.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.business.data.api.CaseHeader;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.attr.SubType;
import pro.ibpm.mercury.entities.attr.TypeCase;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.attr.ITypeCaseLogic;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.ws.server.api.actions.attr.ITypeCaseAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithCaseHeader;

/**
 * 
 * TypeCaseLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class TypeCaseLogic extends WsClientDataLogic<TypeCase, Long, ITypeCaseAction> implements ITypeCaseLogic {

	private static final long serialVersionUID = 7830579927175997507L;

	@Override
	public TypeCase insert(Context context, final TypeCase e) throws MercuryException {
		/* pole version minor jest wymagane!! */
		if (e.getVersionMinor() == null) {
			e.setVersionMinor(0.0);
		}
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<TypeCase, Long> insertList(Context context, List<TypeCase> eBag) throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, (Collection<TypeCase>) eBag));
	}

	@Override
	public Long remove(Context context, final TypeCase e) throws MercuryException {
		/* pole version minor jest wymagane!! */
		if (e.getVersionMinor() == null) {
			e.setVersionMinor(0.0);
		}
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<TypeCase> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, (Collection<TypeCase>) eBag), eBag);
	}

	@Override
	public List<TypeCase> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService().findAll(context));
	}

	@Override
	public TypeCase find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public TypeCase findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public TypeCase update(Context context, TypeCase e) throws MercuryException {
		/* pole version minor jest wymagane!! */
		if (e.getVersionMinor() == null) {
			e.setVersionMinor(0.0);
		}
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<TypeCase, Long> updateList(Context context, List<TypeCase> eBag) throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, (Collection<TypeCase>) eBag));
	}

	@Override
	public EntityList<TypeCase, Long> findLastVersionByTypeName(Context context, String typeName)
			throws MercuryException {
		return getEntityCollection(context, getService().findLastVersionByTypeName(context, typeName));
	}

	@Override
	public TypeCase findLastVersionByTypeCode(Context context, String typeCode) throws MercuryException {
		return getEntity(context, getService().findLastVersionByTypeCode(context, typeCode));
	}

	@Override
	public List<TypeCase> getAllDocumentTypes(Context context) throws MercuryException {
		return getEntityCollection(context, getService().getAllDocumentTypes(context));
	}

	@Override
	public List<TypeCase> getAllCaseTypes(Context context) throws MercuryException {
		return getEntityCollection(context, getService().getAllCaseTypes(context));
	}

	@Override
	public List<TypeCase> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService().findByKeyBag(context, idList));
	}

	@Override
	public List<TypeCase> filter(Context context, TypeCase e) throws MercuryException {
		return getEntityCollection(context, getService().filter(context, e));
	}

	@Override
	public void setQueryCacheEnabled(Context context, boolean queryCacheEnabled) {
		getService().setQueryCacheEnabled(context, queryCacheEnabled);
	}

	@Override
	public void synchronizationWithCMIS(Context context) throws MercuryException {
		getService().synchronizationWithCMIS(context);
	}

	@Override
	public void checkSynchronizationWithCMIS(Context context) throws MercuryException {
		getService().checkSynchronizationWithCMIS(context);
	}

	@Override
	public List<TypeCase> findAllVersionByTypeCode(Context context, String typeCode) throws MercuryException {
		return getEntityCollection(context, getService().findAllVersionByTypeCode(context, typeCode));
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public Integer findUniqueConstraintParamPosition(Context context, Long typeId) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public Integer findRequiredConstraintParamPosition(Context context, Long typeId, String paramName)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public SubType findSubTypeByTypeAndFieldName(Context context, Long typeId, String fieldName)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public List<TypeCase> findByNameAndSumControl(Context context, String typeName, String sumControl)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public List<TypeCase> findByCodeAndSumControl(Context context, String typeCode, String sumControl)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public List<TypeCase> findByObjectIdAndSumControl(Context context, String objectId, String sumControl)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public List<TypeCase> findByObjectIdLastVersion(Context context, String objectId) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<TypeCase> findAllBySumControl(Context paramContext, String paramString) throws MercuryException {
		return getEntityCollection(paramContext, getService().findAllBySumControl(paramContext, paramString));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<TypeCase> findLatestVersionBySumControl(Context paramContext, String paramString)
			throws MercuryException {
		return getEntityCollection(paramContext, getService().findLatestVersionBySumControl(paramContext, paramString));
	}

	@Override
	public List<TypeCase> findByKindCode(Context context, String kindCode) throws MercuryException {
		return getEntityCollection(context, getService().findByKindCode(context, kindCode));
	}

	@Override
	public List<TypeCase> findByObjectIdAndRootVersionContextID(Context context, String objectID,
			String rootVersionContextID) throws MercuryException {
		return getEntityCollection(context,
				getService().findByObjectIdAndRootVersionContextID(context, objectID, rootVersionContextID));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<TypeCase> findByIdentity(Context context, String typeCode, String objectID, String rootVersionContextID)
			throws MercuryException {
		return getEntityCollection(context,
				getService().findByIdentity(context, typeCode, objectID, rootVersionContextID));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<TypeCase> findByIdentityPart(Context context, String typeCode, String objectID)
			throws MercuryException {
		return getEntityCollection(context, getService().findByIdentityPart(context, typeCode, objectID));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public CaseHeader getSampleCaseHeaderByTypeId(Context context, Long typeId) throws MercuryException {
		WsStatusWithCaseHeader result = getService().getSampleCaseHeaderByTypeId(context, typeId);
		if (checkWsStatus((IWsStatus) result)) {
			return result.getValue();
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public CaseHeader getSampleCaseHeaderByTypeCode(Context context, String typeCode) throws MercuryException {
		WsStatusWithCaseHeader result = getService().getSampleCaseHeaderByTypeCode(context, typeCode);
		if (checkWsStatus((IWsStatus) result)) {
			return result.getValue();
		}
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public TypeCase update(Context context, TypeCase entityObject, boolean canChangeTypeCode) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public List<TypeCase> updateList(Context context, List<TypeCase> entityObjects, boolean canChangeTypeCode)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public List<TypeCase> findAllVersionByTypeName(Context context, String typeName) throws MercuryException {
		return getEntityCollection(context, getService().findAllVersionByTypeName(context, typeName));
	}
}
