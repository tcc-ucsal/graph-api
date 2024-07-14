package br.graphpedia.graphapi.core.config;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.usecase.TermUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Component
public class CreateTermInterceptor implements HandlerInterceptor {

    private TermUseCase termUseCase;

    @Autowired
    public CreateTermInterceptor(TermUseCase termUseCase) {
        this.termUseCase = termUseCase;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<String> synonyms = termUseCase.getSynonymTerms(extractTermFromRequest(request));

        if (Objects.nonNull(synonyms) && !synonyms.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode synonymsNode = objectMapper.createArrayNode();
            for (String synonym : synonyms) {
                synonymsNode.add(synonym);
            }

            ObjectNode responseObject = JsonNodeFactory.instance.objectNode();
            responseObject.set("synonyms", synonymsNode);

            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(responseObject));

            return false;
        }

        return true;
    }

    private String extractTermFromRequest(HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Term requestObject = mapper.readValue(request.getInputStream(), Term.class);
        return requestObject.getTitle();
    }
}
