package sg.nus.iss.cts.service;

import java.util.List;

import sg.nus.iss.cts.model.Role;
import sg.nus.iss.cts.model.User;

public interface UserService {

  List<User> findAllUsers();

  User findUser(String userId);

  User createUser(User user);

  User changeUser(User user);

  void removeUser(User user);

  List<Role> findRolesForUser(String userId);

  List<String> findRoleNamesForUser(String userId);

  List<String> findManagerNameByUID(String userId);

  /**
   * Return the respective User object if username and password are correct, null
   * otherwise
   * 
   * @param username
   * @param pwd
   * @return
   */
  User authenticate(String username, String pwd);
}