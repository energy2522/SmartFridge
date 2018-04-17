package com.smartfridge.dto;

import com.smartfridge.entity.Role;
import com.smartfridge.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserDTO {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);

    private String username;

    private String password;

    public User getUser(int roleId) {
        Role role = new Role();
        role.setId(roleId);
        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        user.setRole(role);

        return user;
    }
}
