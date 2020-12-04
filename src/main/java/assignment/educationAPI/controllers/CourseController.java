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
@RequestMapping(path="/course")
public class CourseController {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private UserRepository userRepository;

  // PUT: create a class
  @PutMapping(path="/add")
  public @ResponseBody String addNewCourse(@RequestParam String name,
    @RequestParam Long creatorId) {
    User u = userRepository.findById(creatorId).orElse(null);
    Course c = new Course(name,u);
    courseRepository.save(c);
    return "Course Saved";
  }

  // GET: get list of all courses
  @GetMapping(path="/all")
  public @ResponseBody Iterable<Course> getAllCourses() {
    // This returns a JSON or XML with the users
    return courseRepository.findAll();
  }

  // GET: get course by id
  @GetMapping(path="/{id}")
  public @ResponseBody Course getCourse(@PathVariable Long id) {
    return courseRepository.findById(id).orElse(null);
  }

  // GET: get all classes that a user is a creator for
  @GetMapping(path="/createdBy/{creatorId}")
  public @ResponseBody Iterable<Course> getCoursesByCreator(@PathVariable Long creatorId) {
    User u = userRepository.findById(creatorId).orElse(null);
    return courseRepository.findByCreator(u);
  }

  // GET: get all students user objects for a class (this should return a list of students)
  @GetMapping(path="/{id}/students")
  public @ResponseBody Iterable<User> getStudents(@PathVariable Long id) {
    Course c = courseRepository.findById(id).orElse(null);
    return c.getStudents();
  }

  // POST: update a class name
  @PostMapping(path="/change")
  public @ResponseBody String changeCourseName(@RequestParam Long id,
    @RequestParam String name) {
    Course c = courseRepository.findById(id).orElse(null);
    c.setName(name);
    courseRepository.save(c);
    return "Student Course Name changed to: " + name;
  }

}










