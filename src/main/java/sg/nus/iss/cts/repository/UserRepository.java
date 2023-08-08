package sg.nus.iss.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.cts.model.User;

public interface UserRepository extends JpaRepository<User, String> {
  @Query("SELECT DISTINCT e2.name FROM User u, Employee e1, Employee e2 WHERE u.employeeId = e1.employeeId AND e1.managerId = e2.employeeId AND u.userId=:uid")
  List<String> findManagerNamesByUID(@Param("uid") String uid);
  
  @Query("SELECT u FROM User u WHERE u.name=:username AND u.password=:pwd")
  User findUserByNamePwd(@Param("username")String username, @Param("pwd")String pwd);
}
