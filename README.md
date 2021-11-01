# SpringBoot Basic Customer Account Example
Basic example of customer account management built with Spring Boot

The basic solution implements the below domain object models and relationship, designed with clean architecture.

![Alt text](customer-account-erd.png?raw=true "Customer-Account Object Relationship Model")

The REST endpoints of the solution are :
1. [Create-A-Customer](http://localhost:8080/api/customers) 
   - eg. curl -i -X POST -H "Content-Type:application/json" -d "{\"name\":\"John\",\"email\":\"john@abc.com\", \"accounts\":[{\"accNumber\":\"001-02314313-020\",\"balance\"00}]}" http://localhost:8080/api/customers
       
2. [Get-All-Customers](http://localhost:8080/api/customers) 
   - eg. curl -i -X GET -H "Content-Type:application/json"  http://localhost:8080/api/customers/

3. [Get-A-Customer-IdentifiedBy-CustomerId](http://localhost:8080/api/customers/100) 
   - eg. curl -i -X GET -H "Content-Type:application/json"  http://localhost:8080/api/customers/100
  
4. [Get-All-Customers-IdentifiedBy-CustomerAccountId](http://localhost:8080/api/customers/100/accounts/1000)  
   - eg. curl -i -X GET -H "Content-Type:application/json"  http://localhost:8080/api/customers/100
