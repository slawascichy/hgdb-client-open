package io.hgdb.multi.client.cxf.logic.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.attr.ITypeCaseAction;

@Service
public class TypeCaseLogic extends io.hgdb.mercury.client.cxf.logic.attr.TypeCaseLogic {

	private static final long serialVersionUID = -4418073597594437019L;
	
	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public ITypeCaseAction getService(Context context) {
		return (ITypeCaseAction) httpClientDynamicRegistry.getBean(context, ITypeCaseAction.class);
	}

}
