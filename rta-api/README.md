# rta-api
Note: This info will change in future. Right now its only for Dev setup/understanding.

### Java version
The project requires Java 8 installed.  
Remember to set the `JAVA_HOME` to use the correct JDK  

  
### Database setup
* Build a schema with name `rta`  
  OR  
  Change database properties in `application.properties` as required.  
  Basically we should have an empty schema with configs specified in application.properties.   
  
* Use import.sql provided in `rta-core -> resources -> sql` to populate database.  

**NOTE:** Don't create any tables(as hibernate will take care of that)

### git
Create a folder where your want to setup your local project.
1. Open Git Bash
2. $ cd CreatedFolderAbove
3. $ git clone http://gitlab.kelltontech.net/RTA_webservices/RTA_webservices.git
4. $ cd RTA_webservices/  
(You are now in master)
5. $ git checkout develop

  
### IDE setup (MOST IMPORTANT)
Make sure your IDE is all set as specified in RTA Dev Environment Doc.  
**New steps** have been added in doc. Please follow them too. 

**NOTE:** Always follow this doc when setting up new workspace environment.

    Import "rta-api" in RTA_webservices folder as existing maven project.


### Project specific build
To run the projects specific build use following maven commands:  
   
*  Open cmd and GOTO `/rta-api` folder:  
    RUN: `mvn clean install`  
    RESULT: BUILD SUCCESS  
     
*  Start Registration Project:  
    GOTO: `/rta/registration` folder  
    RUN: `mvn spring-boot:run`  
    Result: registration app started  
    

### Using services
*  login service request:
   Create a post request for `http://localhost:8080/login` with:  
   Content-Type as application/json  
   Body as {"username":"admin","password":"admin"}  
   With valid credentials, It should response with a token.  
   
    You can use this token now in your subsequent service requests.


*  Make service requests with token:  
   You can make get request for `/user` , `/users` OR `/users/{name}` with:  
   Content-Type as application/json  
   In your request header use `Authorization` to provide token generated in /login request.

    Verify services responds as expected and secured with PreAuthorize user roles assigned. 


### curl requests (optional)
Skip, If you as using tools to make service requests  

    curl should work as command in windows cmd  
    i.e. you need to install curl related binaries

* /login request example:  
    
    curl -i -H "Content-Type: application/json" -X POST http://localhost:8080/login -d "{\"username\":\"admin\",\"password\":\"admin\"}'
    
* /user request example:  

	curl -i -H "Content-Type: application/json" -H "Authorization: TOKEN-RESPONSE_FROM_LOGIN" -X GET http://localhost:8080/user


### Dummy data
Here are  combination (as per import.sql)  

**username** | **password**  
------------ | ------------  
admin  | admin  
rto    | admin  
cco    | admin  
dealer | admin  
customer1 | customer  
customer2 | customer  


### Deployment
*TODO*


Lets begin !! 