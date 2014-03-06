#!/usr/bin/env bash

# delete the directory form last time in case of failure
rm -rf maven-repo

# pull the maven repo from git
git clone https://Texasjake95@github.com/Texasjake95/maven-repo.git ./maven-repo -v

# run gradle
./gradlew build uploadArchives

# change to the maven repo directory
cd ./maven-repo

# add correct origin to the repo 
git remote rm origin
git remote add origin https://Texasjake95:${GH_TOKEN}@github.com/Texasjake95/maven-repo.git

# configure repo
git config user.email "texasjake95@gmail.com"
git config user.name "Texasjake95"

# configure git push
git config push.default current

# preform commit
git add .
git commit -q -m "Travis-CI Build Push"

# push commit
git push -q origin HEAD:master

# delete repo since we are done with it
rm -rf maven-repo