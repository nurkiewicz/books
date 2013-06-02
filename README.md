# Simple Spring MVC archetype

This is a ready to use simple Spring web application featuring the following frameworks and libraries:

* Spring and Spring MVC for REST support
* Persistence with JPA 2.1:
	* [Eclipselink](http://www.eclipse.org/eclipselink/)
	* [H2](http://h2database.com) in-memory database
	* [Spring Data JPA](http://www.springsource.org/spring-data/jpa)
* [Flyway](http://flywaydb.org/) integration
* Client side using JavaScript, [jqGrid](http://www.trirand.com/blog/) and REST, full CRUD
	* Server side paging and sorting

## Running

Using maven:

	mvn tomcat7:run

And browse to [http://localhost:8080/books](http://localhost:8080/books). Few smoke Spring integration tests are set up, but no real tests implemented.

## See also

This application was created for the purposes of [*Poor man's CRUD: jqGrid, REST, AJAX, and Spring MVC in one house*](http://nurkiewicz.blogspot.no/2011/07/poor-mans-crud-jqgrid-rest-ajax-and.html) article.

![Screenshot](http://2.bp.blogspot.com/-E5f2LP5ZtQs/ThCg3iqQJcI/AAAAAAAAAcc/aKqjUPF6h8w/s1600/edit_validation_normal.png)