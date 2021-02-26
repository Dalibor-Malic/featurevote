package de.itmalic.featurevote.entity;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Resource
public class NewUser {

    private String firstname;
    private String lastname;
    private String company;
    private int votingFactor;
    private String email;
    private String password;
    private boolean admin;
    private String note;
    private long dashboardId;

    //public void setPassword(String rawPassword) {
//        password = new BCryptPasswordEncoder().encode(rawPassword);
//    }
}
