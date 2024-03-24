# Quiz API

### Introduction

Quiz API is a small project, built to explore spring's capabilities around user creation, password management and ORM.

### Further notes

* The "api/register" endpoint is exposed in order for a user to be able to register
* A user is able then to perform CRUD operations on potential quizzes, i.e a user can create a quiz by posting to
 `/api/quizzes` the following payload:
 ```
 {
   "title": "The Java Logo",
   "text": "What is depicted on the Java logo?",
   "options": ["Robot","Tea leaf","Cup of coffee","Bug"],
   "answer": [2]
 }
 ```
 * A user is able to solve a specific quiz by posting to `/quizzes/{id}/solve` a payload similar to the following:
 ```
 {
     "answer": [2]
 }
 ```
 The answer is a list since the correct answers might be more than one.
 * A user can retrieve his solved quizzes in a paginated way. It's worth mentioning that a quiz can be solved more
 than once

### Technologies Used

* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Spring Security](https://spring.io/projects/spring-security)
* [Java Bean Validation](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html)
* [H2](https://www.h2database.com/html/main.html)
