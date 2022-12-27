#!/bin/bash

export LIB_VERSION=`cat minor_version.txt`
export MVN_EXEC="mvn"

# nazwa profilu użytkownika
#export MAVEN_PROFILE=$1
#export MVN_EXEC="mvn --global-settings \"repo/settings.xml\" --settings \"maven/$MAVEN_PROFILE/settings.xml\""

installLibs()
{
	$MVN_EXEC versions:set -DnewVersion=$LIB_VERSION
	# tworzę domyślny pakiet: "POSTGRESQL"
	$MVN_EXEC clean install -Ddatabse.provider=POSTGRESQL -Dmaven.test.skip -Dmercury.static.version=$LIB_VERSION
	
	# kopuję pom.xml - START
	# hgdb-client-open
	echo "Kopiuję repo/io/hgdb/mercury/hgdb-client-open/$LIB_VERSION/hgdb-client-open-$LIB_VERSION.pom..."
	cp pom.xml repo/io/hgdb/mercury/hgdb-client-open/$LIB_VERSION/hgdb-client-open-$LIB_VERSION.pom
	# poszczególne moduły - START 
	for module in {"hgdb-client-mock","hgdb-multi-client","hgdb-ws-client-cxf"};
	do
	 echo "Kopiuję repo/io/hgdb/mercury/$module/$LIB_VERSION/$module-$LIB_VERSION.pom..."
	 cp $module/pom.xml repo/io/hgdb/mercury/$module/$LIB_VERSION/$module-$LIB_VERSION.pom
	done
	# poszczególne moduły - KONIEC 
	
	$MVN_EXEC versions:revert
}

installLibs
exit 0