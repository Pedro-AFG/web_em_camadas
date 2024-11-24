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
public class CursoDao implements DAO<Curso> {
    private static final String INSERT = "INSERT INTO curso (id, nome) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE curso SET nome = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM curso WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM curso WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM curso";
    private static final String SELECT_ALUNOS_BY_CURSO_ID = "SELECT * FROM aluno WHERE curso_id = ?";

    private Connection connection;

    public CursoDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Curso curso) {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT)) {
            stmt.setLong(1, curso.getId());
            stmt.setString(2, curso.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Curso curso) {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setString(1, curso.getNome());
            stmt.setLong(2, curso.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Curso getById(long id) {
        Curso curso = null;
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                curso = new Curso(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        buscarAlunosPorCursoId(id)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return curso;
    }

    @Override
    public List<Curso> getAll() {
        List<Curso> cursos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        buscarAlunosPorCursoId(rs.getLong("id"))
                );
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    private List<Aluno> buscarAlunosPorCursoId(long cursoId) {
        List<Aluno> alunos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALUNOS_BY_CURSO_ID)) {
            stmt.setLong(1, cursoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getLong("matricula"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        null
                );
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }
}
