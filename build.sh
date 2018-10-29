#!/bin/sh
if [ -z "$1" ]; then
	mvn -T 4 install
else
	mvn -T 4 $@
fi
