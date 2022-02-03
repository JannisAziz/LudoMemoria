FROM openjdk:17

LABEL JannisAziz="aziz_khammas@yahoo.de"

ADD backend/target/ludomemoria.jar ludomemoria.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$URI -jar /ludomemoria.jar" ]