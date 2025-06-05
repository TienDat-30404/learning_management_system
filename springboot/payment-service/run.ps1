


# Đọc nội dung file .env và thiết lập các biến môi trường
$envVars = Get-Content -Path ".env"

foreach ($line in $envVars) {
    # Kiểm tra nếu dòng có dạng KEY=VALUE
    if ($line -match "^(.*?)=(.*?)$") {
        $envName = $matches[1].Trim()   # Tên biến môi trường (KEY)
        $envValue = $matches[2].Trim()  # Giá trị biến môi trường (VALUE)
        
        # Thiết lập biến môi trường cho quá trình hiện tại
        [System.Environment]::SetEnvironmentVariable($envName, $envValue, [System.EnvironmentVariableTarget]::Process)
    }
}

# Kiểm tra các giá trị môi trường đã được thiết lập (tùy chọn)
Write-Host "DB_URL = $env:DB_URL"
Write-Host "DB_USERNAME = $env:DB_USERNAME"
Write-Host "DB_PASSWORD = $env:DB_PASSWORD"

# Chạy ứng dụng Spring Boot
mvn spring-boot:run
