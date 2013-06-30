# Simple Spring MVC archetype

This is a ready to use simple Spring web application featuring the following frameworks and libraries:

* Spring, Spring Security and Spring MVC for REST support
* Persistence with JPA 2.1:
	* [Eclipselink](http://www.eclipse.org/eclipselink/)
	* [H2](http://h2database.com) in-memory database
	* [Spring Data JPA](http://www.springsource.org/spring-data/jpa)
* [Flyway](http://flywaydb.org/) integration
* Client side using JavaScript, [jqGrid](http://www.trirand.com/blog/) and REST, full CRUD
	* Server side paging and sorting
	* Few lines of [AngularJS](http://angularjs.org)

## Running

Using maven:

	mvn tomcat7:run

And browse to [http://localhost:8080/books](http://localhost:8080/books). Few smoke Spring integration tests are set up, but no real tests implemented. [![Build Status](https://travis-ci.org/nurkiewicz/books.png?branch=master)](https://travis-ci.org/nurkiewicz/books)

## See also

This application is featured in the following articles:

* [*Poor man's CRUD: jqGrid, REST, AJAX, and Spring MVC in one house*](http://nurkiewicz.blogspot.no/2011/07/poor-mans-crud-jqgrid-rest-ajax-and.html)

* [*Mapping enums done right with @Convert in JPA 2.1*](http://nurkiewicz.blogspot.no/2013/06/mapping-enums-done-right-with-convert.html)

* [*su and sudo in Spring Security applications*](http://nurkiewicz.blogspot.no/2013/06/su-and-sudo-in-spring-security.html)

![Screenshot](http://2.bp.blogspot.com/-E5f2LP5ZtQs/ThCg3iqQJcI/AAAAAAAAAcc/aKqjUPF6h8w/s1600/edit_validation_normal.png)