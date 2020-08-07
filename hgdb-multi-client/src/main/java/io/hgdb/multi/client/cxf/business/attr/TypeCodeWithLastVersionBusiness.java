package io.hgdb.multi.client.cxf.business.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.business.attr.ITypeCodeWithLastVersionBusinessAction;

@Service
public class TypeCodeWithLastVersionBusiness
		extends io.hgdb.mercury.client.cxf.business.attr.TypeCodeWithLastVersionBusiness {

	private static final long serialVersionUID = -4416029802294033514L;

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ITypeCodeWithLastVersionBusinessAction getService(Context context) {
		return (ITypeCodeWithLastVersionBusinessAction) httpClientDynamicRegistry.getBean(context,
				ITypeCodeWithLastVersionBusinessAction.class);
	}

}
