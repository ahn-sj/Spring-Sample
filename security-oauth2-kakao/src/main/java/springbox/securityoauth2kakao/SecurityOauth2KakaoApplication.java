package springbox.securityoauth2kakao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springbox.securityoauth2kakao.config.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class SecurityOauth2KakaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityOauth2KakaoApplication.class, args);
	}

}
