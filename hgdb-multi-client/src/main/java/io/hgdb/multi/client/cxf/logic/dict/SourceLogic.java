package io.hgdb.multi.client.cxf.logic.dict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.dict.ISourceAction;

@Service
public class SourceLogic extends io.hgdb.mercury.client.cxf.logic.dict.SourceLogic {

	private static final long serialVersionUID = -7653603302914108676L;

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ISourceAction getService(Context context) {
		return (ISourceAction) httpClientDynamicRegistry.getBean(context, ISourceAction.class);
	}

}
