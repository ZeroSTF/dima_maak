package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Comment;
import tn.esprit.dima_maak.entities.Complaint;
import tn.esprit.dima_maak.entities.Post;

import java.util.List;

public interface ICommentService {
    Comment addComment(Comment comment);
    Comment updateComment(Comment comment);
    Comment findCommentById(Long id);
    void deleteComment(Long id);
    List<Comment> getAll();

    public Comment assigncommenttopost(Long idcomment, Long idpost);
    public double calculateAverageCommentsPerPost(List<Post> posts);
}
