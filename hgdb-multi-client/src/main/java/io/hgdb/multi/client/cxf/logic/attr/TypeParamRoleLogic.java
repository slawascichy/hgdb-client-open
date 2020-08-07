package io.hgdb.multi.client.cxf.logic.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.attr.ITypeParamRoleAction;

@Service
public class TypeParamRoleLogic extends io.hgdb.mercury.client.cxf.logic.attr.TypeParamRoleLogic {

	private static final long serialVersionUID = -7131920022573259292L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ITypeParamRoleAction getService(Context context) {
		return (ITypeParamRoleAction) httpClientDynamicRegistry.getBean(context, ITypeParamRoleAction.class);
	}

}
