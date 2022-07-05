package io.hgdb.multi.client.cxf.logic.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.attr.IParamDefinitionAction;

@Service
public class ParamDefinitionLogic extends io.hgdb.mercury.client.cxf.logic.attr.ParamDefinitionLogic {

	private static final long serialVersionUID = -8977564631728901916L;

	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public IParamDefinitionAction getService(Context context) {
		return (IParamDefinitionAction) httpClientDynamicRegistry.getBean(context, IParamDefinitionAction.class);
	}

}
