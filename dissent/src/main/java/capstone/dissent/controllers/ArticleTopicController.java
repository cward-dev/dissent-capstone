package capstone.dissent.controllers;

import capstone.dissent.domain.ArticleService;
import capstone.dissent.domain.Result;
import capstone.dissent.models.ArticleFeedbackTag;
import capstone.dissent.models.ArticleTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article/topic")
public class ArticleTopicController {

    private final ArticleService service;

    public ArticleTopicController(ArticleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody ArticleTopic articleTopic) {
        Result<Void> result = service.addArticleTopic(articleTopic);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{articleId}/{topicId}")
    public ResponseEntity<Object> delete(@PathVariable String articleId, @PathVariable int topicId) {
        Result<Void> result = service.deleteArticleTopicByKey(articleId, topicId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}