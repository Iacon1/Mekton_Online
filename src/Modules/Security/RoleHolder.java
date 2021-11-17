// By Iacon1
// Created 11/17/2021
// An entity that has roles

package Modules.Security;

import java.util.List;

public interface RoleHolder
{
	public void addRole(Role role);
	public boolean hasRole(Role role);
	public List<Role> getRoles();
}
