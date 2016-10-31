#!/bin/bash 
# Absolute path to this script, e.g. /home/user/bin/foo.sh
SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ]; do # resolve $SOURCE until the file is no longer a symlink
  DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  [[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done
SCRIPTPATH="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
echo $SCRIPTPATH

export JAVA_HOME="$(/usr/libexec/java_home -v 1.8)"
export GEMFIRE=~/pivotal/Pivotal_GemFire_821_b18207_Linux/
export WORKING_DIR=$SCRIPTPATH/../..
export CONF_DIR=$WORKING_DIR/conf
export LIB_DIR=$WORKING_DIR/lib
export APP_DIR=$WORKING_DIR/app
export APP_CONF_DIR=$APP_DIR/conf
export APP_LIB_DIR=$APP_DIR/lib
export CLASSPATH=$LIB_DIR/*:$APP_LIB_DIR/*
export PATH=$PATH:$GEMFIRE/bin

export LOCATOR_JAVA_OPT="-server"

export CRITICAL_HEAP_P=90
export EVICTION_HEAP_P=70
export GF_JAVA_OPT=-server,-verbose:gc,-XX:+PrintGCTimeStamps,-XX:+PrintGCDetails,-Xloggc:gc.log,-XX:+UseConcMarkSweepGC,-XX:+UseParNewGC,-XX:CMSInitiatingOccupancyFraction=60,-XX:+UseCompressedOops
