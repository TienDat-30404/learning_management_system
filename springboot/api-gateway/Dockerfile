# Sử dụng image Java base
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file JAR vào container
COPY target/api-gateway-0.0.1-SNAPSHOT-exec.jar api-gateway.jar

# Mở cổng 8080 để API Gateway có thể nghe
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
