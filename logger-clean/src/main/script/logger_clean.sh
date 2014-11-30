#!/bin/sh
CLASSPATH=../conf
for i in `ls ../lib/*.jar`
do
        CLASSPATH=${CLASSPATH}:${i}
done

nohup java -classpath ${CLASSPATH} -server -Xms16m -Xmx32m ${JVM_OPTS} com.zcloud.logger.clean.LoggerCleanBootstrap logger-clean.yml 1>../log/logger_clean.log 2>&1

