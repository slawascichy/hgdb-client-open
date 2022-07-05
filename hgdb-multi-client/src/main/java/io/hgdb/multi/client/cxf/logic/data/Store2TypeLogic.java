package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.IStore2TypeAction;

@Service
public class Store2TypeLogic extends io.hgdb.mercury.client.cxf.logic.data.Store2TypeLogic {

	private static final long serialVersionUID = 873430715247289622L;
	
	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public IStore2TypeAction getService(Context context) {
		return (IStore2TypeAction) httpClientDynamicRegistry.getBean(context, IStore2TypeAction.class);
	}
}
