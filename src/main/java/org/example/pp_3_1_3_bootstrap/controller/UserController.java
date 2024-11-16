package org.example.pp_3_1_3_bootstrap.controller;

import org.example.pp_3_1_3_bootstrap.model.User;
import org.example.pp_3_1_3_bootstrap.service.RoleService;
import org.example.pp_3_1_3_bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String showUserPage(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        List<String> roleNames = roleService.getRolesNames(user);
        boolean isAdmin = roleNames.contains("ROLE_ADMIN");
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("roles", roleNames);
        return "user-page";
    }


}
