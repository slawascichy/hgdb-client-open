package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.IParticipantAction;

@Service
public class ParticipantLogic extends io.hgdb.mercury.client.cxf.logic.data.ParticipantLogic {

	private static final long serialVersionUID = -3656531838374227079L;
	
	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public IParticipantAction getService(Context context) {
		return (IParticipantAction) httpClientDynamicRegistry.getBean(context, IParticipantAction.class);
	}
}
