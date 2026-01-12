package projet.soa.fr.Rules_MS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RulesMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RulesMsApplication.class, args);
	}
}
