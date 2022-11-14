# spring-boot-oauth2-resource-server-client
Demo project for keycloak integration as an authorisation server in a spring boot resource server and client. Custom **rbac** and **scopes** authorisation based on spring security is implemented including OpenApi endpoint support. 

**Build project**

* mvn clean install


**Endpoints**

* http://localhost:8080/api/customer/list
* http://localhost:8080/api/employee/list
* http://localhost:8080/userJwt
* http://localhost:8080/userOAuth2
* http://localhost:8080/logout

**Swagger-Ui**

* http://localhost:8080/swagger-ui.html

On February 2022 Keycloak announced the deprecation of its adapters, among other things the spring adapter https://www.keycloak.org/2022/02/adapter-deprecation.
In my demo project I show how to integrate keycloak as an authorisation server in a spring boot project that serves as a client and a resource server based on spring security OAuth2.

Thanks to:
* https://www.djamware.com/post/6225b66ba88c55c95abca0b6/spring-boot-security-postgresql-and-keycloak-rest-api-oauth2
* https://stackoverflow.com/questions/72719400/how-to-configure-oauth2-in-spring-boot-be-spring-boot-fe-keycloak

**See Turorial.pdf for further infos.** \
**See Turorial Docker setup.pdf for further infos about running the project with Docker.**

