#!/bin/bash

OPTIONS=""

if [ "$1" = '-q' -o  "$1" = '--quick' ]; then
	OPTIONS="-DskipTests"
fi

mvn install $OPTIONS
