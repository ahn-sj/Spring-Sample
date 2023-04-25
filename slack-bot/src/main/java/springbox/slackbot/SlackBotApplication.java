package springbox.slackbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SlackBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlackBotApplication.class, args);
	}

}
