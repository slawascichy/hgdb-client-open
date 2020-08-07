package io.hgdb.multi.client.cxf.logic.dict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.dict.IInitStatusAction;

@Service
public class InitStatusLogic extends io.hgdb.mercury.client.cxf.logic.dict.InitStatusLogic {

	private static final long serialVersionUID = -8951356321859559551L;
	
	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IInitStatusAction getService(Context context) {
		return (IInitStatusAction) httpClientDynamicRegistry.getBean(context, IInitStatusAction.class);
	}

}
