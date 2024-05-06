package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Comment;
import tn.esprit.dima_maak.entities.Complaint;
import tn.esprit.dima_maak.entities.Post;
import tn.esprit.dima_maak.services.ICommentService;
import tn.esprit.dima_maak.services.IComplaintService;

import java.util.List;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CommentRestController {
    private ICommentService commentService;

    @PostMapping("/save")
    public ResponseEntity<?> addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
    @PutMapping("/update")
    public ResponseEntity<?>  updateComment(@RequestBody Comment comment){
        return commentService.updateComment(comment);
    }

    @GetMapping("/get/{idComment}")
    public Comment getComment(@PathVariable("idComment") long idComment){
        return commentService.findCommentById(idComment);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        return "comment deleted !";
    }
    @GetMapping("/all")
    public List<Comment> getAllComment(){
        return commentService.getAll();
    }

    @PutMapping("/assigncommenttopost/{idcomment}/{idpost}")
    public Comment assigncommenttopost (@PathVariable Long idcomment,@PathVariable Long idpost)
    {
        return commentService.assigncommenttopost(idcomment,idpost);
    }
    @PostMapping("/addAffect/{idPost}")
    public Comment ajouterCommentEtAffecterPost(@RequestBody Comment comment,@PathVariable long idPost) {
        return commentService.ajouterCommentEtAffecterPost(comment,idPost);
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
   /* @GetMapping("/calculateAverageCommentsPerPost/{idpost}")
    public String calculateAverageCommentsPerPost(@PathVariable Long idpost){
        return commentService.calculateAverageCommentsPerPost(List< Post >posts);

    }*/
   @PostMapping("/{commentId}/like")
   public String likeComment(@PathVariable Long commentId) {
       commentService.likeComment(commentId);
       return "Like added successfully!";
   }

    @PostMapping("/{commentId}/dislike")
    public String dislikeComment(@PathVariable Long commentId) {
        commentService.dislikeComment(commentId);
        return "DisLike added successfully!";
    }

}

