package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseHistoryStreamAction;

@Service
public class CaseHistoryStreamLogic extends io.hgdb.mercury.client.cxf.logic.data.CaseHistoryStreamLogic {

	private static final long serialVersionUID = 6467487074352681581L;

	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public ICaseHistoryStreamAction getService(Context context) {
		return httpClientDynamicRegistry.getBean(context, ICaseHistoryStreamAction.class);
	}
}
