/**
 * 
 */
package io.hgdb.mercury.client.cxf.business.attr;

import java.util.Collection;
import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientCatalogLogic;
import pro.ibpm.mercury.business.attr.api.IType2TypeWithLastVersionBusiness;
import pro.ibpm.mercury.business.entities.Type2TypeWithLastVersion;
import pro.ibpm.mercury.business.entities.TypeCodeWithLastVersion;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.business.attr.IType2TypeWithLastVersionBusinessAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;

/**
 * 
 * Type2TypeWithLastVersionBusiness
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class Type2TypeWithLastVersionBusiness extends
		WsClientCatalogLogic<Type2TypeWithLastVersion, String, TypeCodeWithLastVersion, String, IType2TypeWithLastVersionBusinessAction>
		implements IType2TypeWithLastVersionBusiness {

	private static final long serialVersionUID = 6353138670210031878L;

	@Override
	public Type2TypeWithLastVersion insert(Context context, final Type2TypeWithLastVersion e) throws MercuryException {
		return getEntity(context, getService().insert(context, e));
	}

	@Override
	public List<Type2TypeWithLastVersion> insertList(Context context, List<Type2TypeWithLastVersion> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService().insertBag(context, (Collection<Type2TypeWithLastVersion>) eBag));
	}

	@Override
	public String remove(Context context, final Type2TypeWithLastVersion e) throws MercuryException {
		return getId(getService().remove(context, e), e);
	}

	@Override
	public List<String> removeList(Context context, final List<Type2TypeWithLastVersion> eBag) throws MercuryException {
		return getIds(getService().removeBag(context, (Collection<Type2TypeWithLastVersion>) eBag), eBag);
	}

	@Override
	public Type2TypeWithLastVersion find(Context context, final String pk) throws MercuryException {
		return getEntity(context, getService().findByKey(context, pk));
	}

	@Override
	public Type2TypeWithLastVersion findFirst(Context context) throws MercuryException {
		return getEntity(context, getService().findFirst(context));
	}

	@Override
	public Type2TypeWithLastVersion update(Context context, Type2TypeWithLastVersion e) throws MercuryException {
		return getEntity(context, getService().update(context, e));
	}

	@Override
	public List<Type2TypeWithLastVersion> updateList(Context context, List<Type2TypeWithLastVersion> eBag)
			throws MercuryException {
		return getEntityCollection(context,
				getService().updateBag(context, (Collection<Type2TypeWithLastVersion>) eBag));
	}

	@Override
	public List<Type2TypeWithLastVersion> getAllParentsRelationshipsByNodeId(Context context, String n,
			String mountPoint) throws MercuryException {
		return (List<Type2TypeWithLastVersion>) getEntityCollection(context,
				getService().getAllParentsRelationshipsByNodeId(context, n, mountPoint));
	}

	@Override
	public List<Type2TypeWithLastVersion> getAllChildrenRelationshipsByNodeId(Context context, String n,
			String mountPoint) throws MercuryException {
		return (List<Type2TypeWithLastVersion>) getEntityCollection(context,
				getService().getAllChildrenRelationshipsByNodeId(context, n, mountPoint));
	}

	@Override
	public List<TypeCodeWithLastVersion> getAllDependsNodesByNodes(Context context, List<TypeCodeWithLastVersion> nBag,
			String mountPoint) throws MercuryException {
		return (List<TypeCodeWithLastVersion>) getNodeList(context,
				getService().getAllDependsNodesByNodes(context, nBag, mountPoint));
	}

	@Override
	public List<TypeCodeWithLastVersion> getAllDependsNodesByNodesWithoutMe(Context context,
			List<TypeCodeWithLastVersion> nBag, String mountPoint) throws MercuryException {
		return (List<TypeCodeWithLastVersion>) getNodeList(context,
				getService().getAllDependsNodesByNodesWithoutMe(context, nBag, mountPoint));
	}

	@Override
	public List<TypeCodeWithLastVersion> getAllDependsNodes(Context context, TypeCodeWithLastVersion n,
			String mountPoint) throws MercuryException {
		return (List<TypeCodeWithLastVersion>) getNodeList(context,
				getService().getAllDependsNodes(context, n, mountPoint));
	};

	@Override
	public List<TypeCodeWithLastVersion> getAllChildrenNodes(Context context, TypeCodeWithLastVersion n,
			String mountPoint) throws MercuryException {
		return (List<TypeCodeWithLastVersion>) getNodeList(context,
				getService().getAllChildrenNodes(context, n, mountPoint));
	};

	@Override
	public List<TypeCodeWithLastVersion> getAllParentsNodes(Context context, TypeCodeWithLastVersion n,
			String mountPoint) throws MercuryException {
		return (List<TypeCodeWithLastVersion>) getNodeList(context,
				getService().getAllParentsNodes(context, n, mountPoint));
	};

	@Override
	public List<Type2TypeWithLastVersion> getAllDependsRelationshipsByNodes(Context context,
			List<TypeCodeWithLastVersion> nBag, String mountPoint) throws MercuryException {
		return (List<Type2TypeWithLastVersion>) getEntityCollection(context,
				getService().getAllDependsRelationshipsByNodes(context, nBag, mountPoint));
	}

	@Override
	public List<Type2TypeWithLastVersion> getAllDependsRelationshipsByNodesWithoutMe(Context context,
			List<TypeCodeWithLastVersion> nBag, String mountPoint) throws MercuryException {
		return (List<Type2TypeWithLastVersion>) getEntityCollection(context,
				getService().getAllDependsRelationshipsByNodesWithoutMe(context, nBag, mountPoint));
	}

	@Override
	public List<Type2TypeWithLastVersion> getAllByPathStartsWith(Context context, String preffix)
			throws MercuryException {
		return (List<Type2TypeWithLastVersion>) getEntityCollection(context,
				getService().getAllByPathStartsWith(context, preffix));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPagedResult<Type2TypeWithLastVersion, IPage> filterPaged(Context context, Type2TypeWithLastVersion e,
			IPage page) throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService().filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<Type2TypeWithLastVersion> findAll(Context context) throws MercuryException {
		return (List<Type2TypeWithLastVersion>) getEntityCollection(context, getService().findAll(context));
	}

	@Override
	public List<Type2TypeWithLastVersion> findByIdList(Context context, List<String> idList) throws MercuryException {
		return (List<Type2TypeWithLastVersion>) getEntityCollection(context,
				getService().findByKeyBag(context, idList));
	}

	@Override
	public Type2TypeWithLastVersion addNodeToMountPoint(Context paramContext, TypeCodeWithLastVersion paramNodeArg,
			String paramString) throws MercuryException {
		return getEntity(paramContext, getService().addNodeToMountPoint(paramContext, paramNodeArg, paramString));
	}

	@Override
	public void removeNodeFromMountPoint(Context paramContext, TypeCodeWithLastVersion paramNodeArg, String paramString)
			throws MercuryException {
		WsStatus wsStatus = getService().removeNodeFromMountPoint(paramContext, paramNodeArg, paramString);
		checkWsStatus(wsStatus);
	}

}
