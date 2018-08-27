Config Service
=============
 
[Configuration Service](https://cloud.spring.io/spring-cloud-config/) handles the configurations for all of the services through a simple point-to-point service call to retrieve those configurations. The configurations for micro services is stored in an environment and not as part of the project. It is a central place to manage external properties for applications across all environments. Configuration service ideally has a dedicated Git repository for the configuration of the corresponding environment (dev/test/production).

### Create your config.jks Keystore

Use the Java's built in KeyTool utility to create a Java Key Store (JKS) with a key as below:

    $ cd $JAVA_HOME/bin
    $ keytool -genkeypair -alias configkey -keyalg RSA \
            -dname "CN=Web Server,OU=RND,O=Emprovise,L=Chicago,S=IL,C=US" \
            -keypass ${CONFIG_KEY_PASSWORD} -keystore config.jks -storepass ${CONFIG_KEYSTORE_PASSWORD}

### Install JCS

In order to [enable encryption and decryption](https://patrickgrimard.io/2016/03/04/encrypting-and-decrypting-configuration-property-values-in-spring-cloud/)
of config property files, you need to install the unlimited strength Java Cryptography Extension (JCE) in your config server environment.
This doesn’t come with the JVM by default.  The one that comes with the JVM is of limited strength.

* Go to Oracle’s website and download the [unlimited strength JCE for your JRE](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
* Extract the downloaded zip file somewhere and copy the local_policy.jar and US_export_policy.jar files into $JAVA_HOME/jre/lib/security directory.

### Encrypt password and verify cipher by decryption

To encrypt a password for adding to the YML file as a {cipher}, use the below encryption service.

http://localhost:8888/encrypt

Authorization Basic {BASE64-ENCODED user:CONFIG_SERVICE_PASSWORD}

To verify the encrypted password, use the below decryption service.

http://localhost:8888/decrypt

Authorization Basic {BASE64-ENCODED user:CONFIG_SERVICE_PASSWORD}

### Running the Config Service

Pass the CONFIG_KEY_PASSWORD and CONFIG_KEYSTORE_PASSWORD created from the above keytool command and new CONFIG_SERVICE_PASSWORD to secure
access to shared configuration files.

    $ java -jar config-service/build/libs/config-service-0.0.1-SNAPSHOT.jar
           -DCONFIG_SERVICE_PASSWORD=xxxx -DCONFIG_KEYSTORE_PASSWORD=xxxx -DCONFIG_KEY_PASSWORD=xxxx 

