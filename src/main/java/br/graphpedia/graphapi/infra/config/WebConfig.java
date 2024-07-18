package br.graphpedia.graphapi.infra.config;

import br.graphpedia.graphapi.infra.controller.interceptor.CreateTermInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final CreateTermInterceptor createTermInterceptor;

    @Autowired
    public WebConfig(CreateTermInterceptor createTermInterceptor) {
        this.createTermInterceptor = createTermInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createTermInterceptor)
                .addPathPatterns("/term/create");
    }
}
