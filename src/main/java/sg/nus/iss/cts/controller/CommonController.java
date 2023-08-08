package sg.nus.iss.cts.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.nus.iss.cts.model.Employee;
import sg.nus.iss.cts.model.User;
import sg.nus.iss.cts.model.UserSession;
import sg.nus.iss.cts.service.EmployeeService;
import sg.nus.iss.cts.service.UserService;

@Controller
public class CommonController {
  @Autowired
  private UserService userService;

  @Autowired
  private EmployeeService employeeService;

  @RequestMapping(value = { "/", "/login", "/home" }, method = RequestMethod.GET)
  public String login(Model model) {
    model.addAttribute("user", new User());

    return "login";
  }

  @RequestMapping(value = "/home/authenticate")
  public String authenticate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model,
      HttpSession session) {
    if (bindingResult.hasErrors()) {
      return "login";
    }

    User u = userService.authenticate(user.getName(), user.getPassword());
    if (u == null) {
      model.addAttribute("loginMessage", "Incorrect username/password");
      return "login";
    }

    // Login ok, let's put the user info into a session
    // a. The user object itself
    UserSession userSession = new UserSession();
    userSession.setUser(u);

    // b. The respective employee object
    userSession.setEmployee(employeeService.findEmployeeById(u.getEmployeeId()));

    // c. The subordinates
    List<Employee> subordinates = employeeService.findSubordinates(u.getEmployeeId());
    if (subordinates != null) {
      userSession.setSubordinates(subordinates);
    }

    session.setAttribute("usession", userSession);

    List<String> roleIds = u.getRoleIds();
    System.out.println("Roles:");
    roleIds.forEach(System.out::println);

    // Done, let's redirect to respective page
    if (roleIds.contains("admin")) {
      return "redirect:/admin/employee/list";
    }

    if (roleIds.contains("manager")) {
      return "redirect:/manager/pending";
    }

    return "redirect:/staff/course/history";
  }

  @GetMapping("/about")
  public String home() {
    return "about";
  }

  @RequestMapping(value = "/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/home";
  }
}
