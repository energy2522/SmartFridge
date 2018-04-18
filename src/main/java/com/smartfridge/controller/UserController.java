package com.smartfridge.controller;

import com.smartfridge.conf.security.UserDetailsImpl;
import com.smartfridge.entity.Fridge;
import com.smartfridge.entity.User;
import com.smartfridge.entity.UserFridge;
import com.smartfridge.repository.FridgeRepository;
import com.smartfridge.repository.UserFridgeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserFridgeRepository userFridgeRepository;

    @Autowired
    private FridgeRepository fridgeRepository;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String goUserMain() {
        return "user/main";
    }

    @RequestMapping(value = "/fridges", method = RequestMethod.GET)
    public ModelAndView goToFridges() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("user ={} go to fridges", login);

        ModelAndView modelAndView = new ModelAndView("user/fridges");

        modelAndView.addObject("fridges", userFridgeRepository.findByUserUsername(login));

        return modelAndView;
    }

    @RequestMapping(value = "/add/fridge", method = RequestMethod.GET)
    public ModelAndView getToAddFridge() {
        log.info("get all fridges for user");
        ModelAndView modelAndView = new ModelAndView("/user/add_fridge");
        modelAndView.addObject("fridges", fridgeRepository.findAll());

        return modelAndView;
    }

    @RequestMapping(value = "/add/fridge", method = RequestMethod.POST)
    public ModelAndView addFridge(@RequestParam("id") int id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user  ={} want add fridge with id = {}", userDetails.getUsername(), id);

        Fridge fridge = new Fridge();
        User user = new User();
        user.setId(userDetails.getId());
        fridge.setId(id);

        UserFridge userFridge = new UserFridge();
        userFridge.setFridge(fridge);
        userFridge.setUser(user);

        userFridgeRepository.save(userFridge);

        ModelAndView modelAndView = new ModelAndView("/user/add_fridge");
        modelAndView.addObject("fridges", fridgeRepository.findAll());

        return modelAndView;
    }
}
