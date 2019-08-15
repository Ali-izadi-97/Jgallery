package model.DAO;

import model.Logic.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ArtistDAO {
    //    Atrist Atrist;
    private final Connection connection;

    static enum ArtistStatement {
        FIND_ALL,
        FIND_BY_ID,
        ADD,
        EDIT,
        REMOVE,
        FIND_BY_NAME
    }

    private Map<ArtistStatement, PreparedStatement> statements = new HashMap<>();

    public ArtistDAO(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException();
        }
        this.connection = connection;

        prepareStatements();
    }

    private void prepareStatements() {
        try {
            PreparedStatement findAllStatement = this.connection.prepareStatement(
                    "select * from artist"
            );
            statements.put(ArtistStatement.FIND_ALL, findAllStatement);
//            statements.put(ArtistStatement.FIND_BY_Name, this.connection.prepareStatement(
//                    "select * from artist where fname = ? AND lname = ?"
//            ));
            statements.put(ArtistStatement.FIND_BY_ID, this.connection.prepareStatement(
                    "select * from artist where id = ?"
            ));
            statements.put(ArtistStatement.FIND_BY_NAME, this.connection.prepareStatement(
                    "select * from artist where name = ?"
            ));

            statements.put(ArtistStatement.ADD, this.connection.prepareStatement(
                    "insert into artist values (null , ?, ?, ?, ?)"
            ));
            statements.put(ArtistStatement.EDIT, this.connection.prepareStatement(
                    "update artist set fname = ?, lname = ?, age = ?, image_src = ?  where id = ?"
            ));
            statements.put(ArtistStatement.REMOVE, this.connection.prepareStatement(
                    "delete from artist where id = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int addArtist(Artist artist) {
        PreparedStatement addArtistStatement = statements.get(ArtistStatement.ADD);
        try {

            addArtistStatement.setString(1, artist.getFirstname());
            addArtistStatement.setString(2, artist.getLastname());
            addArtistStatement.setInt(3, artist.getAge());
            addArtistStatement.setString(4, artist.getPicturePath());
            return addArtistStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int editAtrist(Artist artist) {
        PreparedStatement editAtristStatement = statements.get(ArtistStatement.EDIT);
        try {
            editAtristStatement.setString(1, artist.getFirstname());
            editAtristStatement.setString(2, artist.getLastname());
            editAtristStatement.setInt(3, artist.getAge());
            editAtristStatement.setString(4, artist.getPicturePath());
            editAtristStatement.setInt(5, artist.getID());
            return editAtristStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int removeArtist(Integer id) {
        try {
            statements.get(ArtistStatement.REMOVE).setInt(1, id);
            return statements.get(ArtistStatement.REMOVE).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Artist> findAllArtist() {
        final List<Artist> artists = new ArrayList<>();
        PreparedStatement preparedStatement = statements.get(ArtistStatement.FIND_ALL);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Artist artist = new Artist(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5)
                );
                artists.add(artist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artists;
    }

//    private List<Artist> fetchAtrists(List<Artist> artists, PreparedStatement findByName) {
//        try (ResultSet resultSet = findByName.executeQuery()) {
//            while (resultSet.next()) {
//                Artist Atrist = new Artist(resultSet.);
//                Atrists.add(Atrist);
//            }
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//        return Atrists;
//    }

    public Artist find_from_Id(Integer id) {
        try {
            PreparedStatement findAtristStatement = statements.get(ArtistStatement.FIND_BY_ID);
            findAtristStatement.setInt(1, id);

            ResultSet resultSet = findAtristStatement.executeQuery();
            while (resultSet.next()) {
                return new Artist(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getString(5));
            }
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


//        public Artist find_form_Name(String fname, String lname) {
//        try {
//            PreparedStatement findAtristStatement = statements.get(ArtistStatement.FIND_BY_Name);
//            findAtristStatement.setString(1, fname);
//            findAtristStatement.setString(2, lname);
//
//            ResultSet resultSet = findAtristStatement.executeQuery();
//            while (resultSet.next()) {
//                return new Artist(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
//                        resultSet.getInt(4), resultSet.getString(5));
//            }
//            return null;
//
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
    }
}

