package de.itmalic.featurevote.controller;

import de.itmalic.featurevote.entity.*;
import de.itmalic.featurevote.entity.db.*;
import de.itmalic.featurevote.repository.*;
import de.itmalic.featurevote.service.MyUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class DashboardController {

    private final UserRepository userRepository;
    private final DashboardRepository dashboardRepository;
    private final UserDashboardRelationRepository userDashboardRelationRepository;
    private final ElementRepository elementRepository;
    private final UserElementRelationRepository userElementRelationRepository;

    public DashboardController(UserRepository userRepository,
                               DashboardRepository dashboardRepository,
                               UserDashboardRelationRepository userDashboardRelationRepository,
                               ElementRepository elementRepository,
                               UserElementRelationRepository userElementRelationRepository) {
        this.userRepository = userRepository;
        this.dashboardRepository = dashboardRepository;
        this.userDashboardRelationRepository = userDashboardRelationRepository;
        this.elementRepository = elementRepository;
        this.userElementRelationRepository = userElementRelationRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model, HttpSession session) {
        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        User user = userRepository.findById(userId).orElse(null);

        UserDashboardRelation userDashboardRelation = userDashboardRelationRepository.findOneByUserId(userId);
        Dashboard userDashboard = dashboardRepository.findById(userDashboardRelation.getDashboardId()).orElse(null);
        ArrayList<Element> elementsOfDashboard = (ArrayList<Element>) elementRepository.findAllByDashboardId(userDashboard.getId());

        List<ElementForList> elementsForList = new ArrayList<>();
        for (Element e : elementsOfDashboard ) {
            ElementForList elementForList = new ElementForList();
            elementForList.setId(e.getId());
            elementForList.setTitle(e.getTitle());
            if (e.getDescription().length() > 50) {
                elementForList.setDescription(e.getDescription().substring(0, 45) + " ...");
            } else {
                elementForList.setDescription(e.getDescription());
            }

            elementForList.setCreatedByUserId(e.getCreatedByUserId());
            elementForList.setDashboardId(e.getDashboardId());
            elementForList.setOnline(e.isOnline());

            elementForList.setUserOwner(e.getCreatedByUserId().equals(userId));

            UserElementRelation userElementRelation = userElementRelationRepository.findOneByElementIdAndUserId(e.getId(), userId);
            elementForList.setVoted(userElementRelation != null);

            ArrayList<UserElementRelation> votedUsers = (ArrayList) userElementRelationRepository.findAllByElementId(e.getId());
            long votes = 0;
            for (UserElementRelation uer : votedUsers) {
                votes = votes + userRepository.findById(uer.getUserId()).get().getVotingFactor();
            }
            elementForList.setVotes(votes);

            if (elementForList.isOnline() || user.isAdmin() ||  elementForList.isUserOwner()) {
                elementsForList.add(elementForList);
            }
        }

        model.addAttribute("newUser", new NewUser());
        model.addAttribute("elements", elementsForList);
        return "dashboard";
    }


}