mvn spring-boot:run -Dspring-boot.run.profiles=dev

java -jar -Dspring.profiles.active=dev XXX.jar

## Database

### PostgreSQL 


```
CREATE USER eightbits WITH PASSWORD '123456';
CREATE DATABASE eightbits;
GRANT ALL PRIVILEGES ON DATABASE eightbits TO eightbits; 
```

```
psql -h <hostname or ip address> -p <port number of remote machine> -d <database name which you want to connect> -U <username of the database server>
```

List all schemas
```
=> \dn
```

List all tables
```
=> \dt
=> \dt+
=> \dt schema_name.*
```

```
select schema_name
from information_schema.schemata;

SELECT nspname FROM pg_namespace;
SELECT tablename FROM pg_tables WHERE schemaname = 'public';

```

## CI/CD

### Events

#### Push to feature branch
```json
{
  "ref": "refs/heads/branch-name",
  "event_name": "push"  
}
```

#### Create pull request


#### Synchronize pull request
#### Merge to master branch 
#### Create tag and push the tag 
#### Create release

### Create Docker Image

#### Caching Maven Dependencies with Docker
[Caching Maven Dependencies with Docker](https://www.baeldung.com/ops/docker-cache-maven-dependencies)

### Deploy to Kubernetes
#### Create tag