package io.hgdb.mercury.client.cxf.logic.cache;

import io.hgdb.mercury.client.cxf.WsClientRoot;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dto.DtoObject;
import pro.ibpm.mercury.dto.StatsInfoRecordDto;
import pro.ibpm.mercury.dto.paging.PageTransportable;
import pro.ibpm.mercury.entities.cache.StatsInfoRecord;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.api.cache.ICacheManagerLogic;
import pro.ibpm.mercury.logic.paging.IPage;
import pro.ibpm.mercury.logic.paging.IPagedResult;
import pro.ibpm.mercury.ws.server.api.actions.cache.ICacheManagerAction;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithPagedResult;

public class CacheManagerLogic extends WsClientRoot<StatsInfoRecord, String, ICacheManagerAction>
		implements ICacheManagerLogic {

	@Override
	public void clearCache(Context context, String cacheName) {
		getService(context).clearCache(context, cacheName);
	}

	@Override
	public void clearEntityCaches(Context context, String entityName) throws MercuryException {
		getService(context).clearEntityCaches(context, entityName);
	}

	@Override
	public IPagedResult<String, IPage> getCacheNames(Context context, IPage page) throws MercuryException {
		return getStringPagedResult(context, getService(context).getCacheNames(context, new PageTransportable(page)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPagedResult<StatsInfoRecord, IPage> getCachesStatistics(Context context, IPage page)
			throws MercuryException {
		return getPagedResult(context,
				(IWsStatusWithPagedResult) getService(context).getCachesStatistics(context, new PageTransportable(page)));
	}

	@Override
	public Class<StatsInfoRecord> getPersistentClass() {
		return StatsInfoRecord.class;
	}

	@Override
	public Class<? extends DtoObject> getPersistentClassDto() {
		return StatsInfoRecordDto.class;
	}

}
