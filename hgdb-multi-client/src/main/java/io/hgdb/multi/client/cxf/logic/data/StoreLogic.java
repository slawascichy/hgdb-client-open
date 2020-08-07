package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.IStoreAction;

@Service
public class StoreLogic extends io.hgdb.mercury.client.cxf.logic.data.StoreLogic {

	private static final long serialVersionUID = -5119399839681724986L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IStoreAction getService(Context context) {
		return (IStoreAction) httpClientDynamicRegistry.getBean(context, IStoreAction.class);
	}
}
