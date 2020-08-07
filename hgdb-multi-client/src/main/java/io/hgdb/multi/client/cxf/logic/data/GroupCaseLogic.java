package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.IGroupCaseAction;

@Service
public class GroupCaseLogic extends io.hgdb.mercury.client.cxf.logic.data.GroupCaseLogic {

	private static final long serialVersionUID = 2351888731623466712L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IGroupCaseAction getService(Context context) {
		return (IGroupCaseAction) httpClientDynamicRegistry.getBean(context, IGroupCaseAction.class);
	}
}
