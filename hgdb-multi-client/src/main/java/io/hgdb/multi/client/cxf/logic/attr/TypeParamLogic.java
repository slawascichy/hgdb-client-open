package io.hgdb.multi.client.cxf.logic.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.attr.ITypeParamAction;

@Service
public class TypeParamLogic extends io.hgdb.mercury.client.cxf.logic.attr.TypeParamLogic {

	private static final long serialVersionUID = -4906236986253882078L;
	
	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public ITypeParamAction getService(Context context) {
		return (ITypeParamAction) httpClientDynamicRegistry.getBean(context, ITypeParamAction.class);
	}

}
