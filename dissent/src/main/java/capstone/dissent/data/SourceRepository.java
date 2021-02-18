package capstone.dissent.data;

import capstone.dissent.models.Source;

import java.util.List;

public interface SourceRepository {

    // create
    Source add(Source source);

    // read
    List<Source> findAll();
    Source findById(String sourceId);

    // update
    boolean edit(Source source);

    // delete
    boolean deleteById(String sourceId);

    // helpers
    List<Source> getSourceFromLogin(Source userLogin);

}
