FROM alpine:latest
RUN apk add openjdk11
ADD build/libs/shopping.jar /usr/share/app.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/app.jar"]
