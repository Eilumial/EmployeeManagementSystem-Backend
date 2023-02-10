package com.avensys.htdx1.EMSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.avensys.htdx1.EMSystem.dto.PWChangeDTO;
import com.avensys.htdx1.EMSystem.entity.Employee;
import com.avensys.htdx1.EMSystem.entity.Role;
import com.avensys.htdx1.EMSystem.entity.UserEntity;
import com.avensys.htdx1.EMSystem.repo.EmployeeRepo;
import com.avensys.htdx1.EMSystem.repo.RoleRepo;
import com.avensys.htdx1.EMSystem.repo.UserRepo;

@Service
@Component
public class UserService {
	@Autowired
	UserRepo ur;

	@Autowired
	EmployeeRepo er;

	@Autowired
	RoleRepo rr;

	@Autowired
	private PasswordEncoder pe;

	public UserEntity add(UserEntity u) {
		ur.save(u);
		return u;
	}

	public List<UserEntity> getAllUser() {
		List<UserEntity> userList = (List<UserEntity>) ur.findAll();
		return userList;
	}

	public UserEntity getById(Long id) {
		return ur.findById(id.intValue());
	}

	public UserEntity updateUserData(Long id, UserEntity u) {

		UserEntity updatedUser = ur.findById(id.intValue());
		List<Long> idList = ur.findAllIdByUsername(u.getUsername());
//		if(ur.existsByUsername(u.getUsername())&&updatedUser.getId()!=id) {		
		if (idList.size() > 0) {

			for (Long i : idList) {
				if (i != id) {
					UserEntity user = new UserEntity();
					user.setErrorMessage("Username already exists in system");
					return user;
				}
			}
		}

		updatedUser.setUsername(u.getUsername());
		updatedUser.setPassword(u.getPassword());
		updatedUser.setUser_type(u.getUser_type());

		// u.setSalt("testing salt");

		if (u.getSalt() != null) {
			updatedUser.setSalt(u.getSalt());
		}

		if (u.getEmployee() == null) {
			Employee e = er.findByUserEntityId(id);
			if (e != null) {
				updatedUser.setEmployee(e);
			} else {
				updatedUser.setEmployee(null);
			}
		} else {
			updatedUser.setEmployee(u.getEmployee());
		}

		return ur.save(updatedUser);

	}

	public String updatePassword(PWChangeDTO u) {
		if (ur.existsByUsername(u.getUsername())) {
			UserEntity user = ur.findByUsername(u.getUsername()).get();
			System.out.println("DTO: " + u.toString());
//			System.out.println("New pw:" + pe.encode(u.getPassword()));
//			System.out.println("Old pw:" + pe.encode(u.getOldpassword()));
//			System.out.println("Records:" + user.getUsername());
//			System.out.println("Records:" + user.getPassword());
//			System.out.println("Old pw:" + pe.encode("12321abc!"));
//			System.out.println("Old pw:" + pe.encode("12321abc!"));
//			if (user.getPassword().equals(pe.encode(u.getOldpassword()))) {
			if (pe.matches(u.getOldpassword(), user.getPassword())) {
				// user.setPassword(pe.encode(regUser.getPassword()));
				user.setPassword(pe.encode(u.getPassword()));
				ur.save(user);
				return "Password updated successfully.";
			} else {
				return "Existing password is incorrect";
			}

		} else {
			return "User does not exist";
		}

	}

	public boolean deleteUser(Long id) {
		if (ur.existsById(id)) {
			ur.deleteById(id);
			return true;
		} else {
			return false;
		}

	}

	public String authAdmin(Long id) {
		try {
			if (ur.existsById(id)) {
				UserEntity user = ur.findById(id).get();
				Role admin_role = rr.findByName("ADMIN").get();
				List<Role> roles = user.getRoles();
//				System.out.println(user.getEmployee().getId());
				
				if (!roles.contains(admin_role)) {
					roles.add(admin_role);
					user.setRoles(roles);
					UserEntity newUser = ur.save(user);
					System.out.println("New User" + newUser.getEmployee().getId()); 
					return "Administrator rights added to (*user*)";
				} else {
					return "Employee already has Administrator rights";
				}
			} else {
				return "User does not exist";
			}
		} catch (Exception e) {
			return "Exception caught: "+e.getMessage();
		}
	}
}
