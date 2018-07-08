Authorization Service
=============

### Running the application

Pass the new AUTH_SERVICE_PASSWORD, CONFIG_SERVICE_PASSWORD from the [config-service](/../config-service/README.md) to access auth-service.yml configuration file, corresponding passwords FINANCE_SERVICE_PASSWORD for finance-service and ANALYTICS_SERVICE_PASSWORD for analytics-service respectively.

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
    Authorization: Basic {BASE64-ENCODED auth-service:AUTH_SERVICE_PASSWORD}  
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
    
