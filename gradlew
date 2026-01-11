#!/usr/bin/env bash

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname "$PRG"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS=""

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME\n\nPlease set the JAVA_HOME variable in your environment to match the location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.\n\nPlease set the JAVA_HOME variable in your environment to match the location of your Java installation."
fi

# Increase the maximum file descriptors if we can.
if [ "$cygwin" = "false" -a "$darwin" = "false" -a "$nonstop" = "false" ] ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ "$MAX_FD_LIMIT" -ge 1024 ] ; then
        if [ "$MAX_FD" = "maximum" ] ; then
            MAX_FD="$MAX_FD_LIMIT"
        fi
        ulimit -n $MAX_FD || warn "Could not set maximum file descriptor limit: $MAX_FD"
    else
        warn "Could not set maximum file descriptor limit: $MAX_FD_LIMIT (current limit)"
    fi
fi

# For Darwin, add options to allow Java to run without a Dock icon.
if [ "$darwin" = "true" ] ; then
    DEFAULT_JVM_OPTS="$DEFAULT_JVM_OPTS -Dapple.awt.UIElement=true"
fi

# Collect all arguments for the Java command
# This avoids issues with empty arguments or arguments containing spaces.
# See the in-line comments for details.
APP_ARGS=()

# Add default JVM options
for arg in $DEFAULT_JVM_OPTS; do
    APP_ARGS+=("$arg")
done

# Add JAVA_OPTS and GRADLE_OPTS
# These rely on word-splitting, so we perform it explicitly.
# This is a common pattern in shell scripts, but it's important to be aware of.
# If you need to pass options with spaces, you should quote them.
for arg in $JAVA_OPTS; do
    APP_ARGS+=("$arg")
done
for arg in $GRADLE_OPTS; do
    APP_ARGS+=("$arg")
done

# Add the application name property
APP_ARGS+=("-Dorg.gradle.appname=$APP_BASE_NAME")

# Add the classpath
APP_ARGS+=("-classpath")
APP_ARGS+=("$CLASSPATH")

# Add the main class
APP_ARGS+=("org.gradle.wrapper.GradleWrapperMain")

# Add all remaining arguments
for arg in "$@"; do
    APP_ARGS+=("$arg")
done

# Execute the Java command
exec "$JAVACMD" "${APP_ARGS[@]}"