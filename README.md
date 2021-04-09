# Transaction API

To run the Spring Boot Application
```
./mvnw clean package
java -jar target/transaction-api-0.0.1-SNAPSHOT.jar
```
OR
```
./mvnw spring-boot:run
```
Use swagger-ui to view Transaction API documentation 
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

Openapi 3.0 supports servers dropdown in swagger-ui. This can be used to test the transaction API.

View Health checks at
http://localhost:8080/actuator/health

Jacoco Code coverage is available at target\site\jacoco\index.html

#Assumptions
* The current code only supports one currency(GBP). 
* Both accounts in the transaction are within the same bank.

#Improvements
* Implement unique id generation for Transaction
* Add further validation for fields like minimum/maximum amount per transaction/day 
* Add more Error handling scenarios
* The list of currencies and conversion rates could be retrieved from a service. 
* Add extra features like money transfer to other banks within UK using account number/sort code or outside UK using IBAN. 
* Add Transaction management scenarios 
* Implement Opentracing API to have a full and consistent view of trail of messages/calls in the platform


