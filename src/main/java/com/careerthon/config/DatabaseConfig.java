package com.careerthon.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Value("${spring.datasource.url:}")
    private String springDatasourceUrl;

    @Value("${spring.datasource.username:postgres}")
    private String springUsername;

    @Value("${spring.datasource.password:postgres}")
    private String springPassword;

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        HikariConfig config = new HikariConfig();
        
        // If Render DATABASE_URL is provided, safely parse it
        if (databaseUrl != null && !databaseUrl.trim().isEmpty() && databaseUrl.startsWith("postgres")) {
            URI dbUri = new URI(databaseUrl);
            
            String username = "postgres";
            String password = "";
            if (dbUri.getUserInfo() != null) {
                String[] userInfo = dbUri.getUserInfo().split(":");
                username = userInfo[0];
                if (userInfo.length > 1) {
                    password = userInfo[1];
                }
            }
            
            String port = dbUri.getPort() != -1 ? ":" + dbUri.getPort() : "";
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + port + dbUri.getPath();

            config.setJdbcUrl(dbUrl);
            config.setUsername(username);
            config.setPassword(password);
        } else {
            // Fallback to local properties if no Render URL
            String fallbackUrl = springDatasourceUrl.isEmpty() ? "jdbc:postgresql://localhost:5432/careerthon" : springDatasourceUrl;
            config.setJdbcUrl(fallbackUrl);
            config.setUsername(springUsername);
            config.setPassword(springPassword);
        }
        
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10); // Standard config
        return new HikariDataSource(config);
    }
}
