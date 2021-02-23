package capstone.dissent.controllers;

import capstone.dissent.domain.ArticleService;
import capstone.dissent.domain.Result;
import capstone.dissent.models.Article;
import capstone.dissent.models.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Article> findAll(){
        List<Article> articles = service.findAll();
        return service.findAll();
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Article> findById(@PathVariable String articleId){
        Article article = service.findById(articleId);
        if(article == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(article);
    }

    @GetMapping("/topic/{topicId}") // TODO maybe delete?
    public List<Article> findArticleByTopicId(@PathVariable int topicId){
       return service.findArticleByTopicId(topicId);
    }

    @GetMapping("/dates/{date1}/{date2}")
    public List<Article> findPostedDateRange(@PathVariable String date1, @PathVariable String date2){
        LocalDateTime dateTime1 = LocalDateTime.parse(date1);
        LocalDateTime dateTime2 = LocalDateTime.parse(date2);
        return service.findByPostedDateRange(dateTime1,dateTime2);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Article article){
        Result<Article> result = service.add(article);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(),HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
   @PutMapping("/{articleId}")
    public ResponseEntity<Object> update(@PathVariable String articleId, @RequestBody Article article){

        if(!articleId.equals(article.getArticleId())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Article> result = service.update(article);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
   }

   @DeleteMapping("/{articleId}")
    public ResponseEntity<Void>  inactivateArticle(@PathVariable String articleId){
        if(service.inactivateArticle(articleId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

}
