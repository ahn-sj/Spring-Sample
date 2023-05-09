package springbox.securityoauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springbox.securityoauth2.auth.jwt.TokenAuthenticationFilter;
import springbox.securityoauth2.auth.oauth.*;
import springbox.securityoauth2.user.Role;

@Configuration
@EnableWebSecurity// (debug = true) //시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // TODO: 쿠키에 JSESSIONID 생성 막기

        http
                .authorizeRequests()
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                        .antMatchers("/has-auth").hasRole(Role.USER.name())
                        .antMatchers("/login", "/join", "/refresh-token", "/oauth2/authorize/**", "/home").permitAll()
                        .anyRequest().authenticated();

        http
                .oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(new CookieAuthorizationRequestRepository())
                .and()
//                    .redirectionEndpoint()
//                    .baseUri("/oauth2/callback/*")
//                .and()
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                .and()
                    .successHandler(oAuth2SuccessHandler)
                    .failureHandler(oAuth2FailureHandler);

        http
                .logout()
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");

        //jwt filter 설정
        http
                .addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
