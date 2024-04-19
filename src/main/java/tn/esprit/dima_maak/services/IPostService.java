package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Post;

import java.time.LocalDate;
import java.util.List;

public interface IPostService {
    Post addPost(Post post);
    Post updatePost(Post post);
    Post findPostById(Long id);
    void deletePost(Long id);
    List<Post> getAll();
    public String calculatePercentagePerTitle(String title);
    public void likePost(Long postId);
    public void dislikePost(Long postId);



}
