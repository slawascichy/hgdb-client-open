package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseAction;

@Service
public class CaseLogic extends io.hgdb.mercury.client.cxf.logic.data.CaseLogic {

	private static final long serialVersionUID = 2261371623289052495L;

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ICaseAction getService(Context context) {
		return (ICaseAction) httpClientDynamicRegistry.getBean(context, ICaseAction.class);
	}

}
