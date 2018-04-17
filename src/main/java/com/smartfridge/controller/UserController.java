package com.smartfridge.controller;

import com.smartfridge.entity.User;
import com.smartfridge.entity.UserFridge;
import com.smartfridge.repository.UserFridgeRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController("/user")
public class UserController {

    @Autowired
    private UserFridgeRepository userFridgeRepository;

    @RequestMapping(value = "/user/main", method = RequestMethod.GET)
    public String goUserMain() {
        return "user/main";
    }

    @RequestMapping(value = "/user/fridges", method = RequestMethod.GET)
    public ModelAndView goToFridges() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("user ={} go to fridges", login);

        ModelAndView modelAndView = new ModelAndView("user/fridges");

        modelAndView.addObject("fridges", userFridgeRepository.findByUserUsername(login));

        return modelAndView;
    }
}
