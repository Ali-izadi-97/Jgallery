package model.DAO;

import model.Logic.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreDAO {
    Genre genre;
    private final Connection connection;

    static enum GenreStatement {
        FIND_ALL,
        FIND_BY_Name,
        ADD,
        EDIT,
        REMOVE
    }

    private Map<GenreStatement, PreparedStatement> statements = new HashMap<>();

    public GenreDAO(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException();
        }
        this.connection = connection;

        prepareStatements();
    }

    private void prepareStatements() {
        try {
            PreparedStatement findAllStatement = this.connection.prepareStatement(
                    "select * from genre"
            );
            statements.put(GenreStatement.FIND_ALL, findAllStatement);
            statements.put(GenreStatement.FIND_BY_Name, this.connection.prepareStatement(
                    "select * from genre where name = ?"
            ));
            statements.put(GenreStatement.ADD, this.connection.prepareStatement(
                    "insert into genre values (null,?)"
            ));
            statements.put(GenreStatement.EDIT, this.connection.prepareStatement(
                    "update genre set name = ? where id=?"
            ));
            statements.put(GenreStatement.REMOVE, this.connection.prepareStatement(
                    "delete from genre where name = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int addGenre(Genre genre) {
        PreparedStatement addGenreStatement = statements.get(GenreStatement.ADD);
        try {
            addGenreStatement.setString(1, genre.getName());
            return addGenreStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int editGenre(Genre genre) {
        PreparedStatement editGenreStatement = statements.get(GenreStatement.EDIT);
        try {
            editGenreStatement.setString(1, genre.getName());
            editGenreStatement.setInt(2, genre.getId());
            return editGenreStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int removeGenre(String name) {
        try {
            statements.get(GenreStatement.REMOVE).setString(1, name);
            return statements.get(GenreStatement.REMOVE).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Genre> findAllGenres() {
        //throw new UnsupportedOperationException();
        final List<Genre> genres = new ArrayList<>();
        return fetchGenres(genres, statements.get(GenreStatement.FIND_ALL));
    }

    private List<Genre> fetchGenres(List<Genre> genres, PreparedStatement findByName) {
        try (ResultSet resultSet = findByName.executeQuery()) {
            while (resultSet.next()) {
                Genre genre = new Genre(resultSet.getInt(1), resultSet.getString(2));
                genres.add(genre);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return genres;
    }

    public Genre find_from_id(Integer integer){
        try (ResultSet resultSet = statements.get(GenreStatement.FIND_ALL).executeQuery()) {
            while (resultSet.next()) {
                if (resultSet.getInt(1) == integer) {
                    Genre genre = new Genre(resultSet.getInt(1), resultSet.getString(2));
                    return genre;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }


    public Genre find_form_Name(String name) {
        try (ResultSet resultSet = statements.get(GenreStatement.FIND_ALL).executeQuery()) {
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(name)) {
                    Genre genre = new Genre(resultSet.getInt(1), resultSet.getString(2));
                    return genre;
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
