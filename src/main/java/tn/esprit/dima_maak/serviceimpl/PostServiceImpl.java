package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Post;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.IPostRepository;
import tn.esprit.dima_maak.services.IPostService;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements IPostService {
    private IPostRepository postRepository;

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePost(Long id) {

    }

    @Override
    public List<Post> getAll() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public String calculatePercentagePerTitle(String title) {
        Long totalposts = postRepository.countPosts();
        if(totalposts==0){
            return"0.00%";
        }
        Long postsbytitle = postRepository.countPostsByTitle(title);
        double percentage = (double) postsbytitle/totalposts *100.0;
        DecimalFormat df = new DecimalFormat("0.00%");
        return df.format(percentage/100.0);
    }


    @Override
    public void likePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        // Augmenter le nombre de likes du post
        post.setLikes(post.getLikes() + 1);

        // Mettre à jour le post dans la base de données
        postRepository.save(post);
    }

    @Override
    public void dislikePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
        // Diminuer le nombre de likes du post
        int likes = post.getLikes();
        if (likes > 0) {
            post.setLikes(likes - 1);
        }
        postRepository.save(post);
    }


}


