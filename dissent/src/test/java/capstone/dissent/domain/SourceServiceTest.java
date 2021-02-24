package capstone.dissent.domain;

import capstone.dissent.data.SourceRepository;
import capstone.dissent.models.Source;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

class SourceServiceTest {
    @Autowired
    SourceService service;

    @MockBean
    SourceRepository repository;
    final Source TEST_SOURCE = new  Source("d","Test Source","www.ADD.com","Test Source");


    @Test
    void shouldFindAll() {
        List<Source> all = new ArrayList<>();
        all.add(TEST_SOURCE);

        when(repository.findAll()).thenReturn(all);

        List<Source> results = service.findAll();

        assertNotNull(results);
        assertEquals(1,results.size());

    }

    @Test
    void shouldFindById() {
        List<Source> all = new ArrayList<>();
        all.add(TEST_SOURCE);

        when(repository.findById("d")).thenReturn(TEST_SOURCE);

        Source sourceOut = service.findById("d");
        assertEquals(TEST_SOURCE,sourceOut);
        assertEquals(TEST_SOURCE.getDescription(),sourceOut.getDescription());
    }
    @Test
    void shouldNotFindById(){
        Source source = service.findById("X");
        assertNull(source);
    }

    @Test
    void shouldAdd() {
        Source sourceIn = TEST_SOURCE;
        sourceIn.setSourceId(null);
        Source sourceOut = new Source();

        when(repository.add(sourceIn)).thenReturn(sourceOut);
        Result<Source> result = service.add(sourceIn);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(sourceOut, result.getPayload());
    }

    @Test
    void shouldNotAddWithSetId(){
        Source source = TEST_SOURCE;

        Result<Source> result = service.add(source);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Id cannot be set for `add` operation."));
    }

    @Test
    void shouldNotAddDuplicate(){
        Source source = TEST_SOURCE;
        source.setSourceId(null);

        when(repository.findAll()).thenReturn(List.of(TEST_SOURCE));

        Result<Source> result = service.add(source);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Duplicate sources are not allowed."));

    }


    @Test
    void shouldEdit() {
        Source source = TEST_SOURCE;
        source.setDescription("I changed this!");

        when(repository.edit(source)).thenReturn(true);
        when(repository.findById(source.getSourceId())).thenReturn(source);

        Result<Source> result = service.edit(source);
        assertEquals(ResultType.SUCCESS,result.getType());

    }

    @Test
    void shouldNotEditWithNullId(){
        Source source = TEST_SOURCE;
        source.setSourceId(null);
        Result<Source> result = service.edit(source);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Must have a valid Id to edit source!"));

    }

    @Test
    void shouldNotEditWithChangedWebsiteURL(){
        Source source = new  Source("d","Test Source","EDIT","Test Source");

        when(repository.findById(source.getSourceId())).thenReturn(TEST_SOURCE);

        Result<Source> result = service.edit(source);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Changes to Source URL not allowed!"));
    }

    @Test
    void shouldNotEditSourceThatDoesNotExit(){
        Source source = TEST_SOURCE;

        when(repository.findById(source.getSourceId())).thenReturn(TEST_SOURCE);
        when(repository.edit(source)).thenReturn(false);

        Result<Source> result = service.edit(source);

        assertFalse(result.isSuccess());
        assertTrue(result.getType()==ResultType.NOT_FOUND);

    }

    @Test
    void shouldDelete() {
        Source source = TEST_SOURCE;

        when(repository.deleteById(source.getSourceId())).thenReturn(true);

        boolean success = service.delete(source.getSourceId());
        assertTrue(success);
    }

    @Test
    void shouldNotDelete(){
        Source source = TEST_SOURCE;

        when(repository.deleteById(source.getSourceId())).thenReturn(false);

        boolean success = service.delete(source.getSourceId());
        assertFalse(success);

    }

    @Test
    void  shouldNotAddBlankSource(){
        Result<Source> result = service.add(null);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Source cannot be blank!"));
    }
    @Test
    void  shouldNotBlankWebsiteURL(){
        Result<Source> result = service.add(new Source());
        assertFalse(result.isSuccess());
        System.out.println(result.getMessages().get(0));
        assertTrue(result.getMessages().contains("Website Url cannot be blank"));
    }
}