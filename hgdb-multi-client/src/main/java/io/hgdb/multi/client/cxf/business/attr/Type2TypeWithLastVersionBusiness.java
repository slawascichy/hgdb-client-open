package io.hgdb.multi.client.cxf.business.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.business.attr.IType2TypeWithLastVersionBusinessAction;

@Service
public class Type2TypeWithLastVersionBusiness
		extends io.hgdb.mercury.client.cxf.business.attr.Type2TypeWithLastVersionBusiness {

	private static final long serialVersionUID = -1025281692375217727L;
	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public IType2TypeWithLastVersionBusinessAction getService(Context context) {
		return (IType2TypeWithLastVersionBusinessAction) httpClientDynamicRegistry.getBean(context,
				IType2TypeWithLastVersionBusinessAction.class);
	}

}
