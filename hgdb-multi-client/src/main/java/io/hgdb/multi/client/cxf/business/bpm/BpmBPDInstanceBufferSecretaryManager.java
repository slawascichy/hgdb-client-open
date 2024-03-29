package io.hgdb.multi.client.cxf.business.bpm;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hgdb.multi.client.config.ClientConfigParams;
import io.hgdb.multi.client.context.ClientContextParams;
import io.hgdb.multi.client.registry.api.IHttpInvokerProxyFactoryRegistry;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.ws.server.api.actions.business.bpm.IBpmBPDInstanceBufferSecretaryManagerAction;

@Service
public class BpmBPDInstanceBufferSecretaryManager
		extends io.hgdb.mercury.client.cxf.business.bpm.BpmBPDInstanceBufferSecretaryManager {

	@Autowired
	private IHttpInvokerProxyFactoryRegistry httpClientDynamicRegistry;

	@Override
	public IBpmBPDInstanceBufferSecretaryManagerAction getService(Context context) {
		String defaultInstance = MercuryConfig.getInstance().get(ClientConfigParams.WS_SERVICE_DEFAULT_PROP);
		if (StringUtils.isBlank(defaultInstance)) {
			defaultInstance = ClientConfigParams.WS_DEFAULT_SERVICE;
		}
		context.setAdditionalPropertyValue(ClientContextParams.HTTP_INVOCER_PROXY_FACTORY_INSTANCE_NAME,
				defaultInstance);
		return (IBpmBPDInstanceBufferSecretaryManagerAction) httpClientDynamicRegistry.getBean(context,
				IBpmBPDInstanceBufferSecretaryManagerAction.class);
	}

}
