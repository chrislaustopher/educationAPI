package assignment.educationAPI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Hibernate;

import org.junit.Assert;

import java.util.*;

@SpringBootTest
class EducationApiApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@Test
	public void checkUsers() {
		Iterable<User> users = userService.list();
		Assert.assertEquals(23, getSize(users));

		Long id = Long.valueOf(1);
		User user1 = userService.getUser(id);
		Assert.assertEquals("jaredgoff@nfl.com", user1.getEmail());
	}

	@Test
	public void checkCourses() {
		Iterable<Course> courses = courseService.list();
		Assert.assertEquals(5, getSize(courses));

		Long id = Long.valueOf(1);
		Course course1 = courseService.getCourse(id);
		Assert.assertEquals("PlayAction101", course1.getName());
	}

	@Test
	public void getClassesForCreator() {
		Long creatorId = Long.valueOf(3);
		Iterable<Course> courses = courseService.getCoursesByCreator(creatorId);
		Assert.assertEquals(2, getSize(courses));
	}

	@Test
	public void changeCourseName() {
		Long id = Long.valueOf(3);
		courseService.changeCourseName(id, "ZoneRead101");
		Course course = courseService.getCourse(id);
		Assert.assertEquals("ZoneRead101",course.getName());
	}

	@Test
	public void updateStudent() {
		Long id = Long.valueOf(22);
		userService.changeData(id,"Danny","Dimes","dannydimes@nfl.com");
		User user = userService.getUser(id);
		Assert.assertEquals("Danny", user.getFirstName());
	}

	@Test
	public void createUser() {
		userService.addNewUser("Dakota","Prescott","dakprescott@nfl.com");
		Iterable<User> users = userService.list();
		Assert.assertEquals(24, getSize(users));
	}

	@Test
	public void createCourse() {
		Long creatorId = Long.valueOf(19);
		Long courseId = Long.valueOf(6);
		courseService.addNewCourse("SideArm101",creatorId);
		Course course = courseService.getCourse(courseId);
		Assert.assertEquals("SideArm101", course.getName());
	}

	public int getSize(Iterable iter) {
		int sum = 0;
		Iterator it = iter.iterator();
		while (it.hasNext()) {
			it.next();
			sum++;
		}
		return sum;
	}

}
