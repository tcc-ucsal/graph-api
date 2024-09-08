package br.graphpedia.graphapi.infra.config;

import br.graphpedia.graphapi.infra.controller.interceptor.NeedForContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final NeedForContextInterceptor needForContextInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    public WebConfig(NeedForContextInterceptor needForContextInterceptor) {
        this.needForContextInterceptor = needForContextInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(needForContextInterceptor)
                .addPathPatterns("/term/create");
    }
}
