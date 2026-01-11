#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,

# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

#
# @author ...
#

#
# A simple script that created a gradle wrapper for projects using gradle.
#
# This script is not a gradle wrapper, it is a script that will create a
# gradle wrapper for you.
#

# Set the gradle version
GRADLE_VERSION=8.5

# Set the gradle distribution type
GRADLE_DISTRIBUTION_TYPE=bin

# Set the gradle distribution url
GRADLE_DISTRIBUTION_URL="https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-${GRADLE_DISTRIBUTION_TYPE}.zip"

# Set the gradle wrapper properties file
GRADLE_WRAPPER_PROPERTIES_FILE="gradle/wrapper/gradle-wrapper.properties"

# Set the gradle wrapper jar file
GRADLE_WRAPPER_JAR_FILE="gradle/wrapper/gradle-wrapper.jar"

# Set the gradle wrapper script file
GRADLE_WRAPPER_SCRIPT_FILE="gradlew"

# Set the gradle wrapper batch file
GRADLE_WRAPPER_BATCH_FILE="gradlew.bat"

# Create the gradle wrapper directory if it does not exist
if [ ! -d "gradle/wrapper" ]; then
    mkdir -p "gradle/wrapper"
fi

# Create the gradle wrapper properties file
echo "distributionBase=GRADLE_USER_HOME" > "${GRADLE_WRAPPER_PROPERTIES_FILE}"
echo "distributionPath=wrapper/dists" >> "${GRADLE_WRAPPER_PROPERTIES_FILE}"
echo "distributionUrl=${GRADLE_DISTRIBUTION_URL}" >> "${GRADLE_WRAPPER_PROPERTIES_FILE}"
echo "zipStoreBase=GRADLE_USER_HOME" >> "${GRAD_WRAPPER_PROPERTIES_FILE}"
echo "zipStorePath=wrapper/dists" >> "${GRADLE_WRAPPER_PROPERTIES_FILE}"

# Download the gradle wrapper jar file
if [ ! -f "${GRADLE_WRAPPER_JAR_FILE}" ]; then
    echo "Downloading ${GRADLE_DISTRIBUTION_URL}"
    if [ -x "$(command -v curl)" ]; then
        curl -L "${GRADLE_DISTRIBUTION_URL}" -o "${GRADLE_WRAPPER_JAR_FILE}"
    elif [ -x "$(command -v wget)" ]; then
        wget "${GRADLE_DISTRIBUTION_URL}" -O "${GRADLE_WRAPPER_JAR_FILE}"
    else
        echo "Could not find curl or wget, please install one of them."
        exit 1
    fi
fi

# Create the gradle wrapper script file
echo '#!/usr/bin/env sh' > "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '##############################################################################' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Copyright 2015 the original author or authors.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '#' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Licensed under the Apache License, Version 2.0 (the "License");' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# you may not use this file except in compliance with the License.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# You may obtain a copy of the License at' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '#' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '#      https://www.apache.org/licenses/LICENSE-2.0' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '#' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Unless required by applicable law or agreed to in writing, software' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# distributed under the License is distributed on an "AS IS" BASIS,' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# See the License for the specific language governing permissions and' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# limitations under the License.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '##############################################################################' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'DEFAULT_JVM_OPTS=""' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'APP_NAME="Gradle"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'APP_BASE_NAME=$(basename "$0")' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Add extra jars that cannot be placed in lib folder here' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'CLASSPATH=""' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Use the maximum available, or set MAX_FD != -1 to use that value.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'MAX_FD="maximum"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'warn () {' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    echo "$*" >&2' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '}' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'die () {' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    echo' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    echo "$*" >&2' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    echo' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    exit 1' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '}' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# OS specific support (must be 'true' or 'false').' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'cygwin=false' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'msys=false' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'darwin=false' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'case "$(uname)" in' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    CYGWIN*)' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        cygwin=true' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        ;;' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    Darwin*)' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        darwin=true' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        ;;' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    MINGW*)' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        msys=true' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        ;;' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'esac' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Attempt to set APP_HOME' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Resolve links: $0 may be a link' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'PRG="$0"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Need this for relative symlinks.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'while [ -h "$PRG" ] ; do' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    ls=$(ls -ld "$PRG")' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    link=$(expr "$ls" : ".*-> \(.*\)$")' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    if expr "$link" : "/.*" > /dev/null; then' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        PRG="$link"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    else' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        PRG=$(dirname "$PRG")/"$link"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    fi' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'done' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'APP_HOME=$(dirname "$PRG")' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Determine the Java command to use to start the JVM.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'if [ -n "$JAVA_HOME" ] ; then' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        # IBM\'s JDK on AIX uses strange locations for the executables' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        JRE_HOME="$JAVA_HOME/jre"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        JAVA_EXE="$JRE_HOME/sh/java"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    else' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        JRE_HOME="$JAVA_HOME"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        JAVA_EXE="$JRE_HOME/bin/java"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    fi' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    if [ ! -x "$JAVA_EXE" ] ; then' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the location of your Java installation."' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    fi' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'else' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    JAVA_EXE="java"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no ''java'' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the location of your Java installation."' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'fi' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Split up the JVM options only if spaces are available.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'if [ -z "${JAVA_OPTS}" ] ; then' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    JAVA_OPTS=($DEFAULT_JVM_OPTS)' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'fi' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'if [ -z "${GRADLE_OPTS}" ] ; then' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    GRADLE_OPTS=()' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'fi' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Escape application args' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'save () {' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    for i do' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '        printf "%s\n" "$i" | sed "s/ /\\ /g"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '    done' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '}' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'APP_ARGS=$(save "$@")' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '# Collect all arguments for the java command, following the shell quoting and substitution rules.' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'eval set -- "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "$APP_ARGS"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo 'exec "$JAVA_EXE" "${JAVA_OPTS[@]}" "${GRADLE_OPTS[@]}" -Dorg.gradle.appname="$APP_BASE_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"
echo '' >> "${GRADLE_WRAPPER_SCRIPT_FILE}"

# Make the gradle wrapper script executable
chmod +x "${GRADLE_WRAPPER_SCRIPT_FILE}"

# Create the gradle wrapper batch file
echo '@if "%DEBUG%" == "" @echo off' > "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem ##############################################################################' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem  Copyright 2015 the original author or authors.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem  Licensed under the Apache License, Version 2.0 (the "License");' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem  you may not use this file except in compliance with the License.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem  You may obtain a copy of the License at' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem       https://www.apache.org/licenses/LICENSE-2.0' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem  Unless required by applicable law or agreed to in writing, software' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem  distributed under the License is distributed on an "AS IS" BASIS,' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem  See the License for the specific language governing permissions and' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem  limitations under the License.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem ##############################################################################' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem Set local scope for the variables with windows NT shell' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'if "%OS%"=="Windows_NT" setlocal' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'set DIRNAME=%~dp0' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'if "%DIRNAME%" == "" set DIRNAME=.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'set APP_BASE_NAME=%~n0' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'set APP_HOME=%DIRNAME%' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'set DEFAULT_JVM_OPTS=' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem Find java.exe' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'if defined JAVA_HOME goto findJavaFromJavaHome' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'set JAVA_EXE=java.exe' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '%JAVA_EXE% -version >NUL 2>&1' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'if %ERRORLEVEL% == 0 goto execute' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'echo ERROR: JAVA_HOME is not set and no ''java'' command could be found in your PATH.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'echo Please set the JAVA_HOME variable in your environment to match the location of your Java installation.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'goto fail' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo ':findJavaFromJavaHome' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'set JAVA_HOME=%JAVA_HOME:"=%' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'set JAVA_EXE=%JAVA_HOME%/bin/java.exe' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'if exist "%JAVA_EXE%" goto execute' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'echo Please set the JAVA_HOME variable in your environment to match the location of your Java installation.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'goto fail' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo ':execute' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem Setup the command line args.' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem Execute Gradle' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo ':end' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '@rem End local scope for the variables with windows NT shell' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'if "%OS%"=="Windows_NT" endlocal' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo ':fail' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'if "%OS%"=="Windows_NT" endlocal' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'exit /b 1' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo '' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo ':eof' >> "${GRADLE_WRAPPER_BATCH_FILE}"
echo 'exit /b 0' >> "${GRADLE_WRAPPER_BATCH_FILE}"
