#!/usr/bin/env sh

mvn --no-transfer-progress package
tar zcvf artifact.tgz $(find . -name target -type d)
