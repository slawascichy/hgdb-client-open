package io.hgdb.multi.client.cxf.business.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.business.data.ICaseHistoryStreamBusinessAction;

@Service
public class CaseHistoryStreamBusiness extends io.hgdb.mercury.client.cxf.business.data.CaseHistoryStreamBusiness {

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ICaseHistoryStreamBusinessAction getService(Context context) {
		return (ICaseHistoryStreamBusinessAction) httpClientDynamicRegistry.getBean(context,
				ICaseHistoryStreamBusinessAction.class);
	}

}
