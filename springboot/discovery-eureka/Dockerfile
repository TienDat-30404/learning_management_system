# Sử dụng image OpenJDK 11 làm base image
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file .jar vào thư mục làm việc
COPY target/discovery-eureka-0.0.1-SNAPSHOT-exec.jar discovery-eureka.jar

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/app/discovery-eureka.jar"]

# Mở port 8761 để Eureka Server có thể kết nối
EXPOSE 8761
