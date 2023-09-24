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
  "head_ref": "",
  "base_ref": "",
  "event_name": "push"  
}
```

#### Create pull request
Pull request #25 opened
```json
{
  "ref": "refs/pull/25/merge",
  "head_ref": "branch-name",  
  "base_ref": "main",
  "event_name": "pull_request",
  "event": {
    "action": "opened",
    "pull_request": {
      "base": {
        "ref": "main"
      },
      "head": {
        "ref": "branch-name"
      }
    }
  }
}
```

#### Synchronize pull request
```json
{
  "ref": "refs/pull/26/merge",
  "head_ref": "22-cicd-pipelines-for-deploy-to-kubernetes",
  "base_ref": "main",
  "event_name": "pull_request",
  "event": {
    "action": "synchronize",
    "base": {
      "ref": "main"
    },
    "head": {      
      "ref": "branch-name"
    }
  }    
}
```

#### Merge to master branch
```json
{
  "ref": "refs/heads/main",
  "head_ref": "",
  "base_ref": "",
  "event_name": "push"  
}
```

#### Delete feature branch after merge to master branch
```json
{
  "ref": "refs/heads/main",
  "head_ref": "",
  "base_ref": "",
  "event_name": "delete",
  "event": {  
    "ref": "23-deploy-on-create-tag-and-release",
    "ref_type": "branch"
  }
}
```

#### Create tag and push the tag 
```json
{
  "ref": "refs/tags/v1.0.2",
  "head_ref": "",
  "base_ref": "",
  "event_name": "push",
  "event": {
    "base_ref": "refs/heads/main",
    "ref": "refs/tags/v1.0.2"
  },
  "ref_name": "v1.0.2",
  "ref_protected": false,
  "ref_type": "tag"
}
```
#### Create release

### Create Docker Image

#### Caching Maven Dependencies with Docker
[Caching Maven Dependencies with Docker](https://www.baeldung.com/ops/docker-cache-maven-dependencies)

### Deploy to Kubernetes
#### Create tag