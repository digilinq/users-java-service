mvn spring-boot:run -Dspring-boot.run.profiles=dev

java -jar -Dspring.profiles.active=dev XXX.jar

## Database

### PostgreSQL 

```
CREATE USER digilinq WITH PASSWORD '123456';
CREATE DATABASE digilinq;
GRANT ALL PRIVILEGES ON DATABASE digilinq TO digilinq; 
```