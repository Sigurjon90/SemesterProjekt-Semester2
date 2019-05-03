#!/bin/bash

cd "$(dirname "$BASH_SOURCE")"/common-service/
mvn package
mvn install:install-file -Dfile=target/common-service-1.0-SNAPSHOT.jar -DpomFile=pom.xml

declare -a arr=("eureka-server/" 
                "gateway-service/"
                "auth-service/"
                "user-service/"
                "journal-service/"
                "diary-service"
                )

for i in "${arr[@]}"
do
   osascript <<END
  tell app "Terminal"
    do script "cd '$(dirname "$BASH_SOURCE")'/'$i'/ && mvn spring-boot:run"
  end tell
END
done