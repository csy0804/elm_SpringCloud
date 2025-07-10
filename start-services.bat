@echo off
chcp 65001
echo ========================================
echo 开始启动Spring Cloud微服务集群
echo ========================================

REM 设置Java环境变量（如果需要）
REM set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_xxx
REM set PATH=%JAVA_HOME%\bin;%PATH%

echo.
echo 1. 启动Eureka注册中心集群...
echo 启动Eureka Server 13000...
start "Eureka Server 13000" cmd /k "cd /d %~dp0eureka_server_13000 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 15 /nobreak >nul

echo 启动Eureka Server 13001...
start "Eureka Server 13001" cmd /k "cd /d %~dp0eureka_server_13001 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 15 /nobreak >nul

echo 等待Eureka注册中心启动完成...
call :check_service http://localhost:13000 "Eureka Server 13000"
call :check_service http://localhost:13001 "Eureka Server 13001"

echo.
echo 2. 启动Config配置中心集群...
echo 启动Config Server 15000...
start "Config Server 15000" cmd /k "cd /d %~dp0config_server_15000 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 15 /nobreak >nul

echo 启动Config Server 15001...
start "Config Server 15001" cmd /k "cd /d %~dp0config_server_15001 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 15 /nobreak >nul

echo 等待Config配置中心启动完成...
call :check_service http://localhost:15000 "Config Server 15000"
call :check_service http://localhost:15001 "Config Server 15001"

echo.
echo 3. 启动业务微服务...
echo 启动User Server 10100...
start "User Server 10100" cmd /k "cd /d %~dp0user_server_10100 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 15 /nobreak >nul

echo 启动Food Server 10200...
start "Food Server 10200" cmd /k "cd /d %~dp0food_server_10200 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动Food Server 10201...
start "Food Server 10201" cmd /k "cd /d %~dp0food_server_10201 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动Business Server 10300...
start "Business Server 10300" cmd /k "cd /d %~dp0business_server_10300 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动Business Server 10301...
start "Business Server 10301" cmd /k "cd /d %~dp0business_server_10301 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动Cart Server 10400...
start "Cart Server 10400" cmd /k "cd /d %~dp0cart_server_10400 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动Cart Server 10401...
start "Cart Server 10401" cmd /k "cd /d %~dp0cart_server_10401 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动DeliveryAddress Server 10500...
start "DeliveryAddress Server 10500" cmd /k "cd /d %~dp0deliveryaddress_server_10500 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动Orders Server 10600...
start "Orders Server 10600" cmd /k "cd /d %~dp0orders_server_10600 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动Orders Server 10601...
start "Orders Server 10601" cmd /k "cd /d %~dp0orders_server_10601 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动Credit Server 10700...
start "Credit Server 10700" cmd /k "cd /d %~dp0credit_server_10700 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo 启动GPT Server 10900...
start "GPT Server 10900" cmd /k "cd /d %~dp0gpt_server_10900 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 10 /nobreak >nul

echo.
echo 4. 启动Gateway网关...
echo 启动Gateway Server 14000...
start "Gateway Server 14000" cmd /k "cd /d %~dp0gateway_server_14000 && mvn compile exec:java -Dexec.mainClass=com.neusoft.MyApplication"
timeout /t 15 /nobreak >nul

echo 等待Gateway网关启动完成...
call :check_service http://localhost:14000 "Gateway Server 14000"

echo.
echo ========================================
echo 所有微服务启动完成！
echo ========================================
echo.
echo 服务访问地址：
echo Eureka注册中心: http://localhost:13000, http://localhost:13001
echo Config配置中心: http://localhost:15000, http://localhost:15001
echo Gateway网关: http://localhost:14000
echo.
echo 按任意键退出...
pause >nul
goto :eof

REM 定义健康检查函数
:check_service
setlocal enabledelayedexpansion
set service_url=%~1
set service_name=%~2
set max_attempts=30
set attempt=0

:check_loop
set /a attempt+=1
echo 检查 !service_name! 状态... (尝试 !attempt!/!max_attempts!)
curl -s "!service_url!" >nul 2>&1
if !errorlevel! equ 0 (
    echo !service_name! 启动成功！
    endlocal
    goto :eof
)
if !attempt! geq !max_attempts! (
    echo 错误：!service_name! 启动超时！
    endlocal
    pause
    exit /b 1
)
timeout /t 2 /nobreak >nul
goto :check_loop