@echo off
set SCRIPTDIR=%cd%
set CMDLINE=%1
for /f "usebackq" %%i in (`PowerShell $date ^= Get-Date^; $date ^= $date.AddDays^(-1^)^; $date.ToString^('ddMMyyyy'^)`) do set YESTERDAY=%%i 

set CLASSPATH=%SCRIPTDIR%\lib\jdbcdump.jar;%SCRIPTDIR%\lib\commons-cli-1.3.1.jar;%SCRIPTDIR%\lib\commons-logging-1.1.1.jar;%SCRIPTDIR%\lib\db2jcc4.jar

java -cp %CLASSPATH% org.crossroad.jdbc.dump.JDBCDump -d %SCRIPTDIR% %CMDLINE%
