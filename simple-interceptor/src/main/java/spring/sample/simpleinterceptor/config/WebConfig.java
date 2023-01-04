package spring.sample.simpleinterceptor.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.PatternMatchUtils;
import spring.sample.simpleinterceptor.filter.LogFilter;

import javax.servlet.Filter;

@Configuration
public class WebConfig {
    /**
     * ServletComponentScan이나 WebFilter로도 필터 등록이 가능하지만 필터 순서를 지정하지 못한다.
     * 그렇기에 FilterRegistrationBean을 사용하는 것이 적합하다.
     */

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
