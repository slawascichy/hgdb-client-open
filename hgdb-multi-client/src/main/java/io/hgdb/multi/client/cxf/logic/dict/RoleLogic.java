package io.hgdb.multi.client.cxf.logic.dict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.dict.IRoleAction;

@Service
public class RoleLogic extends io.hgdb.mercury.client.cxf.logic.dict.RoleLogic {

	private static final long serialVersionUID = -8640217594107770954L;
	
	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public IRoleAction getService(Context context) {
		return (IRoleAction) httpClientDynamicRegistry.getBean(context, IRoleAction.class);
	}

}
