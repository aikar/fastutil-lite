@ECHO OFF
set MAVEN_OPTS="-Xmx1G"
IF %1.==. (
    mvn -T 8 install
) ELSE (
    mvn -T 8 %*
)