package io.hgdb.multi.client.cxf.logic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.data.IGroupCase2ParticipantAction;

@Service 
public class GroupCase2ParticipantLogic extends io.hgdb.mercury.client.cxf.logic.data.GroupCase2ParticipantLogic {

	private static final long serialVersionUID = -7999897812268296753L;
	
	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public IGroupCase2ParticipantAction getService(Context context) {
		return (IGroupCase2ParticipantAction) httpClientDynamicRegistry.getBean(context,
				IGroupCase2ParticipantAction.class);
	}
}
