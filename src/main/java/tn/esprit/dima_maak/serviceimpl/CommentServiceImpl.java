package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Comment;
import tn.esprit.dima_maak.entities.InsuranceP;
import tn.esprit.dima_maak.entities.Post;
import tn.esprit.dima_maak.repositories.ICommentRepository;
import tn.esprit.dima_maak.repositories.IPostRepository;
import tn.esprit.dima_maak.services.ICommentService;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private ICommentRepository commentRepository;
    private IPostRepository postRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteComment(Long id) {

    }

    @Override
    public List<Comment> getAll() {
        return (List<Comment>)commentRepository.findAll();
    }

    @Override
    public Comment assigncommenttopost(Long idcomment, Long idpost) {

            Post post = postRepository.findById(idpost).orElse(null);
            Comment comment = commentRepository.findById(idcomment).orElse(null);
            comment.setPost(post);
            return commentRepository.save(comment);

    }

    @Override
    //nbre moyen de commentaires par post
    public double calculateAverageCommentsPerPost(List<Post>posts) {

        int totalComments = 0;
        int totalPosts = posts.size();

        for (Post post : posts) {
            totalComments += post.getComments().size();
        }

        return totalComments / (double) totalPosts;
    }


}
