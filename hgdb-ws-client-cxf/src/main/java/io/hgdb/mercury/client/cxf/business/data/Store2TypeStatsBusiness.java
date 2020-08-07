package io.hgdb.mercury.client.cxf.business.data;

import java.util.List;

import io.hgdb.mercury.client.cxf.WsClientRoot;
import pro.ibpm.mercury.business.data.api.IStore2TypeStatsBusiness;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.DtoObject;
import pro.ibpm.mercury.dto.Store2TypeStatsDto;
import pro.ibpm.mercury.entities.data.Store2TypePK;
import pro.ibpm.mercury.entities.data.Store2TypeStats;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.ws.server.api.actions.business.data.IStore2TypeStatsBusinessAction;
import pro.ibpm.mercury.ws.server.api.returns.WsStatus;
import pro.ibpm.mercury.ws.server.api.returns.data.WsStatusWithStore2TypeStatsDto;
import pro.ibpm.mercury.ws.server.api.returns.data.WsStatusWithStore2TypeStatsDtos;

/**
 * 
 * Store2TypeStatsBusiness
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public class Store2TypeStatsBusiness extends WsClientRoot<Store2TypeStats, Store2TypePK, IStore2TypeStatsBusinessAction>
		implements IStore2TypeStatsBusiness {

	@Override
	public void repairStats(Context context) throws MercuryException {
		WsStatus wsStatus = getService(context).repairStats(context);
		checkWsStatus(wsStatus);
	}

	@Override
	public Store2TypeStats sumByTypeName(Context context, String typeName) throws MercuryException {
		WsStatusWithStore2TypeStatsDto result = getService(context).sumByTypeName(context, typeName);
		return getEntity(context, result);
	}

	@Override
	public Store2TypeStats sumByTypeCode(Context context, String typeCode) throws MercuryException {
		WsStatusWithStore2TypeStatsDto result = getService(context).sumByTypeCode(context, typeCode);
		return getEntity(context, result);
	}

	@Override
	public List<Store2TypeStats> findByTypeName(Context context, String typeName) throws MercuryException {
		WsStatusWithStore2TypeStatsDtos result = getService(context).findByTypeName(context, typeName);
		return getEntityCollection(context, result);
	}

	@Override
	public List<Store2TypeStats> findByTypeCode(Context context, String typeCode) throws MercuryException {
		WsStatusWithStore2TypeStatsDtos result = getService(context).findByTypeCode(context, typeCode);
		return getEntityCollection(context, result);
	}

	@Override
	public Class<Store2TypeStats> getPersistentClass() {
		return Store2TypeStats.class;
	}

	@Override
	public Class<? extends DtoObject> getPersistentClassDto() {
		return Store2TypeStatsDto.class;
	}

}
