package de.itmalic.featurevote.repository;

import de.itmalic.featurevote.entity.db.UserDashboardRelation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDashboardRelationRepository extends CrudRepository<UserDashboardRelation, Long> {

  //  List<User> findByName(String name);

    UserDashboardRelation findOneByUserId(Long userId);

    List<UserDashboardRelation> findAllByDashboardId(Long dashboardId);

}