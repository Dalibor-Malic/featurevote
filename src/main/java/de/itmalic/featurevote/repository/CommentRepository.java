package de.itmalic.featurevote.repository;

import de.itmalic.featurevote.entity.db.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

  //  List<User> findByName(String name);

    ArrayList<Comment> findAllByElementIdOrderByCreatedDateAsc(Long elementId);

}