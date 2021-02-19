package capstone.dissent.controllers;


import capstone.dissent.domain.Result;
import capstone.dissent.domain.SourceService;
import capstone.dissent.models.Source;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/source")
public class SourceController {

    private final SourceService service;

    public SourceController(SourceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Source> findAll(){
        return service.findAll();
    }

    @GetMapping("/{sourceId}")
    public ResponseEntity<Source> findById(@PathVariable String sourceId){
        Source source = service.findById(sourceId);
        if(source == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(source);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Source source){
        Result<Source> result = service.add(source);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(),HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{sourceId}")
    public ResponseEntity<Object> edit(@PathVariable String sourceId, @RequestBody Source source){

        if(!sourceId.equalsIgnoreCase(source.getSourceId())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Source> result = service.edit(source);

        if(result.isSuccess()){
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  ErrorResponse.build(result);
    }

    @DeleteMapping("/{sourceId}")
    public ResponseEntity<Void> delete(@PathVariable String sourceId){
        if(service.delete(sourceId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

}
