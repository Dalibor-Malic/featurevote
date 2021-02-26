package de.itmalic.featurevote.repository;

import de.itmalic.featurevote.entity.db.Element;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementRepository extends CrudRepository<Element, Long> {

  //  List<User> findByName(String name);

    List<Element> findAllByDashboardId(Long dashboardId);

}