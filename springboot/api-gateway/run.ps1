


$envVars = Get-Content -Path ".env"

foreach ($line in $envVars) {
    if ($line -match "^(.*?)=(.*?)$") {
        $envName = $matches[1].Trim()   # Tên biến môi trường (KEY)
        $envValue = $matches[2].Trim() 
        
        [System.Environment]::SetEnvironmentVariable($envName, $envValue, [System.EnvironmentVariableTarget]::Process)
    }
}

# Chạy ứng dụng Spring Boot
mvn spring-boot:run
