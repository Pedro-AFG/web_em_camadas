package com.empresa.dao;

import com.empresa.model.Aluno;
import com.empresa.model.Curso;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoDao implements DAO<Aluno> {
    private static final String INSERT = "INSERT INTO aluno (matricula, nome, email, curso_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE aluno SET nome = ?, email = ?, curso_id = ? WHERE matricula = ?";
    private static final String DELETE = "DELETE FROM aluno WHERE matricula = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM aluno WHERE matricula = ?";
    private static final String SELECT_ALL = "SELECT * FROM aluno";

    private Connection connection;

    public AlunoDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Aluno insert(Aluno aluno) {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT)) {
            stmt.setLong(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getEmail());
            stmt.setLong(4, aluno.getCurso().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aluno;
    }

    @Override
    public Aluno update(Aluno aluno) {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setLong(3, aluno.getCurso().getId());
            stmt.setLong(4, aluno.getMatricula());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aluno;
    }

    @Override
    public void delete(long matricula) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            stmt.setLong(1, matricula);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Aluno não encontrado para remoção.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Aluno getById(long matricula) {
        Aluno aluno = null;
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setLong(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Curso curso = new CursoDao(connection).getById(rs.getLong("curso_id"));
                aluno = new Aluno(
                        rs.getLong("matricula"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        curso
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aluno;
    }

    @Override
    public List<Aluno> getAll() {
        List<Aluno> alunos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Curso curso = new CursoDao(connection).getById(rs.getLong("curso_id"));
                Aluno aluno = new Aluno(
                        rs.getLong("matricula"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        curso
                );
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }
}
