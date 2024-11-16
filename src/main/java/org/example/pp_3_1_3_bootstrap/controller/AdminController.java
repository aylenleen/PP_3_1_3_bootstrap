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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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

//    @GetMapping(value ="/all-users")
//    public String showAllUsers(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        return "all-users";
//    }

//    @GetMapping(value ="/addUser")
//    public String addUser(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleService.getRoles());
//        return "redirect:/admin";
//    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping(value ="/updateInfo")
    public String updateUser(@RequestParam("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("userId", user.getId());
        model.addAttribute("roles1", roleService.getRoles());
        return "admin-page";
    }

    @GetMapping(value ="/deleteUser")
    public String deleteUser(@RequestParam("userId") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getRolesNames(user));
        userService.delete(user);
        return "redirect:/admin/all-users";
    }
}
