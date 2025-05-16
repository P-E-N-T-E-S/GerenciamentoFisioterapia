package com.implantodontia.apresentacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.implantodontia.apresentacao",
        "com.implantodontia.dominio",
        "com.implantodontia.infraestrutura"})
@EnableScheduling
public class ApresentacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApresentacaoApplication.class, args);
    }

}
