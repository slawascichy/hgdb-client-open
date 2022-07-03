package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.IParticipantHistoryTraceAction;

@Service
public class ParticipantHistoryTraceLogic extends io.hgdb.mercury.client.cxf.logic.data.ParticipantHistoryTraceLogic {

	private static final long serialVersionUID = 3467487074352681581L;

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IParticipantHistoryTraceAction getService(Context context) {
		return httpClientDynamicRegistry.getBean(context, IParticipantHistoryTraceAction.class);
	}
}
