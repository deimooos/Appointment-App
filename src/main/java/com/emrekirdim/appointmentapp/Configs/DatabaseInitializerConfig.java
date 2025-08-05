package com.emrekirdim.appointmentapp.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Configuration
public class DatabaseInitializerConfig {

    @Value("${spring.datasource.url}")
    private String postgresUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${app.database.name}")
    private String targetDatabase;

    @PostConstruct
    public void createDatabaseIfNotExists() {
        try (Connection connection = DriverManager.getConnection(postgresUrl, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT 1 FROM pg_database WHERE datname = '" + targetDatabase + "'");

            if (!resultSet.next()) {
                statement.executeUpdate("CREATE DATABASE " + targetDatabase);
                System.out.println("✅ Database '" + targetDatabase + "' has been successfully created.");
            } else {
                System.out.println("ℹ️ Database already exists: " + targetDatabase);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create database: " + targetDatabase, e);
        }
    }
}
