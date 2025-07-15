package com.techlab.gestion_productos.config;

import com.techlab.gestion_productos.component.EnvConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private final EnvConfig env;

    public DataSourceConfig(EnvConfig env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(env.getDbUrl());
        ds.setUsername(env.getDbUser());
        ds.setPassword(env.getDbPassword());
        return ds;
    }
}
