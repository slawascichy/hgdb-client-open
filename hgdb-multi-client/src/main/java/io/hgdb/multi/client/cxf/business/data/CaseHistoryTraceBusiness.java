package io.hgdb.multi.client.cxf.business.data;

import org.mercury.ws.server.api.actions.business.data.ICaseHistoryTraceBusinessAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;

@Service
public class CaseHistoryTraceBusiness extends io.hgdb.mercury.client.cxf.business.data.CaseHistoryTraceBusiness {

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ICaseHistoryTraceBusinessAction getService(Context context) {
		return (ICaseHistoryTraceBusinessAction) httpClientDynamicRegistry.getBean(context,
				ICaseHistoryTraceBusinessAction.class);
	}

}
