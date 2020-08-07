package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.ICommentAction;

@Service
public class CommentLogic extends io.hgdb.mercury.client.cxf.logic.data.CommentLogic {

	private static final long serialVersionUID = 7396960991744559785L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ICommentAction getService(Context context) {
		return (ICommentAction) httpClientDynamicRegistry.getBean(context, ICommentAction.class);
	}
}
