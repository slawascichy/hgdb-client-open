package io.hgdb.multi.client.cxf.business.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.IClientDynamicRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.business.data.IStore2TypeLastVersionBusinessAction;

@Service
public class Store2TypeLastVersionBusiness
		extends io.hgdb.mercury.client.cxf.business.data.Store2TypeLastVersionBusiness {

	private static final long serialVersionUID = 1257312697702742899L;

	@Autowired
	private IClientDynamicRegistry httpClientDynamicRegistry;

	@Override
	public IStore2TypeLastVersionBusinessAction getService(Context context) {
		return (IStore2TypeLastVersionBusinessAction) httpClientDynamicRegistry.getBean(context,
				IStore2TypeLastVersionBusinessAction.class);
	}

}
