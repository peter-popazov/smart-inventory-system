package org.inventory.appuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// todo RBAC
@EnableJpaAuditing
@SpringBootApplication
public class AppUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppUserApplication.class, args);
	}

}
