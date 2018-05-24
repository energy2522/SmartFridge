package com.smartfridge.controller;

import com.smartfridge.entity.Camera;
import com.smartfridge.entity.Client;
import com.smartfridge.entity.User;
import com.smartfridge.entity.UserFridge;
import com.smartfridge.repository.CameraRepository;
import com.smartfridge.repository.ClientRepository;
import com.smartfridge.repository.UserFridgeRepository;
import com.smartfridge.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class FridgeController {
    private static final String FALSE = "false";
    private static final String SPLIT = ":";

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFridgeRepository userFridgeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CameraRepository cameraRepository;

    @GetMapping("/rest/cameras/{id}")
    public @ResponseBody List<Camera> getCameras(@PathVariable("id") String id) {
        int fridgeId;
        if (StringUtils.isNumeric(id)) {
            fridgeId = Integer.parseInt(id);
        } else {
            return null;
        }

        UserFridge userFridge = userFridgeRepository.findOne(fridgeId);

        List<Camera> cameras = userFridge.getCameras();

        getSimpleCameras(cameras);
        return cameras;
    }

    @GetMapping("/rest/check/{id}")
    public @ResponseBody String isExist(@PathVariable("id") String id) {
        String[] ids = id.split(SPLIT);

        if (!StringUtils.isNumeric(ids[0]) || !StringUtils.isNumeric(ids[1])) {
            return FALSE;
        }

        User user = new User();
        user.setId(Integer.parseInt(ids[0]));
        Client client = clientRepository.findByIdAndUser(Integer.parseInt(ids[1]), user);

        return client == null ? FALSE : client.getName();
    }

    @GetMapping("/rest/get/{id}")
    public String minusProduct(@PathVariable("id") String id) {
        int cameraId = Integer.parseInt(id);

        Camera camera = cameraRepository.findOne(cameraId);

        camera.setAmount(camera.getAmount() - 1);

        cameraRepository.save(camera);

        return camera.getProduct().getName();
    }

    @GetMapping("/rest/user/{login}/{password}")
    public String getUser(@PathVariable("login") String login, @PathVariable("password") String password) {
        String result;

        PasswordEncoder passwordEncoder = getPasswordEncoder();
        User user = userRepository.findByUsername(login);



        if (user != null && passwordEncoder.isPasswordValid(user.getPassword(), password, null)) {
            result = "Success";
        } else {
            result = "Error";
        }

        log.info(result);

        return result;
    }

    public PasswordEncoder getPasswordEncoder() {
        final org.springframework.security.crypto.password.PasswordEncoder delegate = (org.springframework.security.crypto.password.PasswordEncoder) this.passwordEncoder;

        return new PasswordEncoder() {
            public String encodePassword(String rawPass, Object salt) {
                this.checkSalt(salt);
                return delegate.encode(rawPass);
            }

            public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                this.checkSalt(salt);
                return delegate.matches(rawPass, encPass);
            }

            private void checkSalt(Object salt) {
                Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
            }
        };
    }

    private void getSimpleCameras(List<Camera> cameras) {
        Integer id = cameras.get(0).getUserFridge().getUser().getId();
        for (int i = 0; i < cameras.size(); i++) {
            cameras.get(i).setUserId(String.valueOf(id));
            cameras.get(i).setUserFridge(null);
        }
    }
}
