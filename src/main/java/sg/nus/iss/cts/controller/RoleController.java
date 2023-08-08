package sg.nus.iss.cts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.nus.iss.cts.exception.RoleNotFound;
import sg.nus.iss.cts.model.Role;
import sg.nus.iss.cts.model.UserSession;
import sg.nus.iss.cts.service.RoleService;
import sg.nus.iss.cts.validator.RoleValidator;

@Controller
@RequestMapping(value = "/admin/role")
@SessionAttributes(value = { "usession" }, types = { UserSession.class })
public class RoleController {
	@Autowired
	private RoleService rService;
	@Autowired
	private RoleValidator rValidator;

	@InitBinder("role")
	private void initRoleBinder(WebDataBinder binder) {
		binder.addValidators(rValidator);
	}

	/**
	 * ROLE CRUD OPERATIONS
	 * 
	 * @return
	 */
	@GetMapping("/list")
	public String roleListPage(Model model) { // model ->on html page
		List<Role> roleList = rService.findAllRoles(); // in RoleSerivce.java | (1) (7)
		model.addAttribute("roleList", roleList);

		return "role-list";
	}

	@GetMapping("/create")
	public String newRolePage(Model model) {
		model.addAttribute("role", new Role());

		return "role-new";
	}

	@PostMapping("/create")
	public String createNewRole(@ModelAttribute @Valid Role role, BindingResult result) {
		if (result.hasErrors()) {
			return "role-new";
		}

		String message = "New role " + role.getRoleId() + " was successfully created.";
		System.out.println(message);
		rService.createRole(role);

		return "redirect:/admin/role/list";
	}

	@GetMapping("/edit/{id}")
	public String editRolePage(@PathVariable String id, Model model) {
		Role role = rService.findRole(id);
		model.addAttribute("role", role);

		return "role-edit";
	}

	@PostMapping("/edit/{id}")
	public String editRole(@ModelAttribute @Valid Role role, BindingResult result, @PathVariable String id)
			throws RoleNotFound {
		if (result.hasErrors()) {
			return "role-edit";
		}

		String message = "Role was successfully updated.";
		System.out.println(message);
		rService.changeRole(role);

		return "redirect:/admin/role/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteRole(@PathVariable String id) throws RoleNotFound {
		Role role = rService.findRole(id);
		rService.removeRole(role);

		String message = "The role " + role.getRoleId() + " was successfully deleted.";
		System.out.println(message);

		return "redirect:/admin/role/list";
	}

}