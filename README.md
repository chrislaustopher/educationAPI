## Assignment
Your task will be to write an API and any corresponding data objects in Java using Spring Boot and any other tools you want (e.g., Hibernate, MySQL).

The API will be used for handling educational classes. Here are the MINIMUM (feel free to add more) requirements for the data objects that you will have to create:

**Class**
>Has: ID, Class name, creator User ID, and list of student User IDs

**User**
>Has: ID, first name, last name, email

---
# educationAPI

* used: Spring Boot, Hibernate
* data: stored in Hibernate, populates automatically from /resources/import.sql

* to run project locally
> * command line: ./mvnw spring-boot:run
* requires: Maven, JDK, Hibernate

to test:
* run my written tests in junit
> * command line: mvn test
* use any browser for URI path GET requests
* use curl in command line for HTTP requests

---

### Table Schema
>Note: observable in localhost:8080/h2-console

COURSE: id, name, creator_id

USER: id, first_name, last_name, email

STUDENT_COURSE: USER.id as student_id, COURSE.id as course_id

---

### API Calls
> * Note: I used 127.0.0.1 instead of localhost in my terminal due to unknown network restrictions
> * Note: I used Course instead of Class for this project to avoid complications with java classes
> * Note: Time complexity: C = length of COURSE table; U = length of USER table
> * Note: The examples below are tested in /test/java/assignment/educationAPI/EducationApiApplicationTests.java

* GET: get all classes that a user is a creator for
	> * URI Path: localhost:8080/course/createdBy/{User.id}
	> * HTTP: curl -v localhost:8080/course/createdBy/{User.id}
	> * example: /course/createdBy/3 should return 2 courses by Aaron Rodgers(id=3)

	time complexity: O(C), only searches COURSE table

* GET: get all classes that a user is a student for
	> * URI Path: localhost:8080/user/{User.id}/courses
	> * HTTP: curl -v localhost:8080/user/{User.id}/courses
	> * example: /user/16/courses should return courses 2,3,4 for Kyler Murray(id=16)

	time complexity: O(UC), searches USER_COURSE table

* GET: get all students user objects for a class (this should return a list of students)
	> * URI Path: localhost:8080/course/{Course.id}/students
	> * HTTP: curl -v localhost:8080/course/{Course.id}/students
	> * example: /course/4/students should return the students for class:HailMary101(id=4)

	time complexity: O(UC), searches USER_COURSE table

* POST: update a class name
	> * HTTP: curl -v -X POST -d id={Course.id} -d name={New Name} localhost:8080/course/change
	> * example: POST -d id=3 -d name=ZoneRead101 localhost:8080/course/change should change QBDraw101 to ZoneRead101

	time complexity: O(1), access by Course.id is linear

* POST: add a student to a class
	> * HTTP: curl -v -X POST -d courseId={Course.id} -d studentId={User.id} 127.0.0.1:8080/user/addCourse
	> * example: POST -d courseId=2 -d studentId=7 127.0.0.1:8080/user/addCourse should add Cam Newton(id=7) to course:DeepBall101(id=2)

	time complexity: O(1), access by User.id and Course.id is linear

* POST: update a student's first name, last name, and/or email
	> * HTTP: curl -v -X POST -d id={User.id} -d first={New First Name} -d last={New Last Name} -d email={New Email} localhost:8080/user/change (all fields are optional for and/or functionality)
	> * example: POST -d id=22 -d first=Danny -d last=Dimes localhost:8080/user/change should change Daniel Jones(id=22) name to Danny Dimes

	time complexity: O(1), access by User.id is linear

* PUT: create a User
	> * HTTP: curl -v -X PUT -d first={User.firstName} -d last=Prescott{User.lastName} -d email={User.email} localhost:8080/user/add
	> * example: PUT -d first=Dakota -d last=Prescott -d email=dakotaprescott@nfl.com localhost:8080/user/add should add a new user named Dakota Prescott to the USER table

	time complexity: O(1), add is linear

* PUT: create a class (class name and creator user ID should be here by default. Error checking on invalid user is also welcome)
	> * HTTP: curl -v -X PUT -d name={Course.name} -d creatorId={User.id} localhost:8080/course/add
	(for invalid creatorId, should return status: 400 Bad Request)
	> * example: PUT -d name=SideArm101 -d creatorId=19 localhost:8080/course/add should create a new course called SideArm101(id=6) created by Patrick Mahomes(id=19)

	time complexity: O(1), add is linear

Additional Available HTTP Requests:
* GET: user by id
	> * URI Path: localhost:8080/user/{User.id}
* GET: course by id
	> * URI Path: localhost:8080/course/{Course.id}
* GET: all users
	> * URI Path: localhost:8080/user/all
* GET: all courses
	> * URI Path: localhost:8080/course/all

