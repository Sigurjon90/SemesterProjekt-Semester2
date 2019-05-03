#!/bin/bash
mydir="$(dirname "$BASH_SOURCE")"
#SET DEV_HOME=%cd%

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

#cd "$(dirname "$BASH_SOURCE")"/eureka-server/

#cd %DEV_HOME%\gateway-service\
#call mvn spring-boot:run

#cd %DEV_HOME%\auth-service\
#call mvn spring-boot:run

#cd %DEV_HOME%\user-service\
#call mvn spring-boot:run

#cd %DEV_HOME%\journal-service\
#call mvn spring-boot:run

#cd %DEV_HOME%\diary-service\
#call mvn spring-boot:run