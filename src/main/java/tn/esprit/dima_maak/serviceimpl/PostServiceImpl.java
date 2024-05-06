package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    ServiceBadworld serviceBadworld;

    @Override
    public ResponseEntity<?> addPost(Post post) {
        ResponseEntity<String> con = serviceBadworld.filterBadWords1(post.getContent());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");

        if (badWordsTotal == 0) {
            postRepository.save(post);
            return ResponseEntity.ok().body(" post added ... ");
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the post. Please remove them.");
        }

    }
    @Override
    public void favoritePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);

        if (post != null) {
            if (!post.getFavorites().contains(userId)) {
                post.getFavorites().add(userId);
                postRepository.save(post);
                System.out.println("Post favorited successfully by user: " + userId);
            } else {
                System.out.println("User: " + userId + " has already favorited this post.");
            }
        } else {
            System.out.println("Post with ID: " + postId + " not found.");
        }
    }
    @Override
    public ResponseEntity<?> updatePost(Post post) {
        Post post1=postRepository.findById(post.getId()).orElse(null);
        ResponseEntity<String> con = serviceBadworld.filterBadWords1(post.getContent());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");

        if (badWordsTotal == 0) {

            post1.setTitle(post.getTitle());
            post1.setContent(post.getContent());
            post1.setCreatedDate(post.getCreatedDate());
            postRepository.save(post);
            return ResponseEntity.ok().body(" post updated ... ");
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the post. Please remove them.");
        }

    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePost(Long id) {
     postRepository.deleteById(id);
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


