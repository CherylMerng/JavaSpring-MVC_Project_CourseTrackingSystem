package sg.nus.iss.cts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.nus.iss.cts.exception.EmployeeNotFound;
import sg.nus.iss.cts.model.Employee;
import sg.nus.iss.cts.model.UserSession;
import sg.nus.iss.cts.service.EmployeeService;
import sg.nus.iss.cts.validator.EmployeeValidator;

@Controller
@RequestMapping("/admin/employee")
@SessionAttributes(value = { "usession" }, types = { UserSession.class })
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private EmployeeValidator employeeValidator;

  @InitBinder("employee")
  private void initEmployeeBinder(WebDataBinder binder) {
    binder.addValidators(employeeValidator);
  }

  // ------------------------
  // EMPLOYEE CRUD OPERATIONS
  // ------------------------

  @RequestMapping(value = "/list")
  public String employeeListPage(Model model) {
    List<Employee> employeeList = employeeService.findAllEmployees();
    model.addAttribute("employeeList", employeeList);

    return "employee-list";
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String newEmployeePage(Model model) {
    model.addAttribute("employee", new Employee());
    model.addAttribute("eidlist", employeeService.findAllEmployeeIDs());

    return "employee-new";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createNewEmployee(@ModelAttribute @Valid Employee employee, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("eidlist", employeeService.findAllEmployeeIDs());
      return "employee-new";
    }

    employeeService.createEmployee(employee);

    String message = "New employee " + employee.getEmployeeId() + " was successfully created.";
    System.out.println(message);

    return "redirect:/admin/employee/list";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public String editEmployeePage(@PathVariable String id, Model model) {
    Employee employee = employeeService.findEmployee(id);
    model.addAttribute("employee", employee);
    model.addAttribute("eidlist", employeeService.findAllEmployeeIDs());

    return "employee-edit";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
  public String editEmployee(@ModelAttribute @Valid Employee employee, BindingResult result,
      @PathVariable String id) throws EmployeeNotFound {
    if (result.hasErrors()) {
      return "employee-edit";
    }

    employeeService.changeEmployee(employee);

    String message = "Employee was successfully updated.";
    System.out.println(message);

    return "redirect:/admin/employee/list";
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  public String deleteEmployee(@PathVariable String id)
      throws EmployeeNotFound {
    Employee employee = employeeService.findEmployee(id);
    employeeService.removeEmployee(employee);

    String message = "The employee " + employee.getEmployeeId() + " was successfully deleted.";
    System.out.println(message);

    return "forward:/admin/employee/list";
  }
}
