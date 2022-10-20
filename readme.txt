Petstagram - social network for Pets, project build on Spring boot

Libraries used{
    -Thymeleaf
    -Lombok
    -Spring Data Jpa/Hibernate
    -Spring Security
    -Liquibase
    -Postgresql
    -Mapstruct
    -PrettyTime
    -H2 database for junit testing
}

Demonstration video - https://youtu.be/QuHmrXbixJs

In order to launch it on your computer you must have
Intellij Idea
Java 17
Postgresql

Download the project, create a database in your pgAdmin called petstagram

default username and password is "postgres", if your postgres password is different, please change it in
src/main/resources/application.properties

There are total of 27 tests, in order to launch tests execute gradle build

default server port is 8082