package sg.nus.iss.cts.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.cts.exception.CourseNotFound;
import sg.nus.iss.cts.model.Course;
import sg.nus.iss.cts.model.CourseEventEnum;
import sg.nus.iss.cts.model.UserSession;
import sg.nus.iss.cts.service.CourseService;
import sg.nus.iss.cts.validator.CourseValidator;

@Controller
@RequestMapping(value = "/staff")
public class CourseController {
  @Autowired
  private CourseService courseService;

  @Autowired
  private CourseValidator courseValidator;

  @InitBinder("course")
  private void initCourseBinder(WebDataBinder binder) {
    binder.addValidators(courseValidator);
  }

  /**
   * COURSE CRUD OPERATIONS
   * 
   * @return
   */
  @RequestMapping(value = "course/history")
  public String employeeCourseHistory(HttpSession session, Model model) {
    UserSession usession = (UserSession) session.getAttribute("usession");

    System.out.println(usession.getEmployee());

    List<Course> employeeCourses = courseService.findCoursesByEID(usession.getEmployee().getEmployeeId());
    model.addAttribute("chistory", employeeCourses);

    return "course-my-history";
  }

  @GetMapping("/course/create")
  public String newCoursePage(Model model) {
    model.addAttribute("course", new Course());

    return "course-new";
  }

  @PostMapping("/course/create")
  public String createNewCourse(@ModelAttribute @Valid Course course, BindingResult result,
      HttpSession session) {
    if (result.hasErrors()) {
      return "course-new";
    }

    UserSession usession = (UserSession) session.getAttribute("usession");

    course.setEmployeeId(usession.getEmployee().getEmployeeId());
    course.setStatus(CourseEventEnum.SUBMITTED);
    courseService.createCourse(course);

    String message = "New course " + course.getCourseId() + " was successfully created.";
    System.out.println(message);

    return "redirect:/staff/course/history";
  }

  @GetMapping("/course/edit/{id}")
  // id in html page -> @PathVariable Integer id

  public String editCoursePage(@PathVariable Integer id, Model model) {
    Course course = courseService.findCourse(id); // get from db
    model.addAttribute("course", course); // put in to model(= html)

    return "course-edit";
  }

  @PostMapping("/course/edit/{id}")
  public String editCourse(@ModelAttribute @Valid Course course, BindingResult result, @PathVariable Integer id,
      HttpSession session) throws CourseNotFound {
    if (result.hasErrors())
      return "course-edit";

    System.out.println("DATES****" + course.getFromDate() + course.getToDate());

    UserSession usession = (UserSession) session.getAttribute("usession");
    course.setEmployeeId(usession.getEmployee().getEmployeeId());
    course.setStatus(CourseEventEnum.UPDATED);

    courseService.changeCourse(course);

    return "redirect:/staff/course/history";
  }

  @RequestMapping(value = "/course/withdraw/{id}")
  public String deleteCourse(@PathVariable Integer id) throws CourseNotFound {
    Course course = courseService.findCourse(id);

    course.setStatus(CourseEventEnum.WITHDRAWN);
    courseService.changeCourse(course);

    String message = "Course " + course.getCourseId() + " was successfully withdrawn.";
    System.out.println(message);

    return "redirect:/staff/course/history";
  }

}
