package br.graphpedia.graphapi.core.config;

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
                .addPathPatterns(Routes.TERM_PREFIX + Routes.CREATE);
    }
}
