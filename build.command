#!/bin/bash

cd "$(dirname "$BASH_SOURCE")"/common-service/
mvn package
mvn install:install-file -Dfile=target/common-service-1.0-SNAPSHOT.jar -DpomFile=pom.xml

declare -a arr=("eureka-server/" 
                "gateway-service/"
                "auth-service/"
                "user-service/"
                "journal-service/"
                "diary-service/"
                "citizen-service/"
                "frontend"
                )

for i in "${arr[@]}"
do
  if [ $i == "frontend" ]
  then
  osascript <<END
  tell app "Terminal"
    do script "cd '$(dirname "$BASH_SOURCE")'/'$i'/ && npm install && npm start"
  end tell
END
  else
   osascript <<END
  tell app "Terminal"
    do script "cd '$(dirname "$BASH_SOURCE")'/'$i'/ && mvn spring-boot:run"
  end tell
END
fi
done