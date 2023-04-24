package com.tokev.demo.interceptordemospring;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final RequestInterceptor requestInterceptor;

    public WebConfig(RequestInterceptor requestInterceptor){
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(requestInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedMethods("*").allowedHeaders("*");
    }
    
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
      Optional<HttpMessageConverter<?>> registeredJackson2HttpMessageConverter =
          messageConverters
              .stream()
              .filter(MappingJackson2HttpMessageConverter.class::isInstance)
              .findFirst();
      if(registeredJackson2HttpMessageConverter.isPresent()) {
        messageConverters
            .add(messageConverters
                    .indexOf(registeredJackson2HttpMessageConverter.get()),
                new MappingJackson2HttpMessageConverterWithContentLength());
      } else {
        messageConverters.add(new MappingJackson2HttpMessageConverterWithContentLength());
      }
    }
}
