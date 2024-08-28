package br.graphpedia.graphapi.infra.controller.interceptor;

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

@Component
public class NeedForContextInterceptor implements HandlerInterceptor {

    private TermUseCase termUseCase;

    @Autowired
    public NeedForContextInterceptor(TermUseCase termUseCase) {
        this.termUseCase = termUseCase;
    }

    @Override 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<String> contexts = termUseCase.verifyNeedForContext(extractTermFromRequest(request));

        if (Objects.nonNull(contexts) && !contexts.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode contextNode = objectMapper.createArrayNode();
            for (String context : contexts) {
                contextNode.add(context);
            }

            ObjectNode responseObject = JsonNodeFactory.instance.objectNode();
            responseObject.set("contexts", contextNode);

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
