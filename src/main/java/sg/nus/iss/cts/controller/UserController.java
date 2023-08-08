package sg.nus.iss.cts.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.nus.iss.cts.exception.UserNotFound;
import sg.nus.iss.cts.model.Role;
import sg.nus.iss.cts.model.User;
import sg.nus.iss.cts.model.UserSession;
import sg.nus.iss.cts.service.EmployeeService;
import sg.nus.iss.cts.service.RoleService;
import sg.nus.iss.cts.service.UserService;
import sg.nus.iss.cts.validator.UserValidator;

@Controller
@RequestMapping(value = "/admin/user")
@SessionAttributes(value = { "usession" }, types = { UserSession.class })
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private UserValidator userValidator;

  @InitBinder("user")
  private void initUserBinder(WebDataBinder binder) {
    binder.addValidators(userValidator);
  }

  /**
   * USER CRUD OPERATIONS
   * 
   * @return
   */
  @RequestMapping(value = "/list")
  public String userListPage(Model model) {
    List<User> userList = userService.findAllUsers();
    model.addAttribute("userList", userList);

    return "user-list";
  }

  // @RequestMapping(value = "/create", method = RequestMethod.GET)
  @GetMapping("/create")
  public String newUserPage(Model model) {
    model.addAttribute("user", new User());
    List<String> eidList = employeeService.findAllEmployeeIDs();
    model.addAttribute("eidlist", eidList);
    List<Role> roles = roleService.findAllRoles();
    model.addAttribute("roles", roles);

    return "user-new";
  }

  @PostMapping("/create")
  public String createNewUser(@ModelAttribute @Valid User user, BindingResult result, Model model) {
    if (result.hasErrors()) {
      List<String> eidList = employeeService.findAllEmployeeIDs();
      model.addAttribute("eidlist", eidList);
      List<Role> roles = roleService.findAllRoles();
      model.addAttribute("roles", roles);

      return "user-new";
    }

    // The roles from user input only has id, we retrieve
    // the respective Role object
    List<Role> newRoleSet = new ArrayList<Role>();
    user.getRoleSet().forEach(role -> {
      Role completeRole = roleService.findRole(role.getRoleId());
      newRoleSet.add(completeRole);
    });
    user.setRoleSet(newRoleSet);

    userService.createUser(user);

    return "redirect:/admin/user/list";
  }

  @GetMapping("/edit/{id}")
  public String editUserPage(@PathVariable String id, Model model) {
    User user = userService.findUser(id);
    model.addAttribute("user", user);

    List<Role> roles = roleService.findAllRoles();
    model.addAttribute("allRoles", roles);

    return "user-edit";
  }

  @PostMapping("/edit/{id}")
  public String editUser(@ModelAttribute @Valid User user, BindingResult result,
      @PathVariable String id) throws UserNotFound {
    if (result.hasErrors()) {
      return "user-edit";
    }

    userService.changeUser(user);

    String message = "User was successfully updated.";
    System.out.println(message);

    return "redirect:/admin/user/list";
  }

  @RequestMapping(value = "/delete/{id}")
  public String deleteUser(@PathVariable String id)
      throws UserNotFound {
    User user = userService.findUser(id);
    userService.removeUser(user);

    String message = "The user " + user.getUserId() + " was successfully deleted.";
    System.out.println(message);

    return "redirect:/admin/user/list";
  }
}
