#!/bin/sh
if [ -z "$1" ]; then
	mvn -T 8 install
else
	mvn -T 8 $@
fi
