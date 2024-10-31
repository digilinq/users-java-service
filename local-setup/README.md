### Signup
```shell
curl --location 'http://localhost:8080/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "hussein.muhammadii@gmail.com",
    "password": "Hello12345",
    "confirmPassword": "Hello12345"
}'
```

### Signup using json file
```shell
curl -D - -X POST http://localhost:8080/signup \
-H "Content-Type: application/json" \
-d @code-snippet/signup.json
```

Find all users
```shell
curl -D - http://localhost:8080/v1/users    
```

### Delete a user
```shell
curl -D - -X DELETE http://localhost:8080/v1/users/792da837-9c54-4a05-9db7-16b23299e9f1  
```
