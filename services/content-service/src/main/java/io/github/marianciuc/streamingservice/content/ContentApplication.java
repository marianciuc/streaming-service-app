package io.github.marianciuc.streamingservice.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ContentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentApplication.class, args);
	}

}
