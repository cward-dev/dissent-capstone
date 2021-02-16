package capstone.dissent.data;

import capstone.dissent.models.Source;

import java.util.List;

public interface SourceRepository {

    // create
    Source add(Source source);

    // read
    List<Source> findAll();
    Source findById(int sourceId);

    // update
    boolean edit(Source source);

    // delete
    boolean deleteById(int sourceId);

    // helpers
    List<Source> getSourceFromLogin(Source userLogin);

}
