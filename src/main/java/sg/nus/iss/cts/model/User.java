package sg.nus.iss.cts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * User class
 *
 * @version $Revision: 1.0
 * @author Suria
 * 
 */

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 6529685098267757680L;
	@Id
	@Column(name = "userid")
	private String userId;

	@NotBlank(message = "{error.user.name.empty}")
	@Column(name = "name")
	private String name;

	@NotBlank(message = "{error.user.password.empty}")
	@Column(name = "password")
	private String password;

	@Column(name = "employeeid")
	private String employeeId;

	@ManyToMany(targetEntity = Role.class, cascade = { CascadeType.ALL, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "userrole", joinColumns = {
			@JoinColumn(name = "userid", referencedColumnName = "userid") }, inverseJoinColumns = {
					@JoinColumn(name = "roleid", referencedColumnName = "roleid") })
	private List<Role> roleSet;

	public User() {
	}

	public User(String userId, String name, String password, String employeeId) {
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.employeeId = employeeId;
	}

	public User(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public List<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(List<Role> roleSet) {
		this.roleSet = roleSet;
	}

	public List<String> getRoleIds() {
		List<String> retList = new ArrayList<>();
		roleSet.forEach(role -> retList.add(role.getRoleId()));

		return retList;
	}

}