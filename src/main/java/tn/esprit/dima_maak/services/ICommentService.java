package tn.esprit.dima_maak.services;

import org.springframework.http.ResponseEntity;
import tn.esprit.dima_maak.entities.Comment;
import tn.esprit.dima_maak.entities.Complaint;
import tn.esprit.dima_maak.entities.Post;

import java.util.List;

public interface ICommentService {
    public ResponseEntity<?> addComment(Comment comment);
    public ResponseEntity<?> updateComment(Comment comment) ;
    Comment findCommentById(Long id);
    void deleteComment(Long id);
    List<Comment> getAll();

    public Comment assigncommenttopost(Long idcomment, Long idpost);
    public double calculateAverageCommentsPerPost(List<Post> posts);
    Comment ajouterCommentEtAffecterPost(Comment comment, long idPost);
    List<Comment> getCommentsByPostId(Long postId);
    public void likeComment(Long idcomment);
    public void dislikeComment(Long idcomment);
}
