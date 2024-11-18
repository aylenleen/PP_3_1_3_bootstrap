package org.example.pp_3_1_3_bootstrap.controller;

import org.example.pp_3_1_3_bootstrap.model.Role;
import org.example.pp_3_1_3_bootstrap.model.User;
import org.example.pp_3_1_3_bootstrap.service.RoleService;
import org.example.pp_3_1_3_bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
@EnableAutoConfiguration
public class AdminController {

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


    @GetMapping
    public String showAdminPage(Model model, Principal principal) {
        User admin = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("admin", admin);
        List<String> roles = roleService.getRolesNames(admin);
        model.addAttribute("roleService", roleService);
        model.addAttribute("roles", roles);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getRoles());
        model.addAttribute("newUser", new User());
        return "admin-page";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@RequestParam("id") int id, @ModelAttribute User user) {
        user.setId((long)id);
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping(value ="/deleteUser")
    public String deleteUser(@RequestParam("id") int id, @ModelAttribute User user) {
        user.setId((long)id);
        userService.delete(user);
        return "redirect:/admin";
    }
}
