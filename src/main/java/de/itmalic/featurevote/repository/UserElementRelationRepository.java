package de.itmalic.featurevote.repository;

import de.itmalic.featurevote.entity.db.UserElementRelation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserElementRelationRepository extends CrudRepository<UserElementRelation, Long> {

  //  List<User> findByName(String name);

    List<UserElementRelation> findAllByElementId(Long elementId);

    UserElementRelation findOneByElementIdAndUserId(Long elementId, Long userId);
}