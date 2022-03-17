package org.sid.service;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;

public interface AccountService {

	public AppUser saveUser(String username, String password, String rePassword);
	public AppRole saveRole(String roleName);
	public AppUser loadUserByUsername(String username);
	public void addRoleToUser(String username, String roleName);
	
}
