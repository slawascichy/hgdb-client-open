#!/bin/bash

# Wysłanie tag'a do github'a
# git push origin <tag_name>

# Zmień wersje i uruchom skrypt. 
export RELEASE_VERSION=3.1.2

mvn versions:set -DnewVersion=$RELEASE_VERSION-SNAPSHOT

