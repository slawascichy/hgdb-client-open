package io.hgdb.multi.client.cxf.business.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.business.data.ICase2CaseBusinessAction;

@Service
public class Case2CaseBusiness extends io.hgdb.mercury.client.cxf.business.data.Case2CaseBusiness {

	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public ICase2CaseBusinessAction getService(Context context) {
		return (ICase2CaseBusinessAction) httpClientDynamicRegistry.getBean(context, ICase2CaseBusinessAction.class);
	}

}
