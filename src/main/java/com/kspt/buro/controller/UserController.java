package com.kspt.buro.controller;

import com.kspt.buro.domain.Request;
import com.kspt.buro.domain.Role;
import com.kspt.buro.domain.User;
import com.kspt.buro.repos.RequestRepo;
import com.kspt.buro.repos.UserRepo;
import com.kspt.buro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Password;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RequestRepo requestRepo;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdits";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(params = "save")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {

        String newUsername = userService.updateUsername(user, username.trim(), user.getId().toString());
        if (!newUsername.equals("ok")) return newUsername;

        userService.updateRoles(user, form);

        userRepo.save(user);

        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(params = "delete")
    public String delete(@RequestParam String username) {

        userService.deleteUser(username);

        return "redirect:/user";
    }


    @GetMapping("profile")
    public String getProfile(Model model) {

        model.addAttribute("user",
                userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @RequestParam(defaultValue = "") String username,
            @RequestParam String password,
            @RequestParam("userId") User user) {

        if (!user.getUsername().equals("admin")) {
            String newUsername = userService.updateUsername(user, username.trim(), "profile");
            if (!newUsername.equals("ok")) return newUsername;
        }

        userService.updatePassword(user, password);

        userRepo.save(user);

        return "redirect:/user/profile";
    }
}

