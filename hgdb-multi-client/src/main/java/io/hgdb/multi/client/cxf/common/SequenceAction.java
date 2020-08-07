package io.hgdb.multi.client.cxf.common;

import org.mercury.ws.server.api.actions.common.ISequenceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;

@Service
public class SequenceAction extends io.hgdb.mercury.client.cxf.common.SequenceAction {

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public ISequenceAction getService(Context context) {
		return (ISequenceAction) httpClientDynamicRegistry.getBean(context, ISequenceAction.class);
	}

}
