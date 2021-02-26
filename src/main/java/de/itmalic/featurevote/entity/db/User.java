package de.itmalic.featurevote.entity.db;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String firstname;
    private String lastname;
    private String company;
    private int votingFactor;
    private String email;
    private String password;
    private boolean admin;
    private String note;

    public void setPassword(String rawPassword) {
        password = new BCryptPasswordEncoder().encode(rawPassword);
    }
}
