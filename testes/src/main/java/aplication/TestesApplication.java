package aplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.implantodontia")
class TestesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestesApplication.class, args);
    }

}
