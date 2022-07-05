package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.ICase2CaseAction;

@Service
public class Case2CaseLogic extends io.hgdb.mercury.client.cxf.logic.data.Case2CaseLogic {

	private static final long serialVersionUID = 7467487074352681581L;

	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public ICase2CaseAction getService(Context context) {
		return httpClientDynamicRegistry.getBean(context, ICase2CaseAction.class);
	}
}
