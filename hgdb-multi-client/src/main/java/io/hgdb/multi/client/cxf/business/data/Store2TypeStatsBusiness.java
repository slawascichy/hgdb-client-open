package io.hgdb.multi.client.cxf.business.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.business.data.IStore2TypeStatsBusinessAction;

@Service
public class Store2TypeStatsBusiness extends io.hgdb.mercury.client.cxf.business.data.Store2TypeStatsBusiness {

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IStore2TypeStatsBusinessAction getService(Context context) {
		return (IStore2TypeStatsBusinessAction) httpClientDynamicRegistry.getBean(context,
				IStore2TypeStatsBusinessAction.class);
	}

}
