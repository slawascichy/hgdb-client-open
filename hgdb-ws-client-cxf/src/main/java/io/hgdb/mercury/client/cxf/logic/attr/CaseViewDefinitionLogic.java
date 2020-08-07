package io.hgdb.mercury.client.cxf.logic.attr;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import io.hgdb.mercury.client.cxf.logic.WsClientDataLogic;
import pro.ibpm.mercury.attrs.CaseDateUtils;
import pro.ibpm.mercury.attrs.javax.CaseDate;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.attr.CaseViewDefinition;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.attr.ICaseViewDefinitionLogic;
import pro.ibpm.mercury.ws.server.api.actions.attr.ICaseViewDefinitionAction;

/**
 * 
 * CaseViewDefinitionLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CaseViewDefinitionLogic extends WsClientDataLogic<CaseViewDefinition, String, ICaseViewDefinitionAction>
		implements ICaseViewDefinitionLogic {

	private static final long serialVersionUID = 5897034660116989128L;

	@Override
	public CaseViewDefinition find(Context context, String pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public List<CaseViewDefinition> findAll(Context context) throws MercuryException {
		return (List<CaseViewDefinition>) getEntityCollection(context, getService(context).findAll(context));
	}

	@Override
	public CaseViewDefinition findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public CaseViewDefinition insert(Context context, CaseViewDefinition e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public List<CaseViewDefinition> insertList(Context context, List<CaseViewDefinition> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, (Collection<CaseViewDefinition>) eBag));
	}

	@Override
	public String remove(Context context, CaseViewDefinition e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, List<CaseViewDefinition> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, (Collection<CaseViewDefinition>) eBag), eBag);
	}

	@Override
	public CaseViewDefinition update(Context context, CaseViewDefinition e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public List<CaseViewDefinition> updateList(Context context, List<CaseViewDefinition> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, (Collection<CaseViewDefinition>) eBag));
	}

	@Override
	public List<CaseViewDefinition> filter(Context context, CaseViewDefinition arg1) throws MercuryException {
		return getEntityCollection(context, getService(context).filter(context, arg1));
	}

	@Override
	public List<CaseViewDefinition> findByIdList(Context context, List<String> idList) throws MercuryException {
		return (List<CaseViewDefinition>) getEntityCollection(context,
				getService(context).findByKeyBag(context, (Collection<String>) idList));
	}

	@Override
	public CaseViewDefinition createView(Context context, String viewName, Set<String> typeParams, Calendar dateFrom,
			Calendar dateTo, String credentialsMode, Boolean skipHeaderFields, Boolean ignoreAlternateFields,
			String baseTypeCodes) throws MercuryException {
		CaseDate cDateTo = null;
		if (dateTo != null) {
			cDateTo = new CaseDate();
			CaseDateUtils.setDateValue(cDateTo, dateTo);
		}
		CaseDate cDateFrom = null;
		if (dateFrom != null) {
			cDateFrom = new CaseDate();
			CaseDateUtils.setDateValue(cDateFrom, dateFrom);
		}
		return getEntity(context, getService(context).createView(context, viewName, typeParams, cDateFrom, cDateTo,
				credentialsMode, skipHeaderFields, ignoreAlternateFields, baseTypeCodes));
	}

	@Override
	public CaseViewDefinition dropView(Context context, String viewName) throws MercuryException {
		return getEntity(context, getService(context).dropView(context, viewName));
	}

	@Override
	public CaseViewDefinition generateView(Context context, String viewName, boolean force) throws MercuryException {
		return getEntity(context, getService(context).generateView(context, viewName, force));
	}

	@Override
	public CaseViewDefinition updateView(Context context, String viewName, Set<String> typeParams, Calendar dateFrom,
			Calendar dateTo, String credentialsMode, Boolean skipHeaderFields, Boolean ignoreAlternateFields,
			String baseTypeCodes) throws MercuryException {
		CaseDate cDateTo = null;
		if (dateTo != null) {
			cDateTo = new CaseDate();
			CaseDateUtils.setDateValue(cDateTo, dateTo);
		}
		CaseDate cDateFrom = null;
		if (dateFrom != null) {
			cDateFrom = new CaseDate();
			CaseDateUtils.setDateValue(cDateFrom, dateFrom);
		}
		return getEntity(context, getService(context).updateView(context, viewName, typeParams, cDateFrom, cDateTo,
				credentialsMode, skipHeaderFields, ignoreAlternateFields, baseTypeCodes));
	}

}
