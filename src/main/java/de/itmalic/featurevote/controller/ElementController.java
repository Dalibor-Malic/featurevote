package de.itmalic.featurevote.controller;

import de.itmalic.featurevote.entity.*;
import de.itmalic.featurevote.entity.db.*;
import de.itmalic.featurevote.repository.*;
import de.itmalic.featurevote.service.MyUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;


@Controller
public class ElementController {

    private final UserRepository userRepository;
    private final DashboardRepository dashboardRepository;
    private final UserDashboardRelationRepository userDashboardRelationRepository;
    private final ElementRepository elementRepository;
    private final UserElementRelationRepository userElementRelationRepository;
    private final CommentRepository commentRepository;

    public ElementController(UserRepository userRepository,
                             DashboardRepository dashboardRepository,
                             UserDashboardRelationRepository userDashboardRelationRepository,
                             ElementRepository elementRepository,
                             UserElementRelationRepository userElementRelationRepository,
                             CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.dashboardRepository = dashboardRepository;
        this.userDashboardRelationRepository = userDashboardRelationRepository;
        this.elementRepository = elementRepository;
        this.userElementRelationRepository = userElementRelationRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/elementdetails/{id}")
    public String elementdetails(@PathVariable("id") Long id, Authentication authentication, Model model) {
        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        UserDashboardRelation userDashboardRelation = userDashboardRelationRepository.findOneByUserId(userId);
        Dashboard userDashboard = dashboardRepository.findById(userDashboardRelation.getDashboardId()).orElse(null);
        Element element = elementRepository.findById(id).orElse(null);

        UserElementRelation userElementRelation = userElementRelationRepository.findOneByElementIdAndUserId(element.getId(), userId);

        ArrayList<Comment> comments = commentRepository.findAllByElementIdOrderByCreatedDateAsc(id);
        ArrayList<CommentUI> commentsUi = new ArrayList<>();
        int counter = 0;
        Long lastUser = null;
        for (Comment comment : comments) {
            if (!comment.getUserId().equals(lastUser)) {
                counter++;
                lastUser = comment.getUserId();
            }
            CommentUI commentUi = new CommentUI();
            commentUi.setId(comment.getId());
            commentUi.setText(comment.getText());
            commentUi.setUserId(comment.getUserId());
            commentUi.setElementId(comment.getElementId());
            User user = userRepository.findById(comment.getUserId()).orElse(new User());
            commentUi.setUserFirstname(user.getFirstname());
            commentUi.setUserLastname(user.getLastname());
            commentUi.setCreatedDate(comment.getCreatedDate());
            commentUi.setCounter(counter);
            commentsUi.add(commentUi);
        }

        model.addAttribute("element", element);
        model.addAttribute("owner", element.getCreatedByUserId().equals(userId));
        model.addAttribute("voted", userElementRelation != null);
        model.addAttribute("comments", commentsUi);
        model.addAttribute("newcomment", new Comment());

        return "elementdetails";
    }

    @PostMapping("/createcomment/{id}")
    public String createcomment(@PathVariable("id") Long id, Comment comment,  Authentication authentication, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        comment.setId(null);
        comment.setCreatedDate(new Date());
        comment.setElementId(id);
        comment.setUserId(userId);
        commentRepository.save(comment);

        redirectAttrs.addAttribute("id", id);
        return "redirect:/elementdetails/{id}";
    }
        @PostMapping("/updateelement/{id}")
    public String updateelement(@PathVariable("id") Long id, Element element,  Authentication authentication, BindingResult result, Model model) {

        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        Element elementDB = elementRepository.findById(id).orElse(null);
        elementDB.setTitle(element.getTitle());
        elementDB.setDescription(element.getDescription());
        elementDB.setCategory(element.getCategory());

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            elementDB.setOnline(element.isOnline());
        }
        elementRepository.save(elementDB);

        return "redirect:/dashboard";
    }

    @GetMapping("/addelement")
    public String addelement(Authentication authentication, Model model) {

        Element element = new Element();
        model.addAttribute("element", element);

        return "addelement";
    }

    @PostMapping("/addelement")
    public String addelementPost(Element element, Authentication authentication, Model model) {
        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        UserDashboardRelation userDashboardRelation = userDashboardRelationRepository.findOneByUserId(userId);
        Dashboard userDashboard = dashboardRepository.findById(userDashboardRelation.getDashboardId()).orElse(null);

        element.setId(null);
        element.setDashboardId(userDashboard.getId());
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            element.setOnline(false);
        }

        element.setCreatedByUserId(userId);
        elementRepository.save(element);

        return "redirect:/dashboard";
    }

    @GetMapping("/voteforelement/{id}")
    public String voteforelement(@PathVariable("id") Long id, Authentication authentication, Model model) {
        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        UserElementRelation userElementRelation = new UserElementRelation();
        userElementRelation.setUserId(userId);
        userElementRelation.setElementId(id);
        userElementRelationRepository.save(userElementRelation);

        return "redirect:/dashboard";
    }

    @GetMapping("/revertvoteforelement/{id}")
    public String revertvoteforelement(@PathVariable("id") Long id, Authentication authentication, Model model) {

        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        UserElementRelation userElementRelation = userElementRelationRepository.findOneByElementIdAndUserId(id, userId);
        userElementRelationRepository.delete(userElementRelation);


        return "redirect:/dashboard";
    }


}