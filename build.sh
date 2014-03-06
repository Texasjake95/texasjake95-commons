#!/usr/bin/env bash

rm -rf maven-test
git clone https://Texasjake95@github.com/Texasjake95/maven-repo.git ./maven-test -v
./gradlew build uploadArchives
cd ./maven-test
git remote rm origin
git remote add origin https://Texasjake95:${GH_TOKEN}@github.com/Texasjake95/maven-repo.git
git config user.email "texasjake95@gmail.com"
git config user.name "Texasjake95"
git add .
git commit -q --quiet -m "Travis-CI Build Push"
git push 
rm -rf maven-test