package io.hgdb.mercury.client.test;

public enum EntityDbStatus {

	/** nowy obiekt nie istniejący w bazie danych */
	NEW,
	/** obiekt istniejący już w bazie danych podczas wykonywania testów */
	EXISTS,
	/** obiekt został usunięty podczas przeprowadzania testów */
	DELETED;
}
