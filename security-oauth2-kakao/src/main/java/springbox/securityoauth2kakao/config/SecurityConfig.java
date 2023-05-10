package springbox.securityoauth2kakao.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity// (debug = true) //시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
//                .antMatchers("/has-auth").hasRole(Role.USER.name())
//                .antMatchers("/login", "/join", "/refresh-token", "/oauth2/authorize/**", "/home").permitAll()
                .anyRequest().authenticated();

        http
                .oauth2Login()
                .defaultSuccessUrl("/login-success")
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
//                .authorizationRequestRepository(new CookieAuthorizationRequestRepository())
//                .and()
//                    .redirectionEndpoint()
//                    .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint();
//                .userService(customOAuth2UserService)
//                .and()
//                .successHandler(oAuth2SuccessHandler)
//                .failureHandler(oAuth2FailureHandler);

//        http
//                .logout()
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSucce*/ssUrl("/");

        //jwt filter 설정
//        http
//                .addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
