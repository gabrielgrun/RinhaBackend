FROM openjdk:17-jdk
ADD target/rinha-0.0.1-SNAPSHOT.jar /opt/
ADD src/main/resources/* /opt/config/

ENTRYPOINT ["java","-jar","/opt/rinha-0.0.1-SNAPSHOT.jar","-server","--spring.config.location=file:/opt/config/application.properties","-Xms256m","-Dfile.encoding=UTF-8"]