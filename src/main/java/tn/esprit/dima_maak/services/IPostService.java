package tn.esprit.dima_maak.services;

import org.springframework.http.ResponseEntity;
import tn.esprit.dima_maak.entities.Post;

import java.time.LocalDate;
import java.util.List;

public interface IPostService {
    ResponseEntity<?> addPost(Post post);
    public ResponseEntity<?> updatePost(Post post);
    public void favoritePost(Long postId, Long userId);
    Post findPostById(Long id);
    void deletePost(Long id);
    List<Post> getAll();
    public String calculatePercentagePerTitle(String title);
    public void likePost(Long postId);
    public void dislikePost(Long postId);



}
