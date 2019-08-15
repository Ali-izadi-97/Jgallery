package model.DAO;

import model.Logic.ArtWork;
import model.Logic.Artist;
import model.Logic.Genre;
import model.Logic.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtWorkDAO {
    private final Connection connection;

    static enum artWorkStatement {
        FIND_ALL,
        ADD,
        EDIT,
        REMOVE
    }

    private Map<artWorkStatement, PreparedStatement> statements = new HashMap<>();

    public ArtWorkDAO(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException();
        }
        this.connection = connection;

        prepareStatements();
    }

    private void prepareStatements() {
        try {
            PreparedStatement findAllStatement = this.connection.prepareStatement(
                    "select * from artwork"
            );
            statements.put(artWorkStatement.FIND_ALL, findAllStatement);

            statements.put(artWorkStatement.ADD, this.connection.prepareStatement(
                    "insert into artwork values (null, ?, ?, ?, ?, ?, ?, ?, ?)"
            ));
            statements.put(artWorkStatement.EDIT, this.connection.prepareStatement(
                    "update artwork set artist_id = ?, name = ?, type_id = ?, genre_id = ?, description = ? , price = ? , address = ? , where id = ?"
            ));
            statements.put(artWorkStatement.REMOVE, this.connection.prepareStatement(
                    "delete from atrwork where id = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int addartwork(ArtWork artWork) {
        PreparedStatement addArtWorkStatement = statements.get(artWorkStatement.ADD);
        return statement_util(addArtWorkStatement, artWork);

    }

    private int statement_util(PreparedStatement preparedStatement, ArtWork artWork) {
        try {
            preparedStatement.setInt(1, artWork.getArtist().getID());
            preparedStatement.setString(2, artWork.getName());
            preparedStatement.setInt(3, artWork.getType_artwork().getId());
            preparedStatement.setInt(4, artWork.getGenre().getId());
            preparedStatement.setString(5, artWork.getDescription());
            preparedStatement.setDouble(6, artWork.getPrice());
            preparedStatement.setString(7, artWork.getAddress());
            preparedStatement.setInt(8, 0);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public int editartwork(ArtWork artWork) {
        PreparedStatement editBookStatement = statements.get(artWorkStatement.EDIT);
        return statement_util(editBookStatement, artWork);
    }

    public int removeArtWork(Integer id) {
        try {
            statements.get(artWorkStatement.REMOVE).setInt(1, id);
            return statements.get(artWorkStatement.REMOVE).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ArtWork> findAllArtworks() {
        final List<ArtWork> artorksList = new ArrayList<>();
        PreparedStatement preparedStatement = statements.get(artWorkStatement.FIND_ALL);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id_artwork = resultSet.getInt(1);

                ArtistDAO artist = new ArtistDAO(connection);
                Artist artistObj = artist.find_from_Id(resultSet.getInt(2));

                String name = resultSet.getString(3);

                TypeDAO typeDAO = new TypeDAO(connection);
                Type typeObj = typeDAO.find_from_id(resultSet.getInt(4));
//                Type typeObj = new Type(45, "mashang");
//                System.out.println(resultSet.getString(4));

                GenreDAO genreDAO = new GenreDAO(connection);
                Genre genreObj = genreDAO.find_from_id((resultSet.getInt(5)));
//                System.out.println(resultSet.getString(5));
//                Genre genreObj = new Genre(23, "tanbal");

                String description = resultSet.getString(6);

                Double price = resultSet.getDouble(7);
                Integer check = resultSet.getInt(9);
                ArtWork artWork = new ArtWork(id_artwork, artistObj, name, description, price, typeObj, genreObj, null, check);
                artorksList.add(artWork);
//                System.out.println(artWork.getCheck());
            }
            return artorksList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

//        return fetchArtWorks(artWorks, statements.get(artWorkStatement.FIND_ALL));
    }

//    private List<ArtWork> fetchArtWorks(List<ArtWork> artWorks, PreparedStatement findAll) {
//        try (ResultSet resultSet = findAll.executeQuery()){
//            while (resultSet.next()) {
//                ArrayList arrayList = new ArrayList();
//                arrayList.add(resultSet.getString(1));
//                ArtistDao artistDao = new ArtistDao();
//                arrayList.add(artistDao.findformID((resultSet.getInt(2))));
//                arrayList.add(resultSet.getString(3));
//                arrayList.add(resultSet.getDouble(4));
//                TypeDAO typeDAO = new TypeDAO();
//                typeDAO.findformName(resultSet.getString(5));
//                GenreDAO genreDAO = new GenreDAO(connection);
//                arrayList.add(genreDAO.find_form_Name(resultSet.getString(6)));
//                arrayList.add(resultSet.getString(7));
//                ArtWork artWork = new ArtWork(
//                        (Artist)arrayList.get(1),
//                        (String) arrayList.get(0),
//                        (String)arrayList.get(2),
//                        (Double)arrayList.get(3),
//                        (Type)arrayList.get(4),
//                        (Genre)arrayList.get(5),
//                        (String)arrayList.get(6)
//                );
//
//                artWorks.add(artWork);
//            }
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//
//        return artWorks;
//    }
}


