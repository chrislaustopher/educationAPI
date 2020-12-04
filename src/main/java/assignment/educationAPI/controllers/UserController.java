package assignment.educationAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller 
@RequestMapping(path="/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CourseRepository courseRepository;

  // PUT: create a User
  @PutMapping(path="/add")
  public @ResponseBody String addNewUser(@RequestParam String first,
    @RequestParam String last, @RequestParam String email) {
    User u = new User(first,last,email);
    userRepository.save(u);
    return "User saved";
  }

  // GET: get list of all users
  @GetMapping(path="/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }

  // Get: get user by id
  @GetMapping(path="/{id}")
  public @ResponseBody User getUser(@PathVariable Long id) {
    return userRepository.findById(id).orElse(null);
  }

  // GET: get all classes that a user is a student for
  @GetMapping(path="/{id}/courses")
  public @ResponseBody Iterable<Course> getCoursesById(@PathVariable Long id) {
    User u = userRepository.findById(id).orElse(null);
    return u.getCourses();
  }

  // POST: add a student to a class
  @PostMapping(path="/addCourse")
  public @ResponseBody String addStudentToCourse(@RequestParam Long courseId,
    @RequestParam Long studentId) {
    Course c = courseRepository.findById(courseId).orElse(null);
    User u = userRepository.findById(studentId).orElse(null);
    u.addCourse(c);
    userRepository.save(u);
    return studentId + " added to course " + courseId;
  }

  // POST: update a student's first name, last name, and/or email
  @PostMapping(path="/change")
  public @ResponseBody String changeEmail(@RequestParam Long id, 
    @RequestParam(required=false) String first, 
    @RequestParam(required=false) String last, 
    @RequestParam(required=false) String email) {
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
}