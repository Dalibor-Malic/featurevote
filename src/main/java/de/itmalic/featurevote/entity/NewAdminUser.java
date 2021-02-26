package de.itmalic.featurevote.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Resource
public class NewAdminUser {

    private String firstname;
    private String lastname;
    private String company;
    private String email;
    private String password;
    private String note;
    private String dashboardName;

//    public void setPassword(String rawPassword) {
//        password = new BCryptPasswordEncoder().encode(rawPassword);
//    }
}
