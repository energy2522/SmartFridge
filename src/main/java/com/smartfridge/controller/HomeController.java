package com.smartfridge.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Slf4j
@Controller
public class HomeController {

    @GetMapping({"/login", "/"})
    public String login() {
        return "login";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/main")
    public String getMain(Principal principal) {
        String result = "/error/access-denied";
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        log.info("role = {}", role);

        if (role.contains("ROLE_user")) {
            result = "redirect:/user/main";
        }

        if (role.contains("ROLE_admin")) {
            result = "redirect:/admin/main";
        }
        log.info("result = {}", result);

        return result;
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }
}
