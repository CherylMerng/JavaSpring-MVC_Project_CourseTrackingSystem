package sg.nus.iss.cts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.cts.model.Role;
import sg.nus.iss.cts.model.User;
import sg.nus.iss.cts.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Transactional
  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Transactional
  @Override
  public User findUser(String userId) {
    return userRepository.findById(userId).orElse(null);
  }

  @Transactional
  @Override
  public User createUser(User user) {
    return userRepository.saveAndFlush(user);
  }

  @Transactional
  @Override
  public User changeUser(User user) {
    return userRepository.saveAndFlush(user);
  }

  @Transactional
  @Override
  public void removeUser(User user) {
    userRepository.delete(user);
  }

  @Transactional
  @Override
  public List<Role> findRolesForUser(String userId) {
    User user = userRepository.findById(userId).orElse(null);

    if (user == null) {
      return new ArrayList<Role>();
    }

    return user.getRoleSet();
  }

  @Transactional
  @Override
  public List<String> findRoleNamesForUser(String userId) {
    List<Role> roles = findRolesForUser(userId);

    List<String> roleNames = new ArrayList<>();
    roles.forEach(role -> roleNames.add(role.getName()));

    return roleNames;
  }

  @Transactional
  @Override
  public List<String> findManagerNameByUID(String userId) {
    return userRepository.findManagerNamesByUID(userId);
  }

  @Transactional
  @Override
  public User authenticate(String username, String pwd) {
    return userRepository.findUserByNamePwd(username, pwd);
  }
}
