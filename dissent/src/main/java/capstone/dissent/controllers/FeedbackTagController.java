package capstone.dissent.controllers;

import capstone.dissent.domain.FeedbackTagService;
import capstone.dissent.domain.Result;
import capstone.dissent.models.FeedbackTag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3030"})
@RequestMapping("/api/feedback-tag")
public class FeedbackTagController {

    private final FeedbackTagService service;

    public FeedbackTagController(FeedbackTagService service) {
        this.service = service;
    }

    @GetMapping
    public List<FeedbackTag> findAll() {
        return service.findAll();
    }

    @GetMapping("/inactive")
    public List<FeedbackTag> findAllInactive() {
        return service.findAllInactive();
    }

    @GetMapping("/{feedbackTagId}")
    public ResponseEntity<FeedbackTag> findById(@PathVariable int feedbackTagId) {
        FeedbackTag feedbackTag = service.findById(feedbackTagId);
        if (feedbackTag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(feedbackTag);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody FeedbackTag feedbackTag) {
        Result<FeedbackTag> result = service.add(feedbackTag);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/{feedbackTagId}")
    public ResponseEntity<Object> edit(@PathVariable int feedbackTagId, @RequestBody FeedbackTag feedbackTag) {
        if (feedbackTagId != feedbackTag.getFeedbackTagId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<FeedbackTag> result = service.edit(feedbackTag);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{feedbackTagId}")
    public ResponseEntity<FeedbackTag> deleteById(@PathVariable int feedbackTagId) {
        if (service.deleteById(feedbackTagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
