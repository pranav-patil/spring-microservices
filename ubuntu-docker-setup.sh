#!/bin/bash

# install docker and docker-compose for ubuntu bionic
sudo apt update
sudo apt install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
sudo apt update
sudo apt install docker-ce
sudo apt install docker-compose
sudo sysctl -w vm.max_map_count=262144

# install jdk 8
sudo apt-get install openjdk-8-jdk

# install gradle
sudo add-apt-repository ppa:cwchien/gradle
sudo apt-get update
sudo apt upgrade gradle
