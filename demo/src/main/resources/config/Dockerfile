FROM eclipse-temurin:17-jdk-alpine

ENV WORKDIR /app

COPY target/${project.artifactId}-${project.version}.jar ${project.artifactId}-${project.version}.jar

RUN groupadd demogroup -g 10100 && useradd demo -u 10101 -g demogroup

RUN mkdir -p ${WORKDIR}
RUN chmod a+w -R ${WORKDIR}

USER demo
WORKDIR ${WORKDIR}

# Define the healthcheck command
HEALTHCHECK --interval=10s --timeout=5s --start-period=15s \ 
   CMD curl --fail localhost:8080/health || exit 1

# Expose port 8080 for the application to listen on
EXPOSE 8080

ENTRYPOINT ["java", "-XshowSettings:vm", "-XX:MaxRAMPercentage=70.0", "-Djava.security.egd=file:/dev/./urandom",\
    "-jar", "${project.artifactId}-${project.version}.jar"]