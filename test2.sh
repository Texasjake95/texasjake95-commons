#!/usr/bin/env bash

rm -rf maven-test
git clone https://Texasjake95@github.com/Texasjake95/maven-repo.git ./maven-test -v
./gradlew build uploadArchives
cd ./maven-test
git config credential.helper "store --file=.git/credentials"
echo "https://${GH_TOKEN}:@github.com" > .git/credentials
git config --global user.name "Texasjake95"
git add .
git commit -m "Travis-CI Build Push"
git push 
rm -rf maven-test