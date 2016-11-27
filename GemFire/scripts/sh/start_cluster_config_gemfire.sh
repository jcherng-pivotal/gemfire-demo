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

# Run setenv to set the GemFire environment variables
source $SCRIPTPATH/setenv.sh

# start GemFire instances
(cd $WORKING_DIR && \
gfsh \
-e "set variable --name=CLASSPATH --value=$CLASSPATH" \
-e "set variable --name=LOCATOR_JAVA_OPT --value=$LOCATOR_JAVA_OPT" \
-e "set variable --name=LOCATOR_NAME --value=locator1" \
-e "set variable --name=LOCATOR_PORT --value=10334" \
-e "set variable --name=JMX_PORT --value=1099" \
-e "set variable --name=JMX_HTTP_PORT --value=7070" \
-e "run --file=$SCRIPTPATH/../gfsh/start_cluster_config_locator.gfsh" \
-e "set variable --name=LOCATOR_NAME --value=locator2" \
-e "set variable --name=LOCATOR_PORT --value=10335" \
-e "set variable --name=JMX_PORT --value=1199" \
-e "set variable --name=JMX_HTTP_PORT --value=7071" \
-e "run --file=$SCRIPTPATH/../gfsh/start_cluster_config_locator.gfsh" \
-e "set variable --name=SERVER_NAME --value=server1" \
-e "set variable --name=SERVER_PORT --value=40404" \
-e "set variable --name=SERVER_GROUP --value=group1" \
-e "set variable --name=HTTP_SERVICE_PORT --value=8080" \
-e "run --file=$SCRIPTPATH/../gfsh/start_cluster_config_server.gfsh" \
-e "set variable --name=SERVER_NAME --value=server2" \
-e "set variable --name=SERVER_PORT --value=40405" \
-e "set variable --name=SERVER_GROUP --value=group2" \
-e "set variable --name=HTTP_SERVICE_PORT --value=8081" \
-e "run --file=$SCRIPTPATH/../gfsh/start_cluster_config_server.gfsh" \
-e "list members" \
-e "list regions")
