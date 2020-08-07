package io.hgdb.multi.client.cxf.logic.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.attr.ITypeCodeAction;

@Service
public class TypeCodeLogic extends io.hgdb.mercury.client.cxf.logic.attr.TypeCodeLogic {

	private static final long serialVersionUID = -4945169900051503084L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ITypeCodeAction getService(Context context) {
		return (ITypeCodeAction) httpClientDynamicRegistry.getBean(context, ITypeCodeAction.class);
	}
}
