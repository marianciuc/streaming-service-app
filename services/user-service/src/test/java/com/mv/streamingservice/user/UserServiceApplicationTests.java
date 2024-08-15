
package com.mv.streamingservice.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

@SpringBootTest
class UserServiceApplicationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
         .withUserConfiguration(UserServiceApplication.class);

	@Test
	void testMainMethod() {
         contextRunner.run(context -> {
             // Check here for loaded beans
         });
	}

	@Test
	void contextLoads() {
	}

}
