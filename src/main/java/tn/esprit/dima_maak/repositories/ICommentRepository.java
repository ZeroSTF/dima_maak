package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.Comment;

import java.util.List;

@Repository
public interface ICommentRepository extends CrudRepository<Comment,Long> {
    List<Comment> findAllByPostId(Long postId);
}
