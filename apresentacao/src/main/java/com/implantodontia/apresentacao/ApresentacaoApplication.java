package com.implantodontia.apresentacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.implantodontia.apresentacao",
        "com.implantodontia.dominio",
        "com.implantodontia.infraestrutura"})
public class ApresentacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApresentacaoApplication.class, args);
    }

}
