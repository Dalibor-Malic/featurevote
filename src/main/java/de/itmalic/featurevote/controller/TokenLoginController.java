package de.itmalic.featurevote.controller;

import de.itmalic.featurevote.entity.db.User;
import de.itmalic.featurevote.repository.DashboardRepository;
import de.itmalic.featurevote.repository.UserDashboardRelationRepository;
import de.itmalic.featurevote.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TokenLoginController {

    @Resource(name="authenticationManager2")
    private AuthenticationManager authManager;

    private final UserRepository userRepository;
    private final DashboardRepository dashboardRepository;
    private final UserDashboardRelationRepository userDashboardRelationRepository;

    public TokenLoginController(UserRepository userRepository,
                                DashboardRepository dashboardRepository,
                                UserDashboardRelationRepository userDashboardRelationRepository) {
        this.userRepository = userRepository;
        this.dashboardRepository = dashboardRepository;
        this.userDashboardRelationRepository = userDashboardRelationRepository;
    }

    @GetMapping("/logwithtoken")
    public String index(Model model, HttpServletRequest request, HttpSession session, ServletResponse servletResponse) { //HttpSession session

//        HttpServletRequest request = (HttpServletRequest)servletRequest;
        User user = userRepository.findById(0L).orElse(null);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), "pass");
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication auth = authManager.authenticate(token);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        //HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);

        return "redirect:/dashboard";
    }

}