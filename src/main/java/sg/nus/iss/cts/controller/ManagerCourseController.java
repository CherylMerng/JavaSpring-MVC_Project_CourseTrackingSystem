package sg.nus.iss.cts.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import sg.nus.iss.cts.model.Approve;
import sg.nus.iss.cts.model.Course;
import sg.nus.iss.cts.model.CourseEventEnum;
import sg.nus.iss.cts.model.Employee;
import sg.nus.iss.cts.model.UserSession;
import sg.nus.iss.cts.service.CourseService;

@Controller
@RequestMapping(value = "/manager")
public class ManagerCourseController {

  @Autowired
  private CourseService cService;

  @InitBinder
  private void initBinder(WebDataBinder binder) {

  }

  @RequestMapping(value = "/pending")
  public String pendingApprovals(HttpSession session, Model model) {
    UserSession usession = (UserSession) session.getAttribute("usession");

    Map<Employee, List<Course>> subordinateToCoursesMap = new HashMap<>();
    for (Employee subordinate : usession.getSubordinates()) {
      List<Course> clist = cService.findPendingCoursesByEID(subordinate.getEmployeeId());
      subordinateToCoursesMap.put(subordinate, clist);
    }

    model.addAttribute("pendinghistory", subordinateToCoursesMap);

    return "manager-course-pending";
  }

  @RequestMapping(value = "/subordinates-history")
  public String subordinatesHistory(HttpSession session, Model model) {
    UserSession usession = (UserSession) session.getAttribute("usession");

    Map<Employee, List<Course>> subordinateToCoursesMap = new HashMap<>();
    for (Employee subordinate : usession.getSubordinates()) {
      subordinateToCoursesMap.put(subordinate, cService.findCoursesByEID(subordinate.getEmployeeId()));
    }

    model.addAttribute("submap", subordinateToCoursesMap);

    return "manager-subordinate-course-history";

  }

  @GetMapping("/course/display/{id}")
  public String newDepartmentPage(@PathVariable int id, Model model) {
    Course course = cService.findCourse(id);
    model.addAttribute("course", course);
    model.addAttribute("approve", new Approve());

    return "manager-course-detail";
  }

  @PostMapping("/course/edit/{id}")
  public String approveOrRejectCourse(@ModelAttribute("approve") @Valid Approve approve, BindingResult result,
      @PathVariable Integer id) {
    if (result.hasErrors())
      return "manager-course-detail";

    Course c = cService.findCourse(id);
    if (approve.getDecision().trim().equalsIgnoreCase(CourseEventEnum.APPROVED.toString())) {
      c.setStatus(CourseEventEnum.APPROVED);
    } else {
      c.setStatus(CourseEventEnum.REJECTED);
    }

    cService.changeCourse(c);
    String message = "Course was successfully updated.";
    System.out.println(message);

    return "redirect:/manager/pending";
  }

}
