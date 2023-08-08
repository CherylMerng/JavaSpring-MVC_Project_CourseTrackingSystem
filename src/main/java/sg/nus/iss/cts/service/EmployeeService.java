package sg.nus.iss.cts.service;

import java.util.List;

import sg.nus.iss.cts.model.Employee;

public interface EmployeeService {
  List<Employee> findEmployeesByManager(String s);

  Employee findEmployeeById(String s);

  List<Employee> findAllEmployees();

  Employee findEmployee(String empid);

  Employee createEmployee(Employee emp);

  Employee changeEmployee(Employee emp);

  void removeEmployee(Employee emp);

  List<String> findAllManagerNames();

  List<Employee> findAllManagers();

  List<Employee> findSubordinates(String employeeId);

  List<String> findAllEmployeeIDs();
}
