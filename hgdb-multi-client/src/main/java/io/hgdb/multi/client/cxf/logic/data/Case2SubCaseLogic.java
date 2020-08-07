package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.ICase2SubCaseAction;

@Service
public class Case2SubCaseLogic extends io.hgdb.mercury.client.cxf.logic.data.Case2SubCaseLogic {

	private static final long serialVersionUID = 1467487074352681581L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ICase2SubCaseAction getService(Context context) {
		return (ICase2SubCaseAction) httpClientDynamicRegistry.getBean(context, ICase2SubCaseAction.class);
	}
}
