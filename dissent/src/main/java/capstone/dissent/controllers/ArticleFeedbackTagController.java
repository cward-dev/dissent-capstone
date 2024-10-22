package capstone.dissent.controllers;

import capstone.dissent.domain.ArticleService;
import capstone.dissent.domain.Result;
import capstone.dissent.models.ArticleFeedbackTag;
import capstone.dissent.models.FeedbackTagHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article/feedback-tag")
public class ArticleFeedbackTagController {

    private final ArticleService service;

    public ArticleFeedbackTagController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/article/{articleId}")
    public List<FeedbackTagHelper> findByArticleId(@PathVariable String articleId) {
        List<FeedbackTagHelper> result = service.findArticleFeedbackTagsByArticleId(articleId);
        return result;
    }

    @GetMapping("/{articleId}/{userId}/{feedbackTagId}")
    public ResponseEntity<ArticleFeedbackTag> findByKey(@PathVariable String articleId, @PathVariable String userId, @PathVariable int feedbackTagId) {
        ArticleFeedbackTag articleFeedbackTag = service.findArticleFeedbackTagByKey(articleId, userId, feedbackTagId);
        if (articleFeedbackTag == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(articleFeedbackTag);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody ArticleFeedbackTag articleFeedbackTag) {
        Result<Void> result = service.addFeedbackTag(articleFeedbackTag);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{articleId}/{userId}/{feedbackTagId}")
    public ResponseEntity<Void> deleteByKey(@PathVariable String articleId,@PathVariable String userId, @PathVariable int feedbackTagId) {
        if (service.deleteFeedbackTagByKey(articleId, userId, feedbackTagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}