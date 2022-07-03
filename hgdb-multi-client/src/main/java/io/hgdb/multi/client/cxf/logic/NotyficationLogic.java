package io.hgdb.multi.client.cxf.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.INotyficationAction;

@Service
public class NotyficationLogic extends io.hgdb.mercury.client.cxf.logic.NotyficationLogic {

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public INotyficationAction getService(Context context) {
		return httpClientDynamicRegistry.getBean(context, INotyficationAction.class);
	}

}
