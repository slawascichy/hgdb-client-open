package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseHistoryTraceAction;

@Service
public class CaseHistoryTraceLogic extends io.hgdb.mercury.client.cxf.logic.data.CaseHistoryTraceLogic {

	private static final long serialVersionUID = 5467487074352681581L;

	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public ICaseHistoryTraceAction getService(Context context) {
		return httpClientDynamicRegistry.getBean(context, ICaseHistoryTraceAction.class);
	}
}
