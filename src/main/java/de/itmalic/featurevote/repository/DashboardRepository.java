package de.itmalic.featurevote.repository;

import de.itmalic.featurevote.entity.db.Dashboard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends CrudRepository<Dashboard, Long> {

  //  List<User> findByName(String name);



}