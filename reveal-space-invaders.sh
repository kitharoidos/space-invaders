#!/bin/bash
java -jar -Xmx2G -Dorg.slf4j.simpleLogger.defaultLogLevel=error --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=java.base/sun.nio.ch=ALL-UNNAMED target/space-invaders.jar "$@"