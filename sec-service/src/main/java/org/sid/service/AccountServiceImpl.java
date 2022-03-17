package org.sid.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.sid.dao.AppRoleRepository;
import org.sid.dao.AppUserRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private BCryptPasswordEncoder bEncoder;
	
	@Override
	public AppUser saveUser(String username, String password, String rePassword) {
		AppUser user = appUserRepository.findByUsername(username);
		if(user != null)
			throw new RuntimeException("User already exists");
		if(!password.equals(rePassword)) {
			throw new RuntimeException("Please check your password with the re-password");
		}
		AppUser appUser = new AppUser();
		appUser.setUsername(username);
		String pass = bEncoder.encode(password);
		appUser.setPassword(pass);
		AppRole role = appRoleRepository.findByRoleName("USER");
		Collection<AppRole> roles = appUser.getRoles();
		roles.add(role);
		appUser.setRoles(roles);
		appUser.setActivated(true);
		appUserRepository.save(appUser);
		addRoleToUser(username, "USER");
		return appUser;
		
	}

	@Override
	public AppRole saveRole(String roleName) {
		AppRole role = new AppRole();
		role.setRoleName(roleName);
		appRoleRepository.save(role);
		return role;
	}

	@Override
	public AppUser loadUserByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser appUser = appUserRepository.findByUsername(username);
		AppRole role = appRoleRepository.findByRoleName(roleName);
		Collection<AppRole> roles = appUser.getRoles();
		roles.add(role);
		appUser.setRoles(roles);
		Collection<AppUser> users = role.getUsers();
		users.add(appUser);
		role.setUsers(users);
		appRoleRepository.save(role);
		appUserRepository.save(appUser);
		
	}


}
