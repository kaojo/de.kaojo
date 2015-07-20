#!/bin/bash
set -e;

if [ "$1" = "${JBOSS_HOME}/bin/standlone.sh" ] ; then
  echo 'if'
  exec gosu $JBOSS_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 "$@"
fi
echo ' fi'
exec "$@"
