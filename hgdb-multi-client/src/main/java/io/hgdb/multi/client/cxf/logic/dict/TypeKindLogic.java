package io.hgdb.multi.client.cxf.logic.dict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.dict.ITypeKindAction;

@Service
public class TypeKindLogic extends io.hgdb.mercury.client.cxf.logic.dict.TypeKindLogic {

	private static final long serialVersionUID = 3539928708863806503L;
	
	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public ITypeKindAction getService(Context context) {
		return (ITypeKindAction) httpClientDynamicRegistry.getBean(context, ITypeKindAction.class);
	}

}
