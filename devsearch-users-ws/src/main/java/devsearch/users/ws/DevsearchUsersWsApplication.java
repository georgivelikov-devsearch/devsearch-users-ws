package devsearch.users.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DevsearchUsersWsApplication {

    public static void main(String[] args) {
	SpringApplication.run(DevsearchUsersWsApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPosswordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext getSpringApplicationContext() {
	return new SpringApplicationContext();
    }
}
