package com.smartfridge.service;

import com.smartfridge.conf.security.UserDetailsImpl;
import com.smartfridge.constants.CameraType;
import com.smartfridge.entity.Camera;
import com.smartfridge.entity.Fridge;
import com.smartfridge.entity.User;
import com.smartfridge.entity.UserFridge;
import com.smartfridge.repository.CameraRepository;
import com.smartfridge.repository.FridgeRepository;
import com.smartfridge.repository.UserFridgeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private UserFridgeRepository userFridgeRepository;

    /**
     * The method unites user with fridge
     *
     * @param id - for fridge
     * @param userDetails - information about authorized user
     */
    @Transactional
    public void setFridgeAndUser(int id, UserDetailsImpl userDetails) {
        UserFridge userFridge = createUserFridge(id, userDetails);

        log.info("before save = {}", userFridge.getFridge());

        createEmptyCameras(userFridge);
    }

    /**
     * The method saved to database user joined with fridge
     *
     * @param id - fridge id
     * @param userDetails - information about authorized user
     *
     * @return object {@link UserFridge} created from params and saved in database
     */
    public UserFridge createUserFridge(int id, UserDetailsImpl userDetails) {
        Fridge fridge = fridgeRepository.findOne(id);

        User user = new User();
        user.setId(userDetails.getId());

        UserFridge userFridge = new UserFridge();
        userFridge.setFridge(fridge);
        userFridge.setUser(user);

        return userFridgeRepository.save(userFridge);
    }

    /**
     * The method creates empty cameras for {@param userFridge}
     *
     * @param userFridge - object as {@link UserFridge}
     */
    public void createEmptyCameras(UserFridge userFridge) {
        List<Camera> list = new ArrayList<>();

        log.info("fridge_id  = {} and max_length = {}", userFridge.getId(), userFridge.getFridge().getMaxCameras());

        for (int i = 0; i < userFridge.getFridge().getMaxCameras(); i++) {
            Camera camera = new Camera();
            camera.setUserFridge(userFridge);

            log.info("camera number {}, camera = {}", i, camera);

            camera = cameraRepository.save(camera);
            list.add(camera);
        }

        log.info("list length = {}", list.size());

    }

    /**
     * The method gets list with available camera's types
     *
     * @return {@link List<String>} with available {@link CameraType} for fridge
     */
    public List<String> getListWithType() {
        List<String> list = new ArrayList<>();

        list.add(CameraType.DRINK.toString());
        list.add(CameraType.PRODUCT.toString());
        list.add(CameraType.SIMPLE.toString());

        return list;
    }
}
