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

