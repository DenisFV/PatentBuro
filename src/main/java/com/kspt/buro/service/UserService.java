package com.kspt.buro.service;

import com.kspt.buro.domain.Request;
import com.kspt.buro.domain.Role;
import com.kspt.buro.domain.User;
import com.kspt.buro.repos.RequestRepo;
import com.kspt.buro.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("Пользователя не существует");

        return user;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        return true;
    }

    public String updateUsername(User user, String username, String url) {
        if (username.equals("admin")) {
            return "redirect:/user/" + url;
        }

        if (user.getUsername().equals(username)) {
            return "ok";
        }

        for (User u : findAll())
            if (u.getUsername().equals(username)) {
            return "redirect:/user/" + url;
            }

        user.setUsername(username);

        return "ok";
    }

    public void updateRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet())
            if (roles.contains(key)) user.getRoles().add(Role.valueOf(key));
    }

    public void deleteUser(String username) {
        Iterable<Request> requests = requestRepo.findAll();
        for (Request request : requests) {
            if (request.getAuthorName().equals(userRepo.findByUsername(username).getUsername()))
                requestRepo.delete(request);
        }

        userRepo.delete(userRepo.findByUsername(username));
    }

    public void updatePassword(User user, String password) {
        if (!password.trim().isEmpty())
//            user.setPassword(passwordEncoder.encode(password.trim()));
            user.setPassword(password.trim());
    }
}
