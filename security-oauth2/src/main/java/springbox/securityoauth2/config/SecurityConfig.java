package springbox.securityoauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import springbox.securityoauth2.auth.PrincipalOAuth2DetailsService;

@Configuration
@EnableWebSecurity(debug = true) //시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOAuth2DetailsService principalOAuth2DetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOAuth2DetailsService);

        return http.build();
    }

}
