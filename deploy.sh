#!/bin/bash

export LIB_VERSION=`cat minor_version.txt`
export MVN_EXEC="mvn"

export REPOSITORY_URL=https://nexusrepo.scisoftware.pl/repository/sci-maven-releases
export REPOSITORY_ID=sci-maven-releases

deployLibs()
{

	# mercury-lib
	$MVN_EXEC deploy:deploy-file \
	 -Durl=${REPOSITORY_URL}\
	 -DrepositoryId=${REPOSITORY_ID}\
	 -Dfile=repo/io/hgdb/mercury/hgdb-client-open/$LIB_VERSION/hgdb-client-open-$LIB_VERSION.jar \
     -DpomFile=repo/io/hgdb/mercury/hgdb-client-open/$LIB_VERSION/hgdb-client-open-$LIB_VERSION.pom
	# poszczególne moduły - START 
	for module in {"hgdb-client-mock","hgdb-multi-client","hgdb-ws-client-cxf"};
	do
	$MVN_EXEC deploy:deploy-file \
	 -Durl=${REPOSITORY_URL}\
	 -DrepositoryId=${REPOSITORY_ID}\
	 -Dfile=repo/io/hgdb/mercury/${module}/$LIB_VERSION/$module-$LIB_VERSION.jar \
     -DpomFile=repo/io/hgdb/mercury/${module}/$LIB_VERSION/$module-$LIB_VERSION.pom
    done
    # poszczególne moduły - KONIEC
}

deployLibs
exit 0