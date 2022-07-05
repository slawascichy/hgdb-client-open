package io.hgdb.multi.client.cxf.logic.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.attr.IType2TypeAction;

@Service
public class Type2TypeLogic extends io.hgdb.mercury.client.cxf.logic.attr.Type2TypeLogic {

	private static final long serialVersionUID = -5848447742838991758L;
	
	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public IType2TypeAction getService(Context context) {
		return (IType2TypeAction) httpClientDynamicRegistry.getBean(context, IType2TypeAction.class);
	}

}
