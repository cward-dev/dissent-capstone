package capstone.dissent.controllers;

import capstone.dissent.domain.PostService;
import capstone.dissent.domain.Result;
import capstone.dissent.models.PostFeedbackTag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/post/feedback-tag")
public class PostFeedbackTagController {

    private final PostService service;

    public PostFeedbackTagController(PostService service) {
        this.service = service;
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