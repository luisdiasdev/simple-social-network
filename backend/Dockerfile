FROM eclipse-temurin:17-jre-alpine as runtime

VOLUME /tmp

RUN apk add --no-cache tzdata
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Create a non-root user
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Copy the jar file
COPY target/*.jar app.jar

# Change ownership of the jar file
RUN chown appuser:appgroup app.jar

# Switch to non-root user
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]