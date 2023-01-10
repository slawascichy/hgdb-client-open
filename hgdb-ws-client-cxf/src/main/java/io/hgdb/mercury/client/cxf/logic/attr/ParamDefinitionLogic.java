package io.hgdb.mercury.client.cxf.logic.attr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pl.slawas.entities.NameValuePair;
import pro.ibpm.mercury.attrs.AttributeType;
import pro.ibpm.mercury.attrs.IAttributeTypeParameter;
import pro.ibpm.mercury.attrs.IAttributeTypeParameterPK;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.context.ContextHelper;
import pro.ibpm.mercury.entities.attr.ParamDefinition;
import pro.ibpm.mercury.entities.attr.ParamDefinitionPK;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.MUser;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.attr.IParamDefinitionLogic;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.ws.server.api.actions.attr.IParamDefinitionAction;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithNameValuePairDto;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithNameValuePairDtos;
import pro.ibpm.mercury.ws.server.api.returns.WsStatusWithStringValue;

/**
 * 
 * ParamDefinitionLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class ParamDefinitionLogic extends WsClientDataLogic<ParamDefinition, ParamDefinitionPK, IParamDefinitionAction>
		implements IParamDefinitionLogic {

	private static final long serialVersionUID = 9093181732052232898L;

	@Override
	public ParamDefinition insert(Context context, final ParamDefinition e) throws MercuryException {
		/* Sprawdzam ustawienie wymaganego pola subType */
		if (StringUtils.isBlank(e.getSubType())) {
			AttributeType at = AttributeType.valueOf(e.getParamType());
			e.setSubType(at.getDefaultSubType());
		}
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<ParamDefinition, ParamDefinitionPK> insertList(Context context, List<ParamDefinition> eBag)
			throws MercuryException {
		if (eBag == null || eBag.isEmpty()) {
			return null;
		}
		for (ParamDefinition e : eBag) {
			/* Sprawdzam ustawienie wymaganego pola subType */
			if (StringUtils.isBlank(e.getSubType())) {
				AttributeType at = AttributeType.valueOf(e.getParamType());
				e.setSubType(at.getDefaultSubType());
			}
		}
		return getEntityCollection(context, getService(context).insertBag(context, (Collection<ParamDefinition>) eBag));
	}

	@Override
	public ParamDefinitionPK remove(Context context, final ParamDefinition e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<ParamDefinitionPK> removeList(Context context, final List<ParamDefinition> eBag)
			throws MercuryException {
		return getIds(getService(context).removeBag(context, (Collection<ParamDefinition>) eBag), eBag);
	}

	@Override
	public List<ParamDefinition> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findAll(context));
	}

	@Override
	public ParamDefinition find(Context context, final ParamDefinitionPK pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public ParamDefinition findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public ParamDefinition update(Context context, ParamDefinition e) throws MercuryException {
		/* Sprawdzam ustawienie wymaganego pola subType */
		if (StringUtils.isBlank(e.getSubType())) {
			AttributeType at = AttributeType.valueOf(e.getParamType());
			e.setSubType(at.getDefaultSubType());
		}
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<ParamDefinition, ParamDefinitionPK> updateList(Context context, List<ParamDefinition> eBag)
			throws MercuryException {
		if (eBag == null || eBag.isEmpty()) {
			return null;
		}
		for (ParamDefinition e : eBag) {
			/* Sprawdzam ustawienie wymaganego pola subType */
			if (StringUtils.isBlank(e.getSubType())) {
				AttributeType at = AttributeType.valueOf(e.getParamType());
				e.setSubType(at.getDefaultSubType());
			}
		}
		return getEntityCollection(context, getService(context).updateBag(context, (Collection<ParamDefinition>) eBag));
	}

	@Override
	public ParamDefinition findLastVersionByDefinitionName(Context context, String definitionName)
			throws MercuryException {
		return getEntity(context, getService(context).findLastVersionByDefinitionName(context, definitionName));
	}

	@Override
	public List<ParamDefinition> findLastVersionsByDefinitionNames(Context context,
			Collection<String> definitionNameList) throws MercuryException {
		return getEntityCollection(context,
				getService(context).findLastVersionsByDefinitionNames(context, definitionNameList));
	}

	@Override
	public List<ParamDefinition> findForCasesOnlyAllVersions(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findForCasesOnlyAllVersions(context));
	}

	@Override
	public List<ParamDefinition> findForDocumentsOnlyAllVersions(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findForDocumentsOnlyAllVersions(context));
	}

	@Override
	public String encodeValue(Context context, ParamDefinition paramDefinition, String value) throws MercuryException {
		WsStatusWithStringValue vDto = getService(context).encodeValue(context, paramDefinition, value);
		return vDto.getValue();
	}

	@Override
	public NameValuePair getValue(Context context, ParamDefinition paramDefinition, String key)
			throws MercuryException {
		WsStatusWithNameValuePairDto dto = getService(context).getValue(context, paramDefinition, key);
		return dto.getDto();
	}

	@Override
	public List<NameValuePair> getValues(Context context, ParamDefinition paramDefinition,
			ParamDefinition superiorLovPd, String superiorKey) throws MercuryException {
		WsStatusWithNameValuePairDtos dtos = getService(context).getValues(context, paramDefinition, superiorLovPd,
				superiorKey);
		return dtos.getDtos() != null ? new ArrayList<NameValuePair>(dtos.getDtos()) : new ArrayList<NameValuePair>();
	}

	@Override
	public List<NameValuePair> getValuesLastVersions(Context context, String paramDefinitionName,
			String superiorParamDefinitionName, String superiorKey) throws MercuryException {
		WsStatusWithNameValuePairDtos dtos = getService(context).getValuesLastVersion(context, paramDefinitionName,
				superiorParamDefinitionName, superiorKey);
		return dtos.getDtos() != null ? new ArrayList<NameValuePair>(dtos.getDtos()) : new ArrayList<NameValuePair>();
	}

	@Override
	public void reloadPredefinedDefinitions() throws MercuryException {
		Context context = ContextHelper.createContext(MUser.DEFAULT_SYSTEM_USER_NAME, createComment());
		checkWsStatus(getService(context).reloadPredefinedDefinitions(context));
	}

	@Override
	public List<ParamDefinition> findByIdList(Context context, List<ParamDefinitionPK> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, (Collection<ParamDefinitionPK>) idList));
	}

	@Override
	public List<ParamDefinition> filter(Context context, ParamDefinition e) throws MercuryException {
		return getEntityCollection(context, getService(context).filter(context, e));
	}

	/* Overridden (non-Javadoc) */
	@Override
	public ParamDefinition findLatestByNameAndType(Context context, String definitionName, String paramType,
			String subType) throws MercuryException {
		return getEntity(context, getService(context).findLatestByNameAndType(context, definitionName, paramType, subType));
	}

	/* Overridden (non-Javadoc) */
	@Deprecated
	@Override
	public IAttributeTypeParameter getAttributeTypeParameter(ParamDefinition paramDefinition) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public IAttributeTypeParameter load(Context context, IAttributeTypeParameterPK arg1) throws MercuryException {
		ParamDefinitionPK pk = new ParamDefinitionPK(arg1.getDefinitionName(), arg1.getVersion());
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public IAttributeTypeParameterPK newPkInstance() {
		return new ParamDefinitionPK();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public NameValuePair getValueWithArg3(Context context, ParamDefinition paramDefinition, String key, boolean arg3)
			throws MercuryException {
		WsStatusWithNameValuePairDto dto = getService(context).getValueWithArg3(context, paramDefinition, key, arg3);
		return dto.getDto();
	}
}
