docker run -d \
  -e DB_USER=$DB_USER -e DB_URL=$DB_URL -e DB_PASSWORD=$DB_PASSWORD \
	-e STORAGE_PATH=$STORAGE_PATH -e SERVER_NAME=$SERVER_NAME \
	-v /home/ec2-user/uploads:$STORAGE_PATH \
  --rm --name communty-api-container -p 8080:8080 community-api