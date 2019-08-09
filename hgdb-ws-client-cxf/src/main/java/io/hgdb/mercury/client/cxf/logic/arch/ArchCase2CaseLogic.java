/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.arch;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientCatalogLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.arch.ArchCase;
import pro.ibpm.mercury.entities.arch.ArchCase2Case;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.arch.IArchCase2CaseLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.arch.IArchCase2CaseAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Karol Kowalczyk
 * 
 */
public class ArchCase2CaseLogic
		extends WsClientCatalogLogic<ArchCase2Case, String, ArchCase, Long, IArchCase2CaseAction>
		implements IArchCase2CaseLogic {

	private static final long serialVersionUID = 2834056673528006393L;

	@Override
	public ArchCase2Case insert(Context context, final ArchCase2Case e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public EntityList<ArchCase2Case, String> insertList(Context context, List<ArchCase2Case> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().insertBag(context, (Collection<ArchCase2Case>) eBag));
	}

	@Override
	public String remove(Context context, final ArchCase2Case e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<ArchCase2Case> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, (Collection<ArchCase2Case>) eBag), eBag);
	}

	@Override
	public ArchCase2Case find(Context context, final String pk) throws MercuryException {
		return getEntity(context, getService().find(context, pk));
	}

	@Override
	public ArchCase2Case findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public ArchCase2Case update(Context context, ArchCase2Case e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public EntityList<ArchCase2Case, String> updateList(Context context, List<ArchCase2Case> eBag)
			throws MercuryException {
		return getEntityCollection(context, getService().updateBag(context, (Collection<ArchCase2Case>) eBag));
	}

	@Override
	public List<ArchCase2Case> getAllParentsRelationshipsByNodeId(Context context, Long id, String mountPoint)
			throws MercuryException {
		return (List<ArchCase2Case>) getEntityCollection(context,
				getService().getAllParentsRelationshipsByNodeId(context, id, mountPoint));
	}

	@Override
	public List<ArchCase> getAllDependsNodesByNodes(Context context, List<ArchCase> nBag, String mountPoint)
			throws MercuryException {
		return (List<ArchCase>) getNodeList(context, getService().getAllDependsNodesByNodes(context, nBag, mountPoint));
	}

	@Override
	public List<ArchCase> getAllDependsNodes(Context context, ArchCase n, String mountPoint) throws MercuryException {
		return (List<ArchCase>) getNodeList(context, getService().getAllDependsNodes(context, n, mountPoint));
	}

	@Override
	public List<ArchCase> getAllChildrenNodes(Context context, ArchCase n, String mountPoint) throws MercuryException {
		return (List<ArchCase>) getNodeList(context, getService().getAllChildrenNodes(context, n, mountPoint));
	}

	@Override
	public List<ArchCase> getAllParentsNodes(Context context, ArchCase n, String mountPoint) throws MercuryException {
		return (List<ArchCase>) getNodeList(context, getService().getAllParentsNodes(context, n, mountPoint));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPagedResult<ArchCase2Case, IPage> filterPaged(Context context, ArchCase2Case e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService().filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<ArchCase2Case> getAllByPathStartsWith(Context context, String preffix, String mountPoint)
			throws MercuryException {
		return (List<ArchCase2Case>) getEntityCollection(context,
				getService().getAllByPathStartsWith(context, preffix, mountPoint));
	}

	@Override
	public List<ArchCase2Case> getAllChildrenRelationshipsByNodeId(Context context, Long id, String mountPoint)
			throws MercuryException {
		return (List<ArchCase2Case>) getEntityCollection(context,
				getService().getAllChildrenRelationshipsByNodeId(context, id, mountPoint));
	}

	@Override
	public List<ArchCase> getAllDependsNodesByNodesWithoutMe(Context context, List<ArchCase> nBag, String mountPoint)
			throws MercuryException {
		return (List<ArchCase>) getNodeList(context,
				getService().getAllDependsNodesByNodesWithoutMe(context, nBag, mountPoint));
	}

	@Override
	public List<ArchCase2Case> getAllDependsRelationshipsByNodes(Context context, List<ArchCase> nBag,
			String mountPoint) throws MercuryException {
		return (List<ArchCase2Case>) getEntityCollection(context,
				getService().getAllDependsRelationshipsByNodes(context, nBag, mountPoint));
	}

	@Override
	public List<ArchCase2Case> getAllDependsRelationshipsByNodesWithoutMe(Context context, List<ArchCase> nBag,
			String mountPoint) throws MercuryException {
		return (List<ArchCase2Case>) getEntityCollection(context,
				getService().getAllDependsRelationshipsByNodesWithoutMe(context, nBag, mountPoint));
	}

	@Override
	public List<ArchCase2Case> findByIdList(Context context, List<String> idList) throws MercuryException {
		return (List<ArchCase2Case>) getEntityCollection(context,
				getService().findByIdList(context, (Collection<String>) idList));
	}

	@Override
	public List<ArchCase2Case> findAll(Context context) throws MercuryException {
		return (List<ArchCase2Case>) getEntityCollection(context, getService().findAll(context));
	}

	@Override
	public ArchCase2Case addNodeToMountPoint(Context context, ArchCase paramNodeArg, String paramString)
			throws MercuryException {
		return getEntity(context, getService().addNodeToMountPoint(context, paramNodeArg, paramString));
	}

	@Override
	public void removeNodeFromMountPoint(Context context, ArchCase paramNodeArg, String paramString)
			throws MercuryException {
		getService().removeNodeFromMountPoint(context, paramNodeArg, paramString);
	}

}
