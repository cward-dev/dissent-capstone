package capstone.dissent.domain;


import capstone.dissent.data.SourceRepository;
import capstone.dissent.models.Article;
import capstone.dissent.models.Source;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class SourceService {

    private final SourceRepository repository;

    public SourceService(SourceRepository repository) {
        this.repository = repository;
    }

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public List<Source> findAll() {
        return repository.findAll();
    }

    public Source findById(String sourceId) {
        return repository.findById(sourceId);
    }

    public Source findBySourceNameAndUrl(String sourceName, String websiteUrl) {
        return repository.findBySourceNameAndUrl(sourceName, websiteUrl);
    }

    public Result<Source> add (Source source){
        Result<Source> result = validate(source);
        if(!result.isSuccess()){
            return result;
        }

        if(source.getSourceId() != null){
            result.addMessage("Id cannot be set for `add` operation.", ResultType.INVALID);
            return result;
        }
        boolean isDuplicateSource = isDuplicate(source);
        if(isDuplicateSource){
            result.addMessage("Duplicate sources are not allowed.", ResultType.INVALID);
            return result;
        }
        UUID uuid= UUID.randomUUID();
        source.setSourceId(uuid.toString());

        source = repository.add(source);
        result.setPayload(source);
        return result;
    }

    public Result<Source> edit(Source source){
        Result<Source> result = validate(source);

        if (!result.isSuccess()) {
            return result;
        }

        if(source.getSourceId()==null){
            result.addMessage("Must have a valid Id to edit source!", ResultType.INVALID);
            return result;
        }
        //making sure URL is not changed
        Source originalSource = findById(source.getSourceId());
        if(!originalSource.getWebsiteUrl().equalsIgnoreCase(source.getWebsiteUrl())){
            result.addMessage("Changes to Source URL not allowed!", ResultType.INVALID);
            return result;
        }

        if(!repository.edit(source)){
            String msg = String.format("Source:  %s  -  %s, was not found! ",source.getSourceName(),source.getWebsiteUrl());
            result.addMessage(msg,ResultType.NOT_FOUND);
            return result;
        }
        return result;
    }

    public boolean delete(String sourceId){
        return repository.deleteById(sourceId);
    }


    private boolean isDuplicate(Source source){
        List<Source> sources = repository.findAll().stream()
                .filter(source1 -> source1.getWebsiteUrl().equalsIgnoreCase(source.getWebsiteUrl())).collect(Collectors.toList());

             return sources.size()>0;
    }

    private Result<Source> validate(Source source) {
        Result<Source> result = new Result<>();

        if (source == null) {
            result.addMessage("Source cannot be blank!", ResultType.INVALID);
            return result;
        }
        if (result.isSuccess()) {
            Set<ConstraintViolation<Source>> violations = validator.validate(source);

            if (!violations.isEmpty()) {
                for (ConstraintViolation<Source> violation : violations) {
                    result.addMessage(violation.getMessage(), ResultType.INVALID);
                }
            }

        }
        return result;
    }
}
