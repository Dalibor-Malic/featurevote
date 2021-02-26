package de.itmalic.featurevote.repository;

import de.itmalic.featurevote.entity.db.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  //  List<User> findByName(String name);

    User findByEmail(String email);

}