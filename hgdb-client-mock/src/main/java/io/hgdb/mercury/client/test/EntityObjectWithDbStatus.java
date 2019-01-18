package io.hgdb.mercury.client.test;

import java.io.Serializable;

/**
 * 
 * EntityObjectWithDbStatus obiekt encji ze statusem czy istnieje/istniała w
 * bazie danych.
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class EntityObjectWithDbStatus implements Serializable {

	private static final long serialVersionUID = 5190063640033020758L;

	private final Object entityObject;

	private final EntityDbStatus dbStatus;

	public EntityObjectWithDbStatus(Object entityObject, EntityDbStatus dbStatus) {
		super();
		this.entityObject = entityObject;
		this.dbStatus = dbStatus;
	}

	/**
	 * @return the {@link #entityObject}
	 */
	public Object getEntityObject() {
		return entityObject;
	}

	/**
	 * @return the {@link #dbStatus}
	 */
	public EntityDbStatus getDbStatus() {
		return dbStatus;
	}

}
