# soilops
Soil sensor monitoring server 


## Usage

### Configuring
* Go to this link and create a github oauth application: https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/
* Go to this link and create a google oauth application: https://developers.google.com/identity/protocols/OAuth2
* Create a file `config/application.yml` with the following content, replace the client id and security key:
```security:
     oauth2:
       client:
         registration:
           github:
             client-id: your client id
             client-secret: your client secret
           google:
             client-id: your client id
             client-secret: your client secret
```

### Running 
* Compile the software using `mvn clean install`
* Run it using `java -jar target/soilops-0.0.1-SNAPSHOT-exec.jar`
* Go to http://localhost:8080/



