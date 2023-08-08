package sg.nus.iss.cts.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.nus.iss.cts.model.Employee;

@Component
public class EmployeeValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return Employee.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    System.out.println(target);
    
    ValidationUtils.rejectIfEmpty(errors, "employeeId", "error.employee.employeeid.empty");
    ValidationUtils.rejectIfEmpty(errors, "name", "error.employee.name.empty");
  }

}
