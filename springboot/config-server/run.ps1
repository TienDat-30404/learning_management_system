# Đọc các biến từ file .env và export chúng vào môi trường
Get-Content .env | ForEach-Object {
    $key, $value = $_ -split "="
    [System.Environment]::SetEnvironmentVariable($key, $value, [System.EnvironmentVariableTarget]::Process)
}

# Chạy ứng dụng Spring Boot
mvn spring-boot:run