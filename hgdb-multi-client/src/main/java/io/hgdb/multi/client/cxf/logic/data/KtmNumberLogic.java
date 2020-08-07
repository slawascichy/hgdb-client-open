package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.IKtmNumberAction;

@Service
public class KtmNumberLogic extends io.hgdb.mercury.client.cxf.logic.data.KtmNumberLogic {

	private static final long serialVersionUID = -6068254603452772065L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IKtmNumberAction getService(Context context) {
		return (IKtmNumberAction) httpClientDynamicRegistry.getBean(context, IKtmNumberAction.class);
	}
}
