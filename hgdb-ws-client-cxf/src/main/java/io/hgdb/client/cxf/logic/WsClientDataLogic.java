/**
 * 
 */
package io.hgdb.client.cxf.logic;

import java.util.List;
import java.util.Map;

import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.entities.MIdModifier;
import pro.ibpm.mercury.entities.data.MUser;
import pro.ibpm.mercury.entities.dict.MRole;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.MDataLogic;
import pro.ibpm.mercury.logic.MModifyInfoHelper;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.ws.server.api.actions.IActionNVP;
import pro.ibpm.mercury.ws.server.api.actions.IActionRoot;

/**
 * 
 * WsClientDataLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 * @param <E>
 * @param <Pk>
 * @param <Ws>
 */
@SuppressWarnings("serial")
public abstract class WsClientDataLogic<E extends MEntity & MIdModifier<Pk>, Pk, Ws extends IActionRoot & IActionNVP>
		extends WsClientDictLogic<E, Pk, Ws> implements MDataLogic<E, Pk> {

	@Override
	public List<E> filter(Context context, Map<String, Object> sqlParams) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public <U extends MUser<U>, R extends MRole<R>> MModifyInfoHelper<U, R> getModifyInfoHelper() {
		throw new IllegalAccessError();
	}

}
