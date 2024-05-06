package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Post;
import tn.esprit.dima_maak.services.IPostService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostRestController {
    private IPostService postService;

    @PostMapping("/save")
    public Post addPost(@RequestBody Post post){
        return postService.addPost(post);
    }
    @PutMapping("/update")
    public Post updatePost(@RequestBody Post post){
        return postService.updatePost(post);
    }

    @GetMapping("/get/{idPost}")
    public Post getPost(@PathVariable("idPost") long idPost){
        return postService.findPostById(idPost);
    }
    @DeleteMapping("/delete/{id}")
    public String deletePost( Long id){
        postService.deletePost(id);
        return "Post deleted !";
    }
    @GetMapping("/all")
    public List<Post> getAllPost(){
        return postService.getAll();
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {
        postService.likePost(postId);
        return "Like added successfully!";
    }

    @PostMapping("/{postId}/dislike")
    public String dislikePost(@PathVariable Long postId) {
        postService.dislikePost(postId);
        return "DisLike added successfully!";
    }

    @GetMapping("/calculatePercentagePerTitle/{title}")
    public String calculatePercentagePerTitle(@PathVariable String title){
        return postService.calculatePercentagePerTitle(title);
    }






}
