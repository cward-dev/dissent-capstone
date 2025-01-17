package capstone.dissent.data;

import capstone.dissent.models.Source;

import java.util.List;

public interface SourceRepository {

    // create
    Source add(Source source);

    // read
    List<Source> findAll();

    Source findById(String sourceId);

    Source findBySourceNameAndUrl(String sourceName, String sourceUrl);

    // update
    boolean edit(Source source);

    // delete
    boolean deleteById(String sourceId);


}
