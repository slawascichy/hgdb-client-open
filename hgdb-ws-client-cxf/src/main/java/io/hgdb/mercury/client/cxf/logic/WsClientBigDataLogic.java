/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic;

import java.util.List;
import java.util.Map;

import pl.slawas.entities._ICopyable;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.entities.MIdModifier;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.MBigDataLogic;
import pro.ibpm.mercury.logic.exceptions.LC025MethodNotSupportedException;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.IActionNVP;
import pro.ibpm.mercury.ws.server.api.actions.IActionRoot;

/**
 * 
 * WsClientBigDataLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 * @param <E>
 * @param <Pk>
 * @param <Ws>
 */
@SuppressWarnings("serial")
public abstract class WsClientBigDataLogic<E extends MIdModifier<Pk> & _ICopyable<E> & MEntity, Pk, Ws extends IActionRoot & IActionNVP>
		extends WsClientDataLogic<E, Pk, Ws> implements MBigDataLogic<E, Pk> {

	@Override
	public List<E> filter(Context context, E entityObject) throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

	@Override
	public IPagedResult<E, IPage> filter(Context context, Map<String, Object> sqlParams, IPage page)
			throws MercuryException {
		throw new LC025MethodNotSupportedException();
	}

}