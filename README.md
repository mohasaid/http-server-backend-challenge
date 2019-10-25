# Backend challenge

The purpose of this backend challenge is to write a HTTP-based mini game back-end in Java which registers game scores for different users and levels, with the capability to return high score lists per level. There shall also be a simple login-system in place (without any authentication...). It is not permitted to use any external framework or any class not included in the JDK, except for testing. And for HTTP, you need to use com.sun.net.httpserver.HttpServer.

There are going to be three use cases:

##### Login:

   - This use case returns a session key in the form of a string (without spaces or “strange” characters) which shall be valid for use with the other functions for 10 minutes. The session keys should be “reasonably unique”.
    
```
Request: GET /<userid>/login
Response: <sessionkey>
<userid> : 31 bit unsigned integer number
<sessionkey> : A string representing session (valid for 10 minutes).

Example: http://localhost:8081/4711/login --> UICSNDK
```

##### Post a user's score to a level

   - Only requests with valid session keys shall be processed.

```
Request: POST /<levelid>/score?sessionkey=<sessionkey> 
Request body: <score>
Response: (nothing)
<levelid> : 31 bit unsigned integer number
<sessionkey> : A session key string retrieved from the login function. 
<score> : 31 bit unsigned integer number

Example: POST http://localhost:8081/2/score?sessionkey=UICSNDK (with the post body: 1500)
```

##### Get a high score list for a level:

   - Retrieves the high scores for a specific level. The result is a comma separated list in descending score order. Because of memory reasons no more than 15 scores are to be returned for each level. Only the highest score counts. ie: an user id can only appear at most once in the list. If a user hasn't submitted a score for the level, no score is present for that user. A request for a high score list of a level without any scores submitted shall be an empty string.


```
Request: GET /<levelid>/highscorelist 
Response: CSV of <userid>=<score>
<levelid> : 31 bit unsigned integer number
<score> : 31 bit unsigned integer number
<userid> : 31 bit unsigned integer number

Example: http://localhost:8081/2/highscorelist -> 4711=1500,131=1220
```

### Summary

This is my solution to the HTTP-based mini game backend-server. I tried to follow the rules of [DDD](https://en.wikipedia.org/wiki/Domain-driven_design) together with [CQS](https://en.wikipedia.org/wiki/Command–query_separation) while following good software engineering practices.

Due to lack of time, I tried to focus on the code structure, concurrency issues and the design of the system. But if I had more time, I would like to improve the tests, because I'm currently testing the use cases and the repositories, and I would like to add unit tests to all the remaining components like domain models, domain services, server handlers and end to end tests as well.

In the root folder there is `backend-challenge-1.0-SNAPSHOT.jar` which is the jar that can be used to run the server.


### Considerations

I made some considerations that were not included in the statement:

- There is no need to delete expired sessions. In case we should need it, we could use a java timer that could be executed every X seconds to delete them.

- Return codes are all grouped to:
    - 200 -> everything worked fine
    - 405 -> method not allowed
    - 500 -> all other exceptions

### Improvements

- Document it
- Configure logger and add logs
- Covering the rest of the code more extensively with unit, integration and end to end tests would be a good practice

### Prerequisites

* JDK 1.11+
* Maven 3.0+

### Build and run 

To build the project you need to execute from the root folder: 

```
$ mvn package
```

To run the server you need to execute from the root folder: 

```
$ java -jar target/backend-challenge-1.0-SNAPSHOT.jar
```



