package capstone.dissent.controllers;

import capstone.dissent.domain.PostService;
import capstone.dissent.domain.Result;
import capstone.dissent.models.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> findAll() {
        return service.findAll();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findById(@PathVariable String postId) {
        Post post = service.findById(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(post);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<Post>> findByArticleId(@PathVariable String articleId) {
        List<Post> posts = service.findByArticleId(articleId);
        if (posts.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> findByUserId(@PathVariable String userId) {
        List<Post> posts = service.findByUserId(userId);
        if (posts.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Post post, UsernamePasswordAuthenticationToken principal) {
        Result<Post> result = service.add(post);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Object> edit(@PathVariable String postId, @RequestBody Post post) {
        if (!postId.equals(post.getPostId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Post> result = service.edit(post);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Post> deleteById(@PathVariable String postId) {
        if (service.deleteById(postId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
