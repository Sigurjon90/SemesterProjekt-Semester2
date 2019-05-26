@ECHO OFF
start cmd /k "cd %~dp0common-service && mvn package && mvn install:install-file -Dfile=target/common-service-1.0-SNAPSHOT.jar -DpomFile=pom.xml"
start cmd /k "cd %~dp0eureka-server && mvn spring-boot:run"
timeout 20
start cmd /k "cd %~dp0gateway-service && mvn spring-boot:run"
start cmd /k "cd %~dp0diary-service && mvn spring-boot:run"
start cmd /k "cd %~dp0auth-service && mvn spring-boot:run"
start cmd /k "cd %~dp0citizen-service && mvn spring-boot:run"
start cmd /k "cd %~dp0journal-service && mvn spring-boot:run"
start cmd /k "cd %~dp0user-service && mvn spring-boot:run"
start cmd /k "cd %~dp0frontend && npm install && npm start"

PAUSE
