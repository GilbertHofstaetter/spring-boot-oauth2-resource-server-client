# spring-boot-oauth2-resource-server-client
Demo project for keycloak integration as an authorisation server in a spring boot resource server and client with **rbac** and **scopes** authorisation based on spring security.

On February 2022 Keycloak announced the deprecation of its adapters, among other things the spring adapter https://www.keycloak.org/2022/02/adapter-deprecation.
In my demo project I show how to integrate keycloak as an authorisation server in a spring boot project that serves as a client and a resource server based on spring security OAuth2.

Thanks to:
* https://www.djamware.com/post/6225b66ba88c55c95abca0b6/spring-boot-security-postgresql-and-keycloak-rest-api-oauth2
* https://stackoverflow.com/questions/72719400/how-to-configure-oauth2-in-spring-boot-be-spring-boot-fe-keycloak

1. Download the latest keycloak server from https://www.keycloak.org/downloads. In my case version 20.0.0. The admin console gui has changed a bit.
2. Unzip and open a terminal and start keycloak with **bin/kc.sh start-dev --http-port 8091** 
3. Follow the tutorial from https://www.djamware.com/post/6225b66ba88c55c95abca0b6/spring-boot-security-postgresql-and-keycloak-rest-api-oauth2
4. Create a Realm named **Demo-Realm** and a client named **springboot-microservice**
5. Create 3 users named employee1 - employee3 and 2 realm roles named **app-user** and **app-admin**
6. Create 2 user roles named **user** and **admin** within client **springboot-microservice**. Don't forget to set a password.
7. Associate realm role **app-admin** with role **admin** and do the same with **app-user** and role **user**
8. Assign user employee1 to realm role **app-user**, employee2 to realm role **app-admin** and employee3 to realm roles **app-user** and **app-admin**
9. Visit Real-Settings in keycloak admin console and open **OpenID Endpoint Configuration**
10. Copy the client secret from **springboot-microservice** client under section credentials.
10. Try to receive a token via token endpoint 

