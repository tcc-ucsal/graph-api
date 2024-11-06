package br.graphpedia.graphapi.infra.controller.interceptor;

import br.graphpedia.graphapi.core.usecase.VerifyNeedForContextUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NeedForContextInterceptor implements HandlerInterceptor {

    private final VerifyNeedForContextUseCase verifyNeedForContextUseCase;

    @Autowired
    public NeedForContextInterceptor(VerifyNeedForContextUseCase verifyNeedForContextUseCase) {
        this.verifyNeedForContextUseCase = verifyNeedForContextUseCase;
    }

    @Override 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<String> contexts = verifyNeedForContextUseCase.execute(extractTermFromRequest(request));

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

    private String extractTermFromRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        Pattern pattern = Pattern.compile("/term/([^/]+)");
        Matcher matcher = pattern.matcher(uri);

        if (matcher.find()) {
            String encodedTerm = matcher.group(1);
            return URLDecoder.decode(encodedTerm, StandardCharsets.UTF_8);
        }

        throw new IllegalArgumentException("Invalid URL: " + uri);
    }
}
