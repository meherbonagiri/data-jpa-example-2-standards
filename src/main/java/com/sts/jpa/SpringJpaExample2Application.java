package com.sts.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration(exclude = DataJpaRepositoriesAutoConfiguration.class)
@EnableJpaRepositories("com.sts.jpa.repository")
@EntityScan("com.sts.jpa.entity")
@ComponentScan("com.sts.jpa")
public class SpringJpaExample2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaExample2Application.class, args);
	}

}
