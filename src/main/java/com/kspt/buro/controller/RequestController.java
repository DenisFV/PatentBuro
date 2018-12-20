package com.kspt.buro.controller;

import com.kspt.buro.domain.Checks;
import com.kspt.buro.domain.Request;
import com.kspt.buro.domain.Role;
import com.kspt.buro.domain.User;
import com.kspt.buro.repos.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RequestController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private RequestRepo requestRepo;

    @GetMapping("/user-requests/{user}")
    public String userRequests(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Request request) {

        model.addAttribute("user", user);
        model.addAttribute("requests", Collections.singleton(request));
        model.addAttribute("checks", Checks.values());
        model.addAttribute("request", request);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userRequests";
    }

    @PostMapping("/user-requests/{user}")
    public String updateRequest(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Request request,
            @RequestParam("text") String text,
            @RequestParam("ptype") String ptype,
            @RequestParam("file") MultipartFile file) throws IOException {

        assert request != null;
        if (request.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) request.setText(text);
            if (!StringUtils.isEmpty(ptype)) request.setPtype(ptype);
            ControllerUtils.saveFile(uploadPath, request, file);
            requestRepo.save(request);
        }

        return "redirect:/main";
    }


    @GetMapping("/check")
    public String checkRequestEdit(
            @AuthenticationPrincipal User user,
            Model model,
            @RequestParam(required = false) Request request) {

        model.addAttribute("user", user);
        model.addAttribute("request", request);
        model.addAttribute("checks", Checks.values());

        return "checkRequest";
    }

    @PostMapping("/check")
    public String checkRequestSave(
            @AuthenticationPrincipal User user,
            @RequestParam("requestId") Request request,
            @RequestParam String message,
            @RequestParam Map<String, String> form) {

        Set<String> checks = Arrays.stream(Checks.values())
                .map(Checks::name)
                .collect(Collectors.toSet());
        request.getChecks().clear();
        for (String key : form.keySet())
            if (checks.contains(key)) request.getChecks().add(Checks.valueOf(key));

        request.setMessage(message+"\n");

        requestRepo.save(request);

        return "redirect:/main";
    }
}
