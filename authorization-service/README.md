Authorization Service
=============
## OAuth 2.0

OAuth 2.0 is the industry-standard protocol for authorization. The OAuth 2.0 specification defines a delegation protocol that is useful for conveying authorization decisions (via a token) across a network of web-enabled applications and APIs.
OAuth 2.0 is not an authentication protocol and is not primarily used to identify a user. Although it is essential to authenticate both the user and the client in any authorization flow defined by the protocol. 
There are number of grant types/methods for a client application to acquire an access token which can be used to authenticate a request to an API endpoint.
The client authentication enforces the use of the API only by known clients. On contrary, the serialized access token once generated, is not bound to a specific client directly.

## OAuth2 Roles

* **Resource owner (the User)**: An entity capable of granting access to a protected resource. Also referred as an end-user.
* **Resource server (the API server)**: The server hosting the protected resources, capable of accepting and responding to protected resource requests using access tokens.
* **Client**: An application making protected resource requests on behalf of the resource owner and with its authorization.
* **Authorization server**: The server issuing access tokens to the client after successfully authenticating the resource owner and obtaining authorization.

## Grant Types

When a client application wants access to the resources of a resource owner hosted on a resource server, the client application must first obtain an authorization grant. OAuth2 provides [several authorization grants](https://alexbilbie.com/guide-to-oauth-2-grants/). Each grant type serves a different purpose and is used in a different way. Below are OAuth2 grant types:

* **Authorization Code**:
    The client redirects the user to the authorization server with parameters, response_type as code, client_id, redirect_uri and scope. The authorization server validates the request and asks user to login. The user login into the authorization server and approves the client. Upon user's approval the client is redirected to the redirect URI with parameters, state and authorization code. The client then sends a POST request to the authorization server with parameters, grant_type as authorization_code, client_id, client_secret, redirect_uri and code with the authorization code from the query string. The authorization server responds with a JSON object containing token_type as Bearer, expires_in, access_token and refresh_token. 
    
* **Implicit**:
    The implicit grant is intended to be used for single page web apps which can’t keep client secret because all of the application code and storage is easily accessible. Here the authorization server returns an access token instead of an authorization code. The client redirects the user to the authorization server with parameters, response_type as token, client_id, redirect_uri and scope. The authorization server validates the request and asks user to login. The user login into the authorization server and approves the client. Upon user's approval the client is redirected to the redirect URI with parameters, containing token_type as Bearer, expires_in and access_token. It does not return a refresh token.
     
* **Resource Owner Password Credentials**:
    It is mainly used by trusted first party clients. The client asks the user for their authorization credentials (username and password). The client then sends a POST request to authorization server with parameters, grant_type as password, client_id, client_secret, scope, username and password. The authorization server responds with a JSON object containing token_type as Bearer, expires_in, access_token and refresh_token.
     
* **Client Credentials**:
    It is used for machine-to-machine authentication where no specific user's permission is required to access data. The client sends a POST request to the authorization server with parameters, grant_type as client_credentials, client_id, client_secret and scope. The authorization server responds with a JSON object containing token_type as Bearer, expires_in and access_token.
    
* **Refresh token**:
    It is used to get a new access token after expiration of current access token. The client sends a POST request to the authorization server with parameters, grant_type as refresh_token, refresh_token, client_id, client_secret and scope. The authorization server will respond with a JSON object containing token_type as Bearer, expires_in, access_token and refresh_token. 

### Installation and Running of PostgreSQL Server

* Download latest [Windows PostgreSQL Installer](https://www.postgresql.org/download/windows/) and follow windows installation steps.
* Alternatively, can download [Windows PostgreSQL Binary Archive](https://www.enterprisedb.com/download-postgresql-binaries) and extract the zip file. POSTGRE_SQL_HOME is the path to the unzipped PostgreSQL **pgsql** directory.
* Run the below [pg_ctl](https://www.postgresql.org/docs/9.5/static/app-pg-ctl.html) utility commands to register PostGreSQL as service in POSTGRE_SQL_HOME/pgsql/bin directory.
* Create a new user named **appuser** and new database named **testdb** using below **psql** commands. The user and password are added to authorization-service.yml configuration file as spring.datasource.username (and password).
* PostGreSQL runs on default port 5432.


    createuser --password postgres
    pg_ctl.exe register -N postgres -U appuser -P secret -D "POSTGRE_SQL_HOME/pgsql/data"
    pg_ctl.exe register -N postgres -D "POSTGRE_SQL_HOME/pgsql/data"
    pg_ctl.exe -D "POSTGRE_SQL_HOME/pgsql/data" -l logfile start
    psql -U postgres
    psql -U appuser postgres
    create user root with password 'verysecret';
    alter user root createdb;
    create database testdb;
    \c testdb;

### Running the Authorization Service

Pass the new AUTH_SERVICE_PASSWORD, CONFIG_SERVICE_PASSWORD from the [config-service](../config-service/README.md) to access authorization-service.yml configuration file, corresponding passwords FINANCE_SERVICE_PASSWORD for finance-service and ANALYTICS_SERVICE_PASSWORD for analytics-service respectively.

    $ java -jar authorization-service/build/libs/authorization-service-0.0.1-SNAPSHOT.jar
           -DAUTH_SERVICE_PASSWORD=secret -DFINANCE_SERVICE_PASSWORD=xxxx 
           -DANALYTICS_SERVICE_PASSWORD=xxxx -DCONFIG_SERVICE_PASSWORD=xxxx
 
### Registering a New User with credentials

* Request for access token using client id **register-service** with the grant type as **client_credentials**.

    HTTP Method: POST  
    http://localhost:9000/api/auth/oauth/token?grant_type=client_credentials&client_id=register-service  
    Authorization: Basic {BASE64-ENCODED register-service:AUTH_SERVICE_PASSWORD}  
    Accept: application/json  
    Response:  
    
        {
            "access_token": "598f477c-5710-4542-b8f0-f7cf8ac36f63",
            "token_type": "bearer",
            "expires_in": 43199,
            "scope": "read"
        }

* Call **/auth/user/register** service to register new user passing user name, password, email and address details.
    Use the access_token received from the previous call for bearer authentication. 
    
    HTTP Method: POST  
    http://localhost:9000/api/auth/user/register  
    Authorization: Bearer {ACCESS_TOKEN} which is 598f477c-5710-4542-b8f0-f7cf8ac36f63  
    Accept: application/json  
    Request:  
    
        {
          "username" : "william",
          "password" : "secret",
          "firstName" : "William",
          "lastName" : "Brian",
          "email" : "williamb@gmail.com"
        }
    
    Response:  
    
        {
            "username": "william",
            "password": "$2a$10$GYqLeHWE/EKo.ATEerwRqOwUKL7QkKJH98yqkCajg8assSzRNNaCG",
            "firstName": "William",
            "lastName": "Brian",
            "email": "williamb@gmail.com"
        }

### Request for Access Token for a User

* We use the user credentials created from earlier user registration to request for an access token.

    HTTP Method: POST  
    http://localhost:9000/api/auth/oauth/token?grant_type=password&username=william&password=secret  
    Authorization: Basic {BASE64-ENCODED authorization-service:AUTH_SERVICE_PASSWORD}  
    Accept: application/json  
    Response:  
    
        {
            "access_token": "b655895b-e0ae-453b-97b3-21921d8448aa",
            "token_type": "bearer",
            "refresh_token": "1c587fc5-6ab9-41b8-8733-8435f6dfd3f4",
            "expires_in": 43199,
            "scope": "read write server"
        }

* Then call the **/auth/user/details** service to test the access-token received from previous call and get the details of the newly registered user.

    HTTP Method: GET  
    http://localhost:9000/api/auth/user/details  
    Authorization: Bearer {ACCESS_TOKEN} which is b655895b-e0ae-453b-97b3-21921d8448aa  
    Content-Type: application/json  
    Response:  
    
        {
          "authorities": [
            {
              "authority": "APPLICATION_CLIENT"
            },
            {
              "authority": "SECURITY_ADMIN"
            }
          ],
          "details": {},
          "authenticated": true,
          "userAuthentication": {
            "authorities": [],
            "details": {
              "grant_type": "password",
              "username": "william"
            },
            "authenticated": true,
            "principal": {
              "id": 1,
              "username": "william",
              "password": "password",
              "firstName": "William",
              "lastName": "Brian",
              "email": "williamb@gmail.com",
              "roles": [
                "server",
                "read",
                "write"
              ],
              "authorities": [
                {
                  "authority": "APPLICATION_CLIENT"
                },
                {
                  "authority": "SECURITY_ADMIN"
                }
              ],
              "accountNonExpired": true,
              "accountNonLocked": true,
              "credentialsNonExpired": true,
              "enabled": true
            }
          }
        }
    
### Notes

* Authorization service uses [MapStruct](http://mapstruct.org/) for mapping between domain object to DTO object. MapStruct requires [mapstruct-processor](https://github.com/mapstruct/mapstruct) to be configured in gradle to generate the corresponding Mapper implementation for defined MapStruct interface. Hence it is highly recommended to **run gradle build before running authorization-service** to avoid Spring NoSuchBeanDefinitionException for MapStruct autowirings.     
