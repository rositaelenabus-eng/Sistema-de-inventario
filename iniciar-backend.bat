@echo off
cd /d "%~dp0backend"
echo ========================================================
echo       INICIANDO BACKEND SPRING BOOT (PUERTO 8080)
echo ========================================================
if exist "target\backend-productos-1.0.0.jar" (
    java -jar target\backend-productos-1.0.0.jar
) else (
    "C:\Users\LENOVO\.m2\wrapper\dists\apache-maven-3.9.16\56ba1f9f\bin\mvn.cmd" spring-boot:run -o
)
pause
