package de.itmalic.featurevote.config;

import de.itmalic.featurevote.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final UserDetailsService myUserDetailsService;

    public SecSecurityConfig(UserRepository userRepository, UserDetailsService myUserDetailsService) {
        this.userRepository = userRepository;
        this.myUserDetailsService = myUserDetailsService;
    }


    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("authenticationManager2")
    public AuthenticationManager authenticationManagerBean2() throws Exception {
        return authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(myUserDetailsService);
//        auth.inMemoryAuthentication()
//                .withUser("user1").password(passwordEncoder().encode("pass")).roles("USER")
//                .and()
//                .withUser("user2").password(passwordEncoder().encode("pass")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("pass")).roles("ADMIN");



    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/index*").permitAll()
                .antMatchers("/de/index*").permitAll()
                .antMatchers("/en/index*").permitAll()
                .antMatchers("/addadminuser*").permitAll()
                .antMatchers("/*/addadminuser*").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/de/").permitAll()
                .antMatchers("/en/").permitAll()
                .antMatchers("/logwithtoken").permitAll()
                .antMatchers("/*/logwithtoken").permitAll()
                .antMatchers("/logwithtoken2").permitAll()
                .antMatchers("/*/logwithtoken2").permitAll()
                .antMatchers("/dashboard*").hasAuthority("USER")
                .antMatchers("/de/dashboard*").hasAuthority("USER")
                .antMatchers("/en/dashboard*").hasAuthority("USER")
                .antMatchers("/elementdetails*").hasAuthority("USER")
                .antMatchers("/*/elementdetails*").hasAuthority("USER")
                .antMatchers("/updateelement*").hasAuthority("USER")
                .antMatchers("/*/updateelement*").hasAuthority("USER")
                .antMatchers("/addelement*").hasAuthority("USER")
                .antMatchers("/*/addelement*").hasAuthority("USER")
                .antMatchers("/voteforelement*").hasAuthority("USER")
                .antMatchers("/*/voteforelement*").hasAuthority("USER")
                .antMatchers("/revertvoteforelement*").hasAuthority("USER")
                .antMatchers("/*/revertvoteforelement*").hasAuthority("USER")
                .antMatchers("/users*").hasAuthority("ADMIN")
                .antMatchers("/*/users*").hasAuthority("ADMIN")
                .antMatchers("/addnewuserbyadmin*").hasAuthority("ADMIN")
                .antMatchers("/*/addnewuserbyadmin*").hasAuthority("ADMIN")
                .antMatchers("/updateuser*").hasAuthority("USER")
                .antMatchers("/*/updateuser*").hasAuthority("USER")
                .antMatchers("/createcomment*").hasAuthority("USER")
                .antMatchers("/*/createcomment*").hasAuthority("USER")

                .antMatchers("/*/landing").permitAll()
                .antMatchers("/landing").permitAll()

                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*").permitAll()
                .antMatchers("/signup*").permitAll()
                .antMatchers("/adduser*").permitAll()
                .antMatchers("/edit*").permitAll()
                .antMatchers("/update*").permitAll()
                .antMatchers("/delete*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/index")
                .loginProcessingUrl("/index")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/index?error=true")
                .permitAll()
                .and()
                .logout()
                .permitAll();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
