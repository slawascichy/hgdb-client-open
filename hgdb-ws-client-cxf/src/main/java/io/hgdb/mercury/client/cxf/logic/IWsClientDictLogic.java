package io.hgdb.mercury.client.cxf.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.entities.MIdModifier;
import pro.ibpm.mercury.entities.beans.EntityList;
import pro.ibpm.mercury.exceptions.MercuryException;

public interface IWsClientDictLogic<E extends MEntity & MIdModifier<Pk>, Pk> {

	<EDto> EntityList<E, Pk> initCollection(Context context, Collection<EDto> dtoList)
			throws MercuryException, IllegalArgumentException, SecurityException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	<EDto> E initObj(Context context, EDto dtoObj)
			throws MercuryException, IllegalArgumentException, SecurityException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
}
