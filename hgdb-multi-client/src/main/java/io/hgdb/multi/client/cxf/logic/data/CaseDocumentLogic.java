package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.ICaseDocumentAction;

@Service
public class CaseDocumentLogic extends io.hgdb.mercury.client.cxf.logic.data.CaseDocumentLogic {

	private static final long serialVersionUID = 7027595774801096616L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ICaseDocumentAction getService(Context context) {
		return (ICaseDocumentAction) httpClientDynamicRegistry.getBean(context, ICaseDocumentAction.class);
	}
}
