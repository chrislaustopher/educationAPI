package assignment.educationAPI;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class CourseService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CourseRepository courseRepository;

  public Iterable<Course> list() {
      return courseRepository.findAll();
  }

  // GET: get all classes that a user is a creator for
  public Iterable<Course> getCoursesByCreator(Long creatorId) {
    User u = userRepository.findById(creatorId).orElse(null);
    return courseRepository.findByCreator(u);
  }

  // GET: get all students user objects for a class (this should return a list of students)
  public Iterable<User> getStudents(Long id) {
    Course c = courseRepository.findById(id).orElse(null);
    return c.getStudents();
  }

  // POST: update a class name
  public String changeCourseName(Long id, String name) {
    Course c = courseRepository.findById(id).orElse(null);
    c.setName(name);
    courseRepository.save(c);
    return "Student Course Name changed to: " + name;
  }

  // PUT: create a class
  public String addNewCourse(String name, Long creatorId) {
    User u = userRepository.findById(creatorId).orElse(null);
    Course c = new Course(name,u);
    courseRepository.save(c);
    return "Course Saved";
  }

  // GET: get course by id
  public Course getCourse(Long id) {
    return courseRepository.findById(id).orElse(null);
  }

}