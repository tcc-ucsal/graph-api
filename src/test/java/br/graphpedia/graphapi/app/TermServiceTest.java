package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.persistence.ITermRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TermServiceTest {

    @Mock
    private ITermRepository termRepository;

    @InjectMocks
    private TermService termService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTerm() {
        // Given
        Term term = new Term();

        term.setId(UUID.randomUUID());
        term.setTitle("Test Title");
        term.setDescription("Test Description");
        term.setSource("Test Source");
        term.setCreatedDate(LocalDateTime.now());
        term.setUpdatedDate(LocalDateTime.now());
        term.setConnectionWiths(new HashSet<>());

        when(termRepository.create(term)).thenReturn(term);

        // When
        Term createdTerm = termService.create(term);

        // Then
        assertNotNull(createdTerm);
        assertEquals(term.getId(), createdTerm.getId());
        assertEquals(term.getTitle(), createdTerm.getTitle());
        assertEquals(term.getDescription(), createdTerm.getDescription());
        assertEquals(term.getSource(), createdTerm.getSource());
        verify(termRepository, times(1)).create(term);
    }
}
