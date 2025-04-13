# Sử dụng image Java base
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file JAR vào container
COPY target/config-server-0.0.1-SNAPSHOT-exec.jar config-server.jar

# Mở cổng 8888 để Config Server có thể nghe
EXPOSE 8888

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "config-server.jar"]
