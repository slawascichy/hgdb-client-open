/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic.attr;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientCatalogLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.attr.Type2Type;
import pro.ibpm.mercury.entities.attr.TypeCode;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.attr.IType2TypeLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.attr.IType2TypeAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * 
 */
public class Type2TypeLogic extends WsClientCatalogLogic<Type2Type, TypeCode, String, IType2TypeAction>
		implements IType2TypeLogic {

	private static final long serialVersionUID = 6353138670210031878L;

	@Override
	public Type2Type insert(Context context, final Type2Type e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<Type2Type, String> insertList(Context context, List<Type2Type> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, (Collection<Type2Type>) eBag));
	}

	@Override
	public String remove(Context context, final Type2Type e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<Type2Type> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, (Collection<Type2Type>) eBag), eBag);
	}

	@Override
	public Type2Type find(Context context, final String pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public Type2Type findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public Type2Type update(Context context, Type2Type e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<Type2Type, String> updateList(Context context, List<Type2Type> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, (Collection<Type2Type>) eBag));
	}

	@Override
	public List<Type2Type> getAllParentsRelationshipsByNodeId(Context context, String n, String mountPoint)
			throws MercuryException {
		return (List<Type2Type>) getEntityCollection(context,
				getService(context).getAllParentsRelationshipsByNodeId(context, n, mountPoint));
	}

	@Override
	public List<Type2Type> getAllChildrenRelationshipsByNodeId(Context context, String n, String mountPoint)
			throws MercuryException {
		return (List<Type2Type>) getEntityCollection(context,
				getService(context).getAllChildrenRelationshipsByNodeId(context, n, mountPoint));
	}

	@Override
	public List<TypeCode> getAllDependsNodesByNodes(Context context, List<TypeCode> nBag, String mountPoint)
			throws MercuryException {
		return (List<TypeCode>) getNodeList(context,
				getService(context).getAllDependsNodesByNodes(context, nBag, mountPoint));
	}

	@Override
	public List<TypeCode> getAllDependsNodesByNodesWithoutMe(Context context, List<TypeCode> nBag, String mountPoint)
			throws MercuryException {
		return (List<TypeCode>) getNodeList(context,
				getService(context).getAllDependsNodesByNodesWithoutMe(context, nBag, mountPoint));
	}

	@Override
	public List<TypeCode> getAllDependsNodes(Context context, TypeCode n, String mountPoint) throws MercuryException {
		return (List<TypeCode>) getNodeList(context, getService(context).getAllDependsNodes(context, n, mountPoint));
	}

	@Override
	public List<TypeCode> getAllChildrenNodes(Context context, TypeCode n, String mountPoint) throws MercuryException {
		return (List<TypeCode>) getNodeList(context, getService(context).getAllChildrenNodes(context, n, mountPoint));
	}

	@Override
	public List<TypeCode> getAllParentsNodes(Context context, TypeCode n, String mountPoint) throws MercuryException {
		return (List<TypeCode>) getNodeList(context, getService(context).getAllParentsNodes(context, n, mountPoint));
	}

	@Override
	public List<Type2Type> getAllDependsRelationshipsByNodes(Context context, List<TypeCode> nBag, String mountPoint)
			throws MercuryException {
		return (List<Type2Type>) getEntityCollection(context,
				getService(context).getAllDependsRelationshipsByNodes(context, nBag, mountPoint));
	}

	@Override
	public List<Type2Type> getAllDependsRelationshipsByNodesWithoutMe(Context context, List<TypeCode> nBag,
			String mountPoint) throws MercuryException {
		return (List<Type2Type>) getEntityCollection(context,
				getService(context).getAllDependsRelationshipsByNodesWithoutMe(context, nBag, mountPoint));
	}

	@Override
	public List<Type2Type> getAllByPathStartsWith(Context context, String preffix, String mountPoint)
			throws MercuryException {
		return (List<Type2Type>) getEntityCollection(context,
				getService(context).getAllByPathStartsWith(context, preffix, mountPoint));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPagedResult<Type2Type, IPage> filterPaged(Context context, Type2Type e, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<Type2Type> findAll(Context context) throws MercuryException {
		return (List<Type2Type>) getEntityCollection(context, getService(context).findAll(context));
	}

	@Override
	public List<Type2Type> findByIdList(Context context, List<String> idList) throws MercuryException {
		return (List<Type2Type>) getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@Override
	public Type2Type addNodeToMountPoint(Context paramContext, TypeCode paramNodeArg, String paramString)
			throws MercuryException {
		return getEntity(paramContext,
				getService(paramContext).addNodeToMountPoint(paramContext, paramNodeArg, paramString));
	}

	@Override
	public void removeNodeFromMountPoint(Context paramContext, TypeCode paramNodeArg, String paramString)
			throws MercuryException {
		getService(paramContext).removeNodeFromMountPoint(paramContext, paramNodeArg, paramString);
	}

}
