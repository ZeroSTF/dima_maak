package tn.esprit.dima_maak.serviceimpl;

import com.nimbusds.jose.shaded.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Comment;
import tn.esprit.dima_maak.entities.InsuranceP;
import tn.esprit.dima_maak.entities.Post;
import tn.esprit.dima_maak.repositories.ICommentRepository;
import tn.esprit.dima_maak.repositories.IPostRepository;
import tn.esprit.dima_maak.services.ICommentService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private ICommentRepository commentRepository;
    private IPostRepository postRepository;
    @Autowired
    ServiceBadworld serviceBadworld;

    @Override
    public ResponseEntity<?> addComment(Comment comment) {
        ResponseEntity<String> con = serviceBadworld.filterBadWords1(comment.getContent());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");

        if (badWordsTotal == 0) {
            commentRepository.save(comment);
            return ResponseEntity.ok().body(" Comment added ... ");
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the comment. Please remove them.");
        }

    }

    @Override
    public ResponseEntity<?> updateComment(Comment comment) {
        Comment existingComment = commentRepository.findById(comment.getId()).orElse(null);
        if (existingComment == null) {
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<String> con = serviceBadworld.filterBadWords1(comment.getContent());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");

        if (badWordsTotal == 0) {
            // Mettre à jour le contenu et la date de création
            existingComment.setContent(comment.getContent());
            existingComment.setCreatedDate(comment.getCreatedDate()); // Mettre à jour la date de création avec la date actuelle
            commentRepository.save(existingComment); // Sauvegarder existingComment au lieu de comment
            return ResponseEntity.ok().body("Comment updated ... ");
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the comment. Please remove them.");
        }
    }



    @Override
    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteComment(Long id) {
     commentRepository.deleteById(id);
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

    @Override
    public Comment ajouterCommentEtAffecterPost(Comment comment, long idPost) {
        Post post= postRepository.findById(idPost).orElse(null);
        comment.setPost(post);
        return commentRepository.save(comment);
    }
    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public void likeComment(Long idcomment) {
        Comment comment = commentRepository.findById(idcomment)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + idcomment));

        comment.setRating(comment.getRating() + 1);

        commentRepository.save(comment);
    }

    @Override
    public void dislikeComment(Long idcomment) {
        Comment comment = commentRepository.findById(idcomment)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + idcomment));

        int Ratings = comment.getRating();
        if (Ratings > 0) {
            comment.setRating(Ratings - 1);
        }
        commentRepository.save(comment);
    }


}
