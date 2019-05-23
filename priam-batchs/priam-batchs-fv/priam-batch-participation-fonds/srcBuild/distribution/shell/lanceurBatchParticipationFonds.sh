#!/bin/sh
# ==================================================================================
# Script de lancement du batch endor
# ==================================================================================
# Version - JJ/MM/AAAA - Auteur        	- Objet
# ==================================================================================
# 1.0.0   - 01/02/2018 - PH
# ==================================================================================
#

# ==================================================================================
# BATCH_HOME
#
BATCH_HOME=`dirname $0`/..
JAVA_HOME=/usr/java/JDK8
SACEM_HOME=/work/working/SYSTEM/LDAP
SACEM_CONFIG="-DSACEM_HOME=${SACEM_HOME}"

# ==================================================================================
# JAVACMD
#
if [ -z "${JAVACMD}" ] ; then
  if [ -n "$J{"  ] ; then
    if [ -x "${JAVA_HOME}/jre/sh/java" ] ; then
      JAVACMD=${JAVA_HOME}/jre/sh/java
    else
      JAVACMD=${JAVA_HOME}/bin/java
    fi
  else
    JAVACMD=java
  fi
fi

if [ ! -x "${JAVACMD}" ] ; then
  echo "Error: JAVA_HOME is not defined correctly."
  echo "  We cannot execute ${JAVACMD}"
  exit
fi

# ==================================================================================
# LOCALCLASSPATH
#
LOCALCLASSPATH=${BATCH_HOME}/config
LOCALCLASSPATH=${BATCH_HOME}/bin:${LOCALCLASSPATH}
DIRLIBS=${BATCH_HOME}/lib/*.jar
for i in ${DIRLIBS}
do
    if [ "$i" != "${DIRLIBS}" ] ; then
      if [ -z "${LOCALCLASSPATH}" ] ; then
        LOCALCLASSPATH=$i
      else
        LOCALCLASSPATH="$i":${LOCALCLASSPATH}
      fi
    fi
done

# ==================================================================================
# Executer le batch

echo ${LOCALCLASSPATH}
echo ${SACEM_CONFIG}

$JAVACMD -jar -Dspring.profiles.active=production /usr1/priam/BatchParticipationFonds/bin/priam-batch-participation-fonds.jar
# Memoriser le numero de processus du programme java
CHILD_PID=$

# Intercepter les demandes d'arret du batch
# et les transmettre sous forme de SIGINT (Ctrl-C) au programme Java
trap 'kill -15 $CHILD_PID; echo "received signal SIGTERM"; exit 1;' 15
trap 'kill -15 $CHILD_PID; echo "received signal SIGTERM"; exit 1;' 2

# Attendre la fin du batch java
# Le code de sortie du batch Java sera egalement celui du shell
wait $CHILD_PID
