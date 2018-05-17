package com.smartfridge.controller;

import com.smartfridge.conf.security.UserDetailsImpl;
import com.smartfridge.entity.*;
import com.smartfridge.repository.CameraRepository;
import com.smartfridge.repository.FridgeRepository;
import com.smartfridge.repository.ProductRepository;
import com.smartfridge.repository.UserFridgeRepository;
import com.smartfridge.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserFridgeRepository userFridgeRepository;

    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String goUserMain() {
        return "user/main";
    }

    @RequestMapping(value = "/fridges", method = RequestMethod.GET)
    public ModelAndView goToFridges() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("user ={} go to fridges", login);

        ModelAndView modelAndView = new ModelAndView("user/fridges");
        List<UserFridge> list = userFridgeRepository.findByUserUsername(login);

        modelAndView.addObject("fridges", list);

        return modelAndView;
    }

    @RequestMapping(value = "/add/fridge", method = RequestMethod.GET)
    public ModelAndView getToAddFridge() {
        log.info("get all fridges for user");
        ModelAndView modelAndView = new ModelAndView("/user/add_fridge");
        modelAndView.addObject("fridges", fridgeRepository.findAll());

        return modelAndView;
    }


    /**
     * The method for getting fridge
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/fridge", method = RequestMethod.POST)
    public ModelAndView showFridge(@RequestParam("id") int id) {
        UserFridge userFridge = userFridgeRepository.findOne(id);

        ModelAndView modelAndView = new ModelAndView("/user/fridge");
        modelAndView.addObject("fridge", userFridge);

        return modelAndView;
    }

    @RequestMapping(value = "/add/fridge", method = RequestMethod.POST)
    public ModelAndView addFridge(@RequestParam("id") int id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user  ={} want add fridge with id = {}", userDetails.getUsername(), id);

        userService.setFridgeAndUser(id, userDetails);

        ModelAndView modelAndView = new ModelAndView("/user/add_fridge");
        modelAndView.addObject("fridges", fridgeRepository.findAll());

        return modelAndView;
    }

    @RequestMapping(value = "/delete/fridge", method = RequestMethod.POST)
    public String deleteFridge(@RequestParam("id") int id) {
        log.info("delete userFridge with id = {}", id);

        UserFridge userFridge = userFridgeRepository.findOne(id);

        cameraRepository.delete(userFridge.getCameras());

        userFridgeRepository.delete(userFridge);

        return "redirect:/user/fridges";
    }

    @RequestMapping(value = "/set/camera", method = RequestMethod.POST)
    public ModelAndView setInformationForCamera(@RequestParam("id") int id) {
        Camera camera = cameraRepository.findOne(id);

        List<Product> products = (List<Product>) productRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("/user/camera");
        modelAndView.addObject("camera", camera);
        modelAndView.addObject("types", userService.getListWithType());
        modelAndView.addObject("products", products);

        return modelAndView;
    }

    @RequestMapping(value = "/update/camera", method = RequestMethod.POST)
    public String updateCamera(@RequestBody Camera camera) {
        log.info(camera.toString());
        return "";
    }

}
