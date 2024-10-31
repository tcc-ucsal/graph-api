package br.graphpedia.graphapi.infra.config;

import br.graphpedia.graphapi.infra.controller.interceptor.NeedForContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final NeedForContextInterceptor needForContextInterceptor;

    @Autowired
    public WebConfig(NeedForContextInterceptor needForContextInterceptor) {
        this.needForContextInterceptor = needForContextInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(needForContextInterceptor)
                .addPathPatterns("/term/create");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
