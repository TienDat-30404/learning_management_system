# Sử dụng image Java base
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file JAR vào container
COPY target/user-service-0.0.1-SNAPSHOT-exec.jar user-service.jar

# Mở cổng 8081 để User Service có thể nghe
EXPOSE 8081

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "user-service.jar"]
