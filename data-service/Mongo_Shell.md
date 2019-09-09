# Mongo Shell Ubuntu Installation

Add the official MongoDB repository

    $ echo "deb [ arch=amd64 ] https://repo.mongodb.org/apt/ubuntu bionic/mongodb-org/4.0 multiverse" \
        | sudo tee /etc/apt/sources.list.d/mongodb-org-4.0.list

Import the public key:

    $ sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 \
        --recv 9DA31620334BD75D9DCB49F368818C72E52529D4
    
Install the latest version of mongo shell:

    $ sudo apt-get update
    $ sudo apt-get install -y mongodb-org-shell

Connect with MongoDB using mongo shell:

    $ mongo <FQDN>:<PORT>/<database> -u <user> -p <password>

Get list of all collections in MongoDb

    db.getCollectionNames()
    
Get records from the specified collection name.

    db.getCollection('collection-name').find({})
    
