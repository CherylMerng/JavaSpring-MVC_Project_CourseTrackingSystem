package sg.nus.iss.cts;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.cts.model.Course;
import sg.nus.iss.cts.model.CourseEventEnum;
import sg.nus.iss.cts.model.Employee;
import sg.nus.iss.cts.model.Role;
import sg.nus.iss.cts.model.User;
import sg.nus.iss.cts.repository.CourseRepository;
import sg.nus.iss.cts.repository.EmployeeRepository;
import sg.nus.iss.cts.repository.RoleRepository;
import sg.nus.iss.cts.repository.UserRepository;

@SpringBootApplication
public class CourseTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseTrackingSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner loadData(RoleRepository roleRepository, 
                            EmployeeRepository employeeRepository,
                            UserRepository userRepository,
                            CourseRepository courseRepository) {
    return (args) -> {
      // Add a few Roles
      Role adminRole = roleRepository.save(new Role("admin", "Administrator", "System administrator"));
      Role staffRole = roleRepository.save(new Role("staff", "Staff", "Staff members"));
      Role managerRole = roleRepository.save(new Role("manager", "Manager", "Manager"));
      
      // Add a few Employees
      employeeRepository.save(new Employee("101034", "Admin"));
      employeeRepository.save(new Employee("100027", "Esther Tan"));
      employeeRepository.save(new Employee("312025", "Nguyen Tri Tin", "100027"));
      employeeRepository.save(new Employee("310017", "Cher Wah", "100027"));
      employeeRepository.save(new Employee("110239", "Yuen Kwan", "100027"));
      
      // Add a few Users
      User admin = new User("admin", "admin", "password", "101034");
      User esther = new User("esther", "esther", "password", "100027");
      User tin = new User("tin", "tin", "password", "312025");
      User cherwah = new User("cherwah", "cherwah", "password", "310017");
      
      admin.setRoleSet(Arrays.asList(adminRole));
      esther.setRoleSet(Arrays.asList(staffRole, managerRole));
      tin.setRoleSet(Arrays.asList(staffRole));
      cherwah.setRoleSet(Arrays.asList(staffRole));
      
      userRepository.saveAndFlush(admin);
      userRepository.saveAndFlush(esther);
      userRepository.saveAndFlush(tin);
      userRepository.saveAndFlush(cherwah);
      
      // Add a few course applications
      Course course1 = new Course();
      course1.setCourseName("Object Oriented Programming");
      course1.setOrganiser("National Universitiy of Singapore");
      course1.setFromDate(LocalDate.now());
      course1.setToDate(LocalDate.now().plusMonths(1));
      course1.setFees(3500.0);
      course1.setGstIncluded(true);
      course1.setJustification("OOP is the best");
      course1.setEmployeeId(tin.getEmployeeId());
      course1.setStatus(CourseEventEnum.SUBMITTED);
      
      courseRepository.saveAndFlush(course1);
      
      Course course2 = new Course();
      course2.setCourseName("ASP.NET");
      course2.setOrganiser("National Universitiy of Singapore");
      course2.setFromDate(LocalDate.now().plusMonths(2));
      course2.setToDate(LocalDate.now().plusMonths(3));
      course2.setFees(4500.0);
      course2.setGstIncluded(true);
      course2.setJustification("Who does not love MVC?");
      course2.setEmployeeId(cherwah.getEmployeeId());
      course2.setStatus(CourseEventEnum.SUBMITTED);
      courseRepository.saveAndFlush(course2);
    };
	}
}
