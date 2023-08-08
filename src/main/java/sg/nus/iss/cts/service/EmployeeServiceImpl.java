package sg.nus.iss.cts.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.cts.model.Employee;
import sg.nus.iss.cts.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Resource
  private EmployeeRepository employeeRepository;

  @Override
  @Transactional
  public List<Employee> findEmployeesByManager(String s) {
    return employeeRepository.findEmployeesByManagerId(s);
  }

  @Override
  @Transactional
  public Employee findEmployeeById(String s) {
    return employeeRepository.findEmployeeById(s);
  }

  @Override
  @Transactional
  public List<Employee> findAllEmployees() {
    return employeeRepository.findAll();
  }

  @Override
  @Transactional
  public Employee findEmployee(String empid) {
    return employeeRepository.findById(empid).orElse(null);

  }

  @Override
  @Transactional
  public Employee createEmployee(Employee emp) {
    return employeeRepository.saveAndFlush(emp);
  }

  @Override
  @Transactional
  public Employee changeEmployee(Employee emp) {
    return employeeRepository.saveAndFlush(emp);
  }

  @Override
  @Transactional
  public void removeEmployee(Employee emp) {
    employeeRepository.delete(emp);
  }

  @Override
  @Transactional
  public List<String> findAllManagerNames() {
    return employeeRepository.findAllManagerNames();
  }

  @Override
  @Transactional
  public List<Employee> findAllManagers() {
    return employeeRepository.findAllManagers();
  }

  @Override
  @Transactional
  public List<Employee> findSubordinates(String employeeId) {
    return employeeRepository.findSubordinates(employeeId);
  }

  @Override
  @Transactional
  public List<String> findAllEmployeeIDs() {
    return employeeRepository.findAllEmployeeIDs();
  }
}
