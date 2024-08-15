#!/usr/bin/env sh

mvn --batch-mode --no-transfer-progress --update-snapshots package
#tar zcvf artifact.tgz $(find . -name target -type d)
#mkdir staging && cp web/target/*.jar staging
#cp deployment/Dockerfile staging
