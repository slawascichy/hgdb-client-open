package io.hgdb.mercury.client.cxf.logic.data;

import java.util.List;

import io.hgdb.mercury.client.cxf.logic.WsClientBigDataLogic;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.entities.data.Comment;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.data.ICommentLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.data.ICommentAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

/**
 * 
 * CommentLogic
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class CommentLogic extends WsClientBigDataLogic<Comment, Long, ICommentAction> implements ICommentLogic {

	private static final long serialVersionUID = -8612278630149298246L;

	@Override
	public Comment insert(Context context, final Comment e) throws MercuryException {
		return getEntity(context, getService(context).insert(context, e));
	}

	@Override
	public EntityList<Comment, Long> insertList(Context context, List<Comment> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).insertBag(context, eBag));
	}

	@Override
	public Long remove(Context context, final Comment e) throws MercuryException {
		return getId(getService(context).remove(context, e), e);
	}

	@Override
	public List<Long> removeList(Context context, final List<Comment> eBag) throws MercuryException {
		return getIds(getService(context).removeBag(context, eBag), eBag);
	}

	@Override
	public Comment find(Context context, final Long pk) throws MercuryException {
		return getEntity(context, getService(context).findByKey(context, pk));
	}

	@Override
	public Comment findFirst(Context context) throws MercuryException {
		return getEntity(context, getService(context).findFirst(context));
	}

	@Override
	public Comment update(Context context, Comment e) throws MercuryException {
		return getEntity(context, getService(context).update(context, e));
	}

	@Override
	public EntityList<Comment, Long> updateList(Context context, List<Comment> eBag) throws MercuryException {
		return getEntityCollection(context, getService(context).updateBag(context, eBag));
	}

	@Override
	public List<Comment> findByIdList(Context context, List<Long> idList) throws MercuryException {
		return getEntityCollection(context, getService(context).findByKeyBag(context, idList));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPagedResult<Comment, IPage> filterPaged(Context context, Comment e, IPage page) throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).filterPaged(context, e, (PageTransportable) page));
	}

	@Override
	public List<Comment> findAll(Context context) throws MercuryException {
		return getEntityCollection(context, getService(context).findAll(context));
	}

}
