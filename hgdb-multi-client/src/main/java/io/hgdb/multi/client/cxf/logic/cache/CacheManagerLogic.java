package io.hgdb.multi.client.cxf.logic.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.cache.ICacheManagerAction;

@Service
public class CacheManagerLogic extends io.hgdb.mercury.client.cxf.logic.cache.CacheManagerLogic {

	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public ICacheManagerAction getService(Context context) {
		return (ICacheManagerAction) httpClientDynamicRegistry.getBean(context, ICacheManagerAction.class);
	}

}
