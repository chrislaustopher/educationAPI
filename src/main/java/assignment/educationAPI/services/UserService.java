package assignment.educationAPI;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CourseRepository courseRepository;

  public Iterable<User> list() {
      return userRepository.findAll();
  }
  
  // GET: get all classes that a user is a student for
  public Iterable<Course> getCoursesById(Long id) {
    User u = userRepository.findById(id).orElse(null);
    return u.getCourses();
  }

  // POST: add a student to a class
  public String addStudentToCourse(Long courseId, Long studentId) {
    Course c = courseRepository.findById(courseId).orElse(null);
    User u = userRepository.findById(studentId).orElse(null);
    u.addCourse(c);
    userRepository.save(u);
    return studentId + " added to course " + courseId;
  }

  // POST: update a student's first name, last name, and/or email
  public String changeData(Long id, String first, 
    String last, String email) {
    User u = userRepository.findById(id).orElse(null);
    String msg = "User ";
    Boolean comma = false;
    if (first!=null) { 
      u.setFirstName(first);
      msg += "first name set to: " + first;
      comma = true;
    }
    if (last!=null) { 
      if (comma) { msg += ", "; }
      u.setLastName(last);
      msg += "last name set to: " + last;
      comma = true;
    }
    if (email!=null) { 
      if (comma) { msg += ", "; }
      u.setEmail(email);
      msg += "email set to: " + email;
    }
    if (!comma) { msg +=  "data not changed"; }
    userRepository.save(u);
    return msg;
  }

  // PUT: create a User
  public String addNewUser(String first, String last, String email) {
    User u = new User(first,last,email);
    userRepository.save(u);
    return "User saved";
  }

  // Get: get user by id
  public User getUser(Long id) {
    return userRepository.findById(id).orElse(null);
  }
}