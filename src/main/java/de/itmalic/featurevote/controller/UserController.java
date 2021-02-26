package de.itmalic.featurevote.controller;

import de.itmalic.featurevote.entity.*;
import de.itmalic.featurevote.entity.db.Dashboard;
import de.itmalic.featurevote.entity.db.User;
import de.itmalic.featurevote.entity.db.UserDashboardRelation;
import de.itmalic.featurevote.repository.DashboardRepository;
import de.itmalic.featurevote.repository.UserDashboardRelationRepository;
import de.itmalic.featurevote.repository.UserRepository;
import de.itmalic.featurevote.service.MyUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final DashboardRepository dashboardRepository;
    private final UserDashboardRelationRepository userDashboardRelationRepository;

    public UserController(UserRepository userRepository,
                          DashboardRepository dashboardRepository,
                          UserDashboardRelationRepository userDashboardRelationRepository) {
        this.userRepository = userRepository;
        this.dashboardRepository = dashboardRepository;
        this.userDashboardRelationRepository = userDashboardRelationRepository;

    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("newAdminUser", new NewAdminUser());
        return "index";
    }

    @GetMapping("/")
    public String indexSlash(Model model) {

        return "redirect:/index";
    }

    @PostMapping("/addadminuser")
    public String addAdminUser(NewAdminUser newAdminUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newAdminUser", newAdminUser);
            return "index";
        }

        User user = new User();
        user.setAdmin(true);
        user.setFirstname(newAdminUser.getFirstname());
        user.setLastname(newAdminUser.getLastname());
        user.setCompany(newAdminUser.getCompany());
        user.setVotingFactor(1);
        user.setEmail(newAdminUser.getEmail().toLowerCase());
        user.setPassword(newAdminUser.getPassword());
        user.setNote("");
        user = userRepository.save(user);

        Dashboard dashboard = new Dashboard();
        dashboard.setName(newAdminUser.getDashboardName());
        dashboard = dashboardRepository.save(dashboard);

        UserDashboardRelation userDashboardRelation = new UserDashboardRelation();
        userDashboardRelation.setUserId(user.getId());
        userDashboardRelation.setDashboardId(dashboard.getId());
        userDashboardRelationRepository.save(userDashboardRelation);

        model.addAttribute("newAdminUser", new NewAdminUser());
        return "redirect:/index";
    }

    @GetMapping("/addnewuser")
    public String addnewuserGet(Model model) {
        model.addAttribute("newUser", new NewUser());

        return "addnewuser";
    }

    @PostMapping("/addnewuser")
    public String addNewUserPost(NewUser newUser, BindingResult result, Model model) {
        User user = new User();
        user.setAdmin(false);
        user.setFirstname(newUser.getFirstname());
        user.setLastname(newUser.getLastname());
        user.setCompany(newUser.getCompany());
        user.setVotingFactor(newUser.getVotingFactor());
        user.setEmail(newUser.getEmail().toLowerCase());
        user.setPassword(newUser.getPassword());
        user.setNote("");
        user = userRepository.save(user);

        userRepository.save(user);
        return "redirect:/index";
    }

    @GetMapping("/users")
    public String users(Authentication authentication, Model model) {
        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        UserDashboardRelation userDashboardRelation = userDashboardRelationRepository.findOneByUserId(userId);
        ArrayList<UserDashboardRelation> userDashboardRelations = (ArrayList<UserDashboardRelation>) userDashboardRelationRepository.findAllByDashboardId(userDashboardRelation.getDashboardId());



        List<User> users = new ArrayList<>();
        for (UserDashboardRelation relation : userDashboardRelations ) {
            users.add(userRepository.findById(relation.getUserId()).orElse(null));
        }
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/updateuser/{id}")
    public String updateuser(@PathVariable("id") Long id, Authentication authentication, Model model) {
        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        User userDb = userRepository.findById(id).orElse(null);

        model.addAttribute("user", userDb);

        return "useretails";
    }

    @PostMapping("/updateuser/{id}")
    public String updateuserPost(@PathVariable("id") Long id, User user, BindingResult result, Model model) {


        User elementDB = userRepository.findById(id).orElse(null);
        elementDB.setAdmin(user.isAdmin());
        elementDB.setFirstname(user.getFirstname());
        elementDB.setLastname(user.getLastname());
        elementDB.setCompany(user.getCompany());
        elementDB.setVotingFactor(user.getVotingFactor());
        elementDB.setEmail(user.getEmail().toLowerCase());
        elementDB.setNote(user.getNote());
        userRepository.save(elementDB);

        return "redirect:/users";
    }


    @GetMapping("/addnewuserbyadmin")
    public String addnewusebyadmin(Authentication authentication, Model model) {

        User user = new User();
        user.setVotingFactor(1);
        model.addAttribute("user", user);

        return "addnewuser";
    }

    @PostMapping("/addnewuserbyadmin")
    public String addnewuserbyadminPost(User user, Authentication authentication, Model model) {
        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        UserDashboardRelation userDashboardRelationAdminUser = userDashboardRelationRepository.findOneByUserId(userId);
        //Dashboard userDashboardAdminUser = dashboardRepository.findById(userDashboardRelation.getDashboardId()).orElse(null);


        user.setId(null);
        user = userRepository.save(user);

        UserDashboardRelation userDashboardRelation = new UserDashboardRelation();
        userDashboardRelation.setUserId(user.getId());
        userDashboardRelation.setDashboardId(userDashboardRelationAdminUser.getDashboardId());
        userDashboardRelationRepository.save(userDashboardRelation);

        return "redirect:/users";
    }


}