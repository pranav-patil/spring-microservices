# Config Service
=============

### Install JCS

In order to [enable encryption and decryption](https://patrickgrimard.io/2016/03/04/encrypting-and-decrypting-configuration-property-values-in-spring-cloud/)
of config property files, you need to install the unlimited strength Java Cryptography Extension (JCE) in your config server environment.
This doesn’t come with the JVM by default.  The one that comes with the JVM is of limited strength.

* Go to Oracle’s website and download the [unlimited strength JCE for your JRE](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
* Extract the downloaded zip file somewhere and copy the local_policy.jar and US_export_policy.jar files into $JAVA_HOME/jre/lib/security directory.

### Encrypt password and verify cipher by decryption

To encrypt a password for adding to the YML file as a {cipher}, use the below encryption service.

http://localhost:8888/encrypt

Authorization Basic dLKlmkdjv25vbWsxVjW=

To verify the encrypted password, use the below decryption service.

http://localhost:8888/decrypt

Authorization Basic dLKlmkdjv25vbWsxVjW=
