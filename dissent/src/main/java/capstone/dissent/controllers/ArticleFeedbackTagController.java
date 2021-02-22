package capstone.dissent.controllers;

import capstone.dissent.domain.ArticleService;
import capstone.dissent.domain.Result;
import capstone.dissent.models.ArticleFeedbackTag;
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

    @GetMapping("/{articleId}/{userId}")
    public List<ArticleFeedbackTag> add(@PathVariable String articleId, @PathVariable String userId) {
        return service.findUserFeedbackForArticle(articleId, userId);
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