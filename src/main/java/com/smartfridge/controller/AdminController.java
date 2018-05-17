package com.smartfridge.controller;

import com.smartfridge.dto.UserDTO;
import com.smartfridge.entity.Camera;
import com.smartfridge.entity.Fridge;
import com.smartfridge.entity.User;
import com.smartfridge.repository.FridgeRepository;
import com.smartfridge.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/add/fridge", method = RequestMethod.GET)
    public String goToAddFridge() {
        return "admin/add_fridge";
    }

    @RequestMapping(value = "/fridges", method = RequestMethod.GET)
    public ModelAndView getFridges() {
        log.info("get all fridges");
        ModelAndView modelAndView = new ModelAndView("/admin/fridges");
        modelAndView.addObject("fridges", fridgeRepository.findAll());

        return modelAndView;
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsers() {
        log.info("get all users");
        ModelAndView modelAndView = new ModelAndView("/admin/users");
        modelAndView.addObject("users", userRepository.findAll());

        return modelAndView;
    }

    @RequestMapping(value = "/add/user", method = RequestMethod.GET)
    public String goToAddUser() {
        return "admin/add_user";
    }

    @RequestMapping(value = "/add/fridge", method = RequestMethod.POST)
    public String addFridge(Fridge fridge, BindingResult bindingResult, Model model) {
        log.info("new fridge add {}", fridge);
        fridgeRepository.save(fridge);

        return "redirect:/admin/fridges";
    }

    @RequestMapping(value = "/add/user", method = RequestMethod.POST)
    public String addUser(UserDTO userDTO, BindingResult bindingResult, Model model) {
        log.info("new user");
        User user = userDTO.getUser(1);

        userRepository.save(user);

        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String goAdminMain() {
        return "admin/main";
    }
}
