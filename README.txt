##########################
Publiczny projekt klienta Java dla serwera Mercury DB (HgDB)
##########################

Zobacz licencję: http://hgdb.io/HGDB_Client_LICENSE.1.0.txt

# Instalacja
export USER_NAME=slawas
export MAVEN_SETTINGS="--global-settings \"repo/settings.xml\" --settings \"maven/$USER_NAME/settings.xml\""
mvn $MAVEN_SETTINGS clean install -Dmaven.test.skip

Utworzone biblioteki możesz znaleźć w odpowiednim katalogu lokalnego repozytorium:
1. repo/io/hgdb/mercury/hgdb-client-mock
2. repo/io/hgdb/mercury/hgdb-client-open
3. repo/io/hgdb/mercury/hgdb-ws-client-cxf


##########################
Przykład konfiguracji maven
##########################
Znajdziesz w projekcie:
1. ustawienia globalne --global-settings "repo/settings.xml"
2. ustawienia indywidualne --settings "maven/slawas/settings.xml"

# ustawienie parametrów maven'a wymaga by dodać do aktywnego profilu parametry:
(pamiętaj o swojej nazwie użytkownika, tag <user>)
<properties>
	<!-- nazwa użytkownika -->
	<user>nazwa_użytkownika</user>
	<!-- ścieżka bazowa workspace -->
	<local-project-workspace-path>D:\workspace\git</local-project-workspace-path>
	<!-- ścieżka w której przechowywane są biblioteki dla scope:system -->
	<local-lib-path>D:\workspace\lib</local-lib-path>
	<!-- ścieżka domowa Java -->
	<local-java-home-path>D:\MojeNarzedzia\Java\jdk1.6.0_37</local-java-home-path>
	<!-- 
	   ścieżka do katalogu, w którym przechowywane będą przestrzenie nazw JNDI
	   niezbędne do realizacji testów 
	-->
	<local-java-naming-provider-url>file:/D:\JNDI-Directory</local-java-naming-provider-url>
</properties>

# Do testów potrzebna jest możliwość połączenia do serwera Mercury DB (HgDB)
# Zobacz:
# /hgdb-ws-client-cxf/src/test/resources-filtered/properties/scichy/test.properties
