package de.itmalic.featurevote.service;

import de.itmalic.featurevote.entity.db.User;
import de.itmalic.featurevote.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new EmailNotFoundException(email);
        }
        return new MyUserPrincipal(user);
    }
}
