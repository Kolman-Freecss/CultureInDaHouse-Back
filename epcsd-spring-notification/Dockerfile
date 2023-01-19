FROM maven:3.6.3-jdk-11 as builder

WORKDIR /app

COPY . ./

RUN mvn package -Dmaven.test.skip.exec

FROM maven:3.6.3-jdk-11 as runner

COPY --from=builder /app/target/Notification.jar /app.jar

EXPOSE 18082/tcp

ENTRYPOINT ["java","-jar","/app.jar"]
