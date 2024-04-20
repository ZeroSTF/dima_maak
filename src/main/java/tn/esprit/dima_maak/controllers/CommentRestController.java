package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
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
public class CommentRestController {
    private ICommentService commentService;

    @PostMapping("/save")
    public Comment addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
    @PutMapping("/update")
    public Comment updateComment(@RequestBody Comment comment){
        return commentService.updateComment(comment);
    }

    @GetMapping("/get/{idComment}")
    public Comment getComment(@PathVariable("idComment") long idComment){
        return commentService.findCommentById(idComment);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteComment( Long id){
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

   /* @GetMapping("/calculateAverageCommentsPerPost/{idpost}")
    public String calculateAverageCommentsPerPost(@PathVariable Long idpost){
        return commentService.calculateAverageCommentsPerPost(List< Post >posts);

    }*/


}

