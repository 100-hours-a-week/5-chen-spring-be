#!/bin/bash

ROOT_PATH="/home/ec2-user/spring-app"
UPLOADED_JAR="$ROOT_PATH/build/libs/app.jar"
SERVER_JAR="$ROOT_PATH/executable.jar"

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

NOW=$(date +%c)

DB_URL=$(aws ssm get-parameter --name /week-eleven/DB_URL --query "Parameter.Value")
DB_USER=$(aws ssm get-parameter --name /week-eleven/DB_USER --query "Parameter.Value")
DB_PASSWORD=$(aws ssm get-parameter --name /week-eleven/DB_PASSWORD --query "Parameter.Value")

echo "[$NOW] DB 정보 : $DB_URL $DB_PASSWORD $DB_USER" >> $START_LOG

echo "[$NOW] $SERVER_JAR 복사" >> $START_LOG
cp $UPLOADED_JAR $SERVER_JAR

echo "[$NOW] > $SERVER_JAR 실행" >> $START_LOG
nohup java -jar -Dspring.profiles.active=dev \
  -DDB_URL=$DB_URL -DDB_PASSWORD=$DB_PASSWORD -DDB_USER=$DB_USER \
  $SERVER_JAR > $APP_LOG 2> $ERROR_LOG &

SERVICE_PID=$(pgrep -f $SERVER_JAR)
echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> $START_LOG