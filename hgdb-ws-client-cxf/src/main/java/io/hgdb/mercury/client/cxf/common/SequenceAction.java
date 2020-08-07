package io.hgdb.mercury.client.cxf.common;

import org.mercury.dto.common.entities.WsStatusWithSequenceReturnValue;
import org.mercury.ws.server.api.actions.common.ISequenceAction;

import io.hgdb.mercury.client.cxf.WsClient;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.exceptions.MercuryException;

public class SequenceAction extends WsClient<ISequenceAction> implements ISequenceAction {

	@Override
	public WsStatusWithSequenceReturnValue incremetSequenceId(Context context, String seqClassName,
			Long incremetValue) {
		try {
			WsStatusWithSequenceReturnValue returnValue = getService(context).incremetSequenceId(context, seqClassName,
					incremetValue);
			checkWsStatus(returnValue);
			return returnValue;
		} catch (MercuryException e) {
			throw new IllegalStateException(e);
		}
	}

}
