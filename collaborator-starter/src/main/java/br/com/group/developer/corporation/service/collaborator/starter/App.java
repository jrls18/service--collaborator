package br.com.group.developer.corporation.service.collaborator.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan({"br.com.group.developer.corporation","br.com.group.*","br.com.grupo.*"})
@EntityScan("br.com.group.developer.corporation.service.collaborator.jpa.entity")
@EnableJpaRepositories("br.com.group.developer.corporation.service.collaborator.jpa.repository")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
