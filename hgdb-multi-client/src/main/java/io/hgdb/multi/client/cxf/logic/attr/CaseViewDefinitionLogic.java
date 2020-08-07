package io.hgdb.multi.client.cxf.logic.attr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.attr.ICaseViewDefinitionAction;

@Service
public class CaseViewDefinitionLogic extends io.hgdb.mercury.client.cxf.logic.attr.CaseViewDefinitionLogic {

	private static final long serialVersionUID = -1204481938920224724L;

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ICaseViewDefinitionAction getService(Context context) {
		return (ICaseViewDefinitionAction) httpClientDynamicRegistry.getBean(context, ICaseViewDefinitionAction.class);
	}

}
