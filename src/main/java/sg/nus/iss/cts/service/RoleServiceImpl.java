package sg.nus.iss.cts.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.cts.model.Role;
import sg.nus.iss.cts.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleRepository roleRepository;

	@Transactional
	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll(); // (3) (5)
	}

	@Transactional
	@Override
	public Role findRole(String roleId) {
		return roleRepository.findById(roleId).orElse(null);
	}

	@Transactional
	@Override
	public Role createRole(Role role) {
		return roleRepository.saveAndFlush(role);
	}

	@Transactional
	@Override
	public Role changeRole(Role role) {
		return roleRepository.saveAndFlush(role);
	}

	@Transactional
	@Override
	public void removeRole(Role role) {
		roleRepository.delete(role);
	}

	@Transactional
	@Override
	public List<String> findAllRolesNames() {
		return roleRepository.findAllRolesNames();
	}

	@Transactional
	@Override
	public List<Role> findRoleByName(String name) {
		return roleRepository.findRoleByName(name);
	}

}
