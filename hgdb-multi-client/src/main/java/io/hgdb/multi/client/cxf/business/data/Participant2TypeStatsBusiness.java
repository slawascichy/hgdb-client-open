package io.hgdb.multi.client.cxf.business.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.business.data.IParticipant2TypeStatsBusinessAction;

@Service
public class Participant2TypeStatsBusiness
		extends io.hgdb.mercury.client.cxf.business.data.Participant2TypeStatsBusiness {

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IParticipant2TypeStatsBusinessAction getService(Context context) {
		return (IParticipant2TypeStatsBusinessAction) httpClientDynamicRegistry.getBean(context,
				IParticipant2TypeStatsBusinessAction.class);
	}

}
