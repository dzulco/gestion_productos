package com.techlab.gestion_productos.component;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class EnvConfig {

    private final Dotenv dotenv = Dotenv.load();

    public String getDbUrl() {
        return dotenv.get("SPRING_DATASOURCE_URL");
    }

    public String getDbUser() {
        return dotenv.get("SPRING_DATASOURCE_USER");
    }

    public String getDbPassword() {
        return dotenv.get("SPRING_DATASOURCE_PASSWORD");
    }
}
