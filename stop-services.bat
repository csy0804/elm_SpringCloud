@echo off
chcp 65001
echo ========================================
echo 开始停止Spring Cloud微服务集群
echo ========================================

echo.
echo 正在停止所有Java进程...
taskkill /f /im java.exe 2>nul
taskkill /f /im javaw.exe 2>nul

echo.
echo 正在停止所有Maven进程...
taskkill /f /im mvn.cmd 2>nul
taskkill /f /im mvn.exe 2>nul

echo.
echo 正在停止所有CMD窗口...
taskkill /f /fi "WINDOWTITLE eq Eureka Server 13000*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Eureka Server 13001*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Config Server 15000*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Config Server 15001*" 2>nul
taskkill /f /fi "WINDOWTITLE eq User Server 10100*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Food Server 10200*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Food Server 10201*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Business Server 10300*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Business Server 10301*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Cart Server 10400*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Cart Server 10401*" 2>nul
taskkill /f /fi "WINDOWTITLE eq DeliveryAddress Server 10500*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Orders Server 10600*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Orders Server 10601*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Credit Server 10700*" 2>nul
taskkill /f /fi "WINDOWTITLE eq GPT Server 10900*" 2>nul
taskkill /f /fi "WINDOWTITLE eq Gateway Server 14000*" 2>nul

echo.
echo ========================================
echo 所有微服务已停止！
echo ========================================
echo.
echo 按任意键退出...
pause >nul 