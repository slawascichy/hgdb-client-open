package io.hgdb.multi.client.cxf.business.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.business.data.ICaseBusinessAction;

@Service
public class CaseBusiness extends io.hgdb.mercury.client.cxf.business.data.CaseBusiness {

	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public ICaseBusinessAction getService(Context context) {
		return (ICaseBusinessAction) httpClientDynamicRegistry.getBean(context, ICaseBusinessAction.class);
	}

}
