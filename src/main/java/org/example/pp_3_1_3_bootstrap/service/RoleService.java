package org.example.pp_3_1_3_bootstrap.service;



import org.example.pp_3_1_3_bootstrap.model.Role;
import org.example.pp_3_1_3_bootstrap.model.User;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    List<String> getRolesNames(User user);
    public void addNewRole(Role role);
    public Role findByName(String roleName);
}
