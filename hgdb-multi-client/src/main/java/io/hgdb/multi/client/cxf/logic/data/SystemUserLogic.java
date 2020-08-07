package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.ISystemUserAction;

@Service
public class SystemUserLogic extends io.hgdb.mercury.client.cxf.logic.data.SystemUserLogic {

	private static final long serialVersionUID = 6448443032813952547L;

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ISystemUserAction getService(Context context) {
		return (ISystemUserAction) httpClientDynamicRegistry.getBean(context, ISystemUserAction.class);
	}
}
