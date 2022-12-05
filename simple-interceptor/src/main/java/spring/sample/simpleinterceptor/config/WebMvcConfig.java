package spring.sample.simpleinterceptor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import spring.sample.simpleinterceptor.computer.repository.ComputerRepository;
import spring.sample.simpleinterceptor.interceptor.SimpleInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ComputerRepository computerRepository;

    public WebMvcConfig(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SimpleInterceptor(computerRepository));
    }
}
