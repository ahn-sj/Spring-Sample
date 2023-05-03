package springbox.securityoauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springbox.securityoauth2.auth.jwt.TokenAuthenticationFilter;
import springbox.securityoauth2.auth.oauth.CustomOAuth2UserService;
import springbox.securityoauth2.auth.oauth.OAuth2SuccessHandler;
import springbox.securityoauth2.auth.Role;

@Configuration
@EnableWebSecurity(debug = true) //시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable();

        http
                .authorizeRequests()
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated();

        http
                .logout()
                        .logoutSuccessUrl("/");

        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2SuccessHandler);

        http
                .logout()
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");

        //jwt filter 설정
        http
                .addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
