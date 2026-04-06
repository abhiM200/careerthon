@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup batch script (FIXED)
@REM ----------------------------------------------------------------------------
@echo off
SET "MAVEN_PROJECTBASEDIR=%~dp0"
IF NOT "%MAVEN_PROJECTBASEDIR:~-1%"=="\" SET "MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR%\"

@SET MAVEN_WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"
@SET MAVEN_WRAPPER_PROPERTIES="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties"

title %0
IF "%MAVEN_BATCH_ECHO%" == "on"  echo %MAVEN_BATCH_ECHO%

IF "%HOME%" == "" (SET "HOME=%HOMEDRIVE%%HOMEPATH%")

SET JAVA_HOME_TO_USE=%JAVA_HOME%
IF "%JAVA_HOME_TO_USE%" == "" (
  FOR /f "tokens=*" %%i IN ('where java 2^>NUL') DO (
    SET JAVA_BINARY=%%i
    GOTO :found_java
  )
  ECHO "ERROR: JAVA_HOME is not set, and java is not on the PATH."
  EXIT /B 1
  :found_java
  FOR %%i IN ("%JAVA_BINARY%") DO SET JAVA_HOME_TO_USE=%%~dpi..
)

SET JAVA_EXECUTABLE="%JAVA_HOME_TO_USE%\bin\java.exe"

@REM Detect Maven distribution URL from wrapper properties
SET DISTRIBUTION_URL=
FOR /F "usebackq tokens=1,* delims==" %%a IN (%MAVEN_WRAPPER_PROPERTIES%) DO (
  IF "%%a"=="distributionUrl" SET DISTRIBUTION_URL=%%b
)

%JAVA_EXECUTABLE% "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" -classpath %MAVEN_WRAPPER_JAR% org.apache.maven.wrapper.MavenWrapperMain %*
