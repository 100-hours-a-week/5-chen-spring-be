#!/bin/bash

ROOT_PATH="/home/ec2-user/spring-app"
SERVER_JAR="$ROOT_PATH/executable.jar"

STOP_LOG="$ROOT_PATH/stop.log"

SERVICE_PID=$(pgrep -f $SERVER_JAR) # 실행중인 Spring 서버의 PID

if [ -z "$SERVICE_PID" ]; then
  echo "서비스 NotFound" >> $STOP_LOG
else
  echo "서비스 종료 " >> $STOP_LOG
  kill "$SERVICE_PID"
  # kill -9 $SERVICE_PID # 강제 종료를 하고 싶다면 이 명령어 사용
fi