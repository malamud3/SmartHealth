package superapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import superapp.data.subEntity.IngridientEntity;
import superapp.logic.utilitys.SpoonaculerUtility;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
    }

}
