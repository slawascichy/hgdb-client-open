package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.IQuickTaskAction;

@Service
public class QuickTaskLogic extends io.hgdb.mercury.client.cxf.logic.data.QuickTaskLogic {

	private static final long serialVersionUID = 392117914830455675L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IQuickTaskAction getService(Context context) {
		return (IQuickTaskAction) httpClientDynamicRegistry.getBean(context, IQuickTaskAction.class);
	}
}
