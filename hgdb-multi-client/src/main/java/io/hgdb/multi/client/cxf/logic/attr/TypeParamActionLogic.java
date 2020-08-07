package io.hgdb.multi.client.cxf.logic.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.attr.ITypeParamActionAction;

@Service
public class TypeParamActionLogic extends io.hgdb.mercury.client.cxf.logic.attr.TypeParamActionLogic {

	private static final long serialVersionUID = -2986042564547983242L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ITypeParamActionAction getService(Context context) {
		return (ITypeParamActionAction) httpClientDynamicRegistry.getBean(context, ITypeParamActionAction.class);
	}

}
