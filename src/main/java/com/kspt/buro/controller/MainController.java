package com.kspt.buro.controller;

import com.kspt.buro.domain.Checks;
import com.kspt.buro.domain.Request;
import com.kspt.buro.domain.User;
import com.kspt.buro.repos.RequestRepo;
import javafx.scene.control.CheckBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private RequestRepo requestRepo;

    @GetMapping("/")
    public String greeting(Model model) {

        String auth = SecurityContextHolder.getContext().getAuthentication().getName();

        if (auth.equals("anonymousUser")) model.addAttribute("user", "гость");
        else model.addAttribute("user", auth);

        return "greeting";
    }


    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {

        Iterable<Request> requests;

        if (user.getRole().equals("USER")) {
            requests = filter != null && !filter.isEmpty() ?
                    requestRepo.findByPtype(filter) : requestRepo.findByAuthor(user);
        } else requests = filter != null && !filter.isEmpty() ?
                requestRepo.findByPtype(filter) : requestRepo.findAll();

        model.addAttribute("requests", requests);
        model.addAttribute("checks", Checks.values());
        model.addAttribute("filter", filter);
        model.addAttribute("user", user);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Request request,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {

        request.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("request", request);
        } else if (file == null || file.getOriginalFilename().isEmpty())
            model.addAttribute("fileError", "Файл нужно выбрать");
        else {

            ControllerUtils.saveFile(uploadPath, request, file);

            model.addAttribute("request", null);

            requestRepo.save(request);
        }
        model.addAttribute("user", user);
        model.addAttribute("checks", Checks.values());
//        model.addAttribute("request", Checks.values());
        model.addAttribute("requests", requestRepo.findByAuthor(user));

        return "main";
    }

    @GetMapping(value = "/main", params = "delete")
    public String delete(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {

        model.addAttribute("requests", filter != null && !filter.isEmpty() ?
                requestRepo.findByPtype(filter) : requestRepo.findAll());

        model.addAttribute("filter", filter);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getName());

        return "main";
    }
}