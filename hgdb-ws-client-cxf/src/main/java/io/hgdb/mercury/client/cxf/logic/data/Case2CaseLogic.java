package io.hgdb.mercury.client.cxf.logic.data;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientCatalogLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.Case;
import pro.ibpm.mercury.entities.data.Case2Case;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.ICase2CaseLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.ICase2CaseAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class Case2CaseLogic extends WsClientCatalogLogic<Case2Case, String, Case, Long, ICase2CaseAction>
		implements ICase2CaseLogic {

	private static final long serialVersionUID = -5323157854323141221L;

	@Override
	public Case2Case insert(Context context, final Case2Case e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<Case2Case, String> insertList(Context context, List<Case2Case> eBag) throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, (Collection<Case2Case>) eBag));
	}

	@Override
	public String remove(Context context, final Case2Case e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<Case2Case> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, (Collection<Case2Case>) eBag), eBag);
	}

	@Override
	public Case2Case find(Context context, final String pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public Case2Case findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public Case2Case update(Context context, Case2Case e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<Case2Case, String> updateList(Context context, List<Case2Case> eBag) throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, (Collection<Case2Case>) eBag));
	}

	@Override
	public List<Case2Case> getAllParentsRelationshipsByNodeId(Context context, Long n, String mountPoint)
			throws MercuryException {
		return (List<Case2Case>) getEntityCollection(context,
				getService().getAllParentsRelationshipsByNodeIdStr(context, Long.toString(n), mountPoint));
	}

	@Override
	public List<Case2Case> getAllChildrenRelationshipsByNodeId(Context context, Long n, String mountPoint)
			throws MercuryException {
		return (List<Case2Case>) getEntityCollection(context,
				getService().getAllChildrenRelationshipsByNodeIdStr(context, Long.toString(n), mountPoint));
	}

	@Override
	public List<Case> getAllDependsNodesByNodes(Context context, List<Case> nBag, String mountPoint)
			throws MercuryException {
		return (List<Case>) getNodeList(context, getService().getAllDependsNodesByNodes(context, nBag, mountPoint));
	}

	@Override
	public List<Case> getAllDependsNodes(Context context, Case n, String mountPoint) throws MercuryException {
		return (List<Case>) getNodeList(context, getService().getAllDependsNodes(context, n, mountPoint));
	}

	@Override
	public List<Case> getAllChildrenNodes(Context context, Case n, String mountPoint) throws MercuryException {
		return (List<Case>) getNodeList(context, getService().getAllChildrenNodes(context, n, mountPoint));
	}

	@Override
	public List<Case> getAllParentsNodes(Context context, Case n, String mountPoint) throws MercuryException {
		return (List<Case>) getNodeList(context, getService().getAllParentsNodes(context, n, mountPoint));
	}

	@Override
	public List<Case> getAllChildrenNodesWithTheTypeCode(Context context, Case n, String typeCode, String mountPoint)
			throws MercuryException {
		return (List<Case>) getNodeList(context,
				getService().getAllChildrenNodesWithTheTypeCode(context, n, typeCode, mountPoint));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPagedResult<Case2Case, IPage> filterPaged(Context context, Case2Case e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService().filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<Case2Case> getAllByPathStartsWith(Context context, String preffix, String mountPoint)
			throws MercuryException {
		return (List<Case2Case>) getEntityCollection(context,
				getService().getAllByPathStartsWith(context, preffix, mountPoint));
	}

	@Override
	public List<Case> getAllDependsNodesByNodesWithoutMe(Context context, List<Case> nBag, String mountPoint)
			throws MercuryException {
		return (List<Case>) getNodeList(context,
				getService().getAllDependsNodesByNodesWithoutMe(context, nBag, mountPoint));
	}

	@Override
	public List<Case2Case> getAllDependsRelationshipsByNodes(Context context, List<Case> nBag, String mountPoint)
			throws MercuryException {
		return (List<Case2Case>) getEntityCollection(context,
				getService().getAllDependsRelationshipsByNodes(context, nBag, mountPoint));
	}

	@Override
	public List<Case2Case> getAllDependsRelationshipsByNodesWithoutMe(Context context, List<Case> nBag,
			String mountPoint) throws MercuryException {
		return (List<Case2Case>) getEntityCollection(context,
				getService().getAllDependsRelationshipsByNodesWithoutMe(context, nBag, mountPoint));
	}

	@Override
	public List<Case2Case> findByIdList(Context context, List<String> idList) throws MercuryException {
		return (List<Case2Case>) getEntityCollection(context,
				getService().findByKeyBag(context, (Collection<String>) idList));
	}

	@Override
	public List<Case2Case> findAll(Context context) throws MercuryException {
		return (List<Case2Case>) getEntityCollection(context, getService().findAll(context));
	}

	@Override
	public Case2Case addNodeToMountPoint(Context paramContext, Case paramNodeArg, String paramString)
			throws MercuryException {
		return getEntity(paramContext, getService().addNodeToMountPoint(paramContext, paramNodeArg, paramString));
	}

	@Override
	public void removeNodeFromMountPoint(Context paramContext, Case paramNodeArg, String paramString)
			throws MercuryException {
		getService().removeNodeFromMountPoint(paramContext, paramNodeArg, paramString);
	}

}