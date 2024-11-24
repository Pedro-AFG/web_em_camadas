package com.empresa.dao;

import jakarta.annotation.PreDestroy;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Data
@Component
@Configuration
public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://junction.proxy.rlwy.net:35998/railway";
    private static final String USER = "postgres";
    private static final String PASSWORD = "jmSHpuvjwYPOxdJybaSSDpnufwKFEdal";

    private Connection connection;

    @Bean
    public Connection connectDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados estabelecida!");
                criarTabelas(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void criarTabelas(Connection connection) {
        String createCursoTable = """
            CREATE TABLE IF NOT EXISTS curso (
                id BIGINT PRIMARY KEY,
                nome VARCHAR(255) NOT NULL
            )
            """;

        String createAlunoTable = """
            CREATE TABLE IF NOT EXISTS aluno (
                matricula BIGINT PRIMARY KEY,
                nome VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                curso_id BIGINT,
                FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE SET NULL
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createCursoTable);
            stmt.execute(createAlunoTable);
            System.out.println("Tabelas criadas ou já existentes!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
