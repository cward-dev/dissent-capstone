package capstone.dissent.controllers;

import capstone.dissent.domain.Result;
import capstone.dissent.domain.TopicService;
import capstone.dissent.models.Topic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    private final TopicService service;

    public TopicController(TopicService service) {
        this.service = service;
    }

    @GetMapping
    public List<Topic> findAll() {
        return service.findAll();
    }

    @GetMapping("/with-inactive")
    public List<Topic> findAllWithInactive() {
        return service.findAllWithInactive();
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<Topic> findById(@PathVariable int topicId) {
        Topic topic = service.findById(topicId);
        if (topic == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(topic);
    }

    @GetMapping("/name/{topicName}")
    public ResponseEntity<Topic> findByTopicName(@PathVariable String topicName) {
        Topic topic = service.findByTopicName(topicName);
        if (topic == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(topic);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Topic topic) {
        Result<Topic> result = service.add(topic);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<Object> edit(@PathVariable int topicId, @RequestBody Topic topic) {
        if (topicId != topic.getTopicId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Topic> result = service.edit(topic);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Topic> deleteById(@PathVariable int topicId) {
        if (service.deleteById(topicId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
