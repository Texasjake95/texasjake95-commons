#!/usr/bin/env bash
git config credential.helper "store --file=.git/credentials"
echo "https://${GH_TOKEN}:@github.com" > .git/credentials
rm -rf maven-test
git clone https://Texasjake95@github.com/Texasjake95/maven-repo.git ./maven-test -v
./gradlew build uploadArchives
cd ./maven-test
git add .
git commit -m "Travis-CI Build Push"
git push 
rm -rf maven-test