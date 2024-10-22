package capstone.dissent.controllers;

import capstone.dissent.domain.PostService;
import capstone.dissent.domain.Result;
import capstone.dissent.models.ArticleFeedbackTag;
import capstone.dissent.models.FeedbackTagHelper;
import capstone.dissent.models.PostFeedbackTag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post/feedback-tag")
public class PostFeedbackTagController {

    private final PostService service;

    public PostFeedbackTagController(PostService service) {
        this.service = service;
    }

    @GetMapping("/post/{postId}")
    public List<FeedbackTagHelper> findByPostId(@PathVariable String postId) {
        return service.findPostFeedbackTagByPostId(postId);
    }

    @GetMapping("/user/{userId}")
    public List<FeedbackTagHelper> findByUserId(@PathVariable String userId) {
        return service.findPostFeedbackTagByUserId(userId);
    }

    @GetMapping("/{postId}/{userId}/{feedbackTagId}")
    public ResponseEntity<PostFeedbackTag> findByKey(@PathVariable String postId, @PathVariable String userId, @PathVariable int feedbackTagId) {
        PostFeedbackTag postFeedbackTag = service.findPostFeedbackTagByKey(postId, userId, feedbackTagId);
        if (postFeedbackTag == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(postFeedbackTag);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody PostFeedbackTag postFeedbackTag) {
        Result<Void> result = service.addFeedbackTag(postFeedbackTag);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{postId}/{userId}/{feedbackTagId}")
    public ResponseEntity<Void> deleteByKey(@PathVariable String postId,@PathVariable String userId, @PathVariable int feedbackTagId) {
        if (service.deleteFeedbackTagByKey(postId, userId, feedbackTagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}