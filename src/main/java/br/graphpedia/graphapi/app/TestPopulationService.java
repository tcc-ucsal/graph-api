package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.exceptions.PersistenceException;
import br.graphpedia.graphapi.core.persistence.IContextTermRepository;
import br.graphpedia.graphapi.core.persistence.IStructTermRepository;
import br.graphpedia.graphapi.core.usecase.test.TestPopulationUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TestPopulationService implements TestPopulationUseCase {

    private final IStructTermRepository structTermRepository;

    private final IContextTermRepository contextTermRepository;
    private static final String PATH_MOCKS = "src/main/resources/mocks/";

    private final List<String> mocks = Arrays.asList(
            "computerTermMock.json",
            "exampleTermMock.json",
            "incompleteTermMock.json",
            "oneTermMock.json",
            "testTermMock.json");

    @Autowired
    public TestPopulationService(IStructTermRepository structTermRepository, IContextTermRepository contextTermRepository) {
        this.structTermRepository = structTermRepository;
        this.contextTermRepository = contextTermRepository;
    }

    @Override
    public void execute() {
        ObjectMapper objectMapper = new ObjectMapper();

        mocks.forEach(mockFile -> {
            Term graph = new Term("Test Term");
            try {
                graph = objectMapper.readValue(new File(PATH_MOCKS + mockFile), Term.class);

                TermContext createdContext = contextTermRepository.save(graph.getContext());
                structTermRepository.create(graph);
                graph.setContext(createdContext);

            } catch (Exception exception){
                contextTermRepository.deleteByTitle(graph.getTitle());
                structTermRepository.deleteByTitleIfNotIncomingConnections(graph.getTitle());
                throw new PersistenceException("Error on save graph", exception);
            }
        });


    }

    @Override
    public List<String> listMockNames() {
        List<String> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        mocks.forEach(mock -> {
            try {
                Term graph = objectMapper.readValue(new File(PATH_MOCKS + mock), Term.class);
                result.add(graph.getTitle());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return result;
    }
}
