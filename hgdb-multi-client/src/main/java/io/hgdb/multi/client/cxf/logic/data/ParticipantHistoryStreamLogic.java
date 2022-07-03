package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.IParticipantHistoryStreamAction;

@Service
public class ParticipantHistoryStreamLogic extends io.hgdb.mercury.client.cxf.logic.data.ParticipantHistoryStreamLogic {

	private static final long serialVersionUID = 4467487074352681581L;

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IParticipantHistoryStreamAction getService(Context context) {
		return httpClientDynamicRegistry.getBean(context, IParticipantHistoryStreamAction.class);
	}
}
