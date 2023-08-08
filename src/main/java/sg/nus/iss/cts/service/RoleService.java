package sg.nus.iss.cts.service;

import java.util.List;

import sg.nus.iss.cts.model.Role;

public interface RoleService {
	List<Role> findAllRoles(); // in RoleServiceImpl.java | (2) (6)

	Role findRole(String roleId);

	Role createRole(Role role);

	Role changeRole(Role role);

	void removeRole(Role role);

	List<String> findAllRolesNames();

	List<Role> findRoleByName(String name);
}
