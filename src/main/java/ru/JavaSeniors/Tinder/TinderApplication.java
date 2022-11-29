package ru.JavaSeniors.Tinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories(basePackages = "ru.JavaSeniors.Tinder.repository")
public class TinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(TinderApplication.class, args);
	}

}
