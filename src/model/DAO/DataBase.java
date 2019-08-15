package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    private Connection connection = null;
    private static DataBase baseConnection = null;

    private DataBase(String u, String p, String n) {
        String url = "jdbc:mysql://localhost:3306/";
        try {
            connection = DriverManager.getConnection(url, u, p);
            System.out.println("Active Connection!");
            try {
                Statement statement = connection.createStatement();
                int sqlQueryResult = statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + n);
                if (sqlQueryResult != 0) {
                    System.out.println("New Database Created!");
                } else {
                    System.out.println("Database Exist");
                }
                connection = DriverManager.getConnection(url + n, u, p);
            } catch (Exception ex) {
                System.out.println("Error in Statement " + ex.getClass());
            }
        } catch (SQLException sql) {
            System.out.println("Connection Failed!");
        }

    }

    public Connection connect() {
        return connection;
    }

    public static DataBase getConnection(String username, String password, String databaseName) {
        if (baseConnection == null) {
            baseConnection = new DataBase(username, password, databaseName);
        }
        return baseConnection;
    }
//
//    public static void main(String[] args) {
//        Database db = Database.getConnection("root", "root", "agm");
//        Connection connection = db.connect();
//        System.out.println(connection);
//
//        Artist artist = new Artist(14, "fnfnfnf", "en", 34, null);
//        ArtistDAO artistDAO = new ArtistDAO(connection);


//        artistDAO.findAllArtist().forEach(x -> System.out.println(x));
//        System.out.println(artistDAO.find_form_Name("fnfnfnf", "en"));
//        Type type = new Type("toole");
//        TypeDAO typeDAO = new TypeDAO(connection);
//        System.out.println(typeDAO.find_form_Name("tt1"));
//        typeDAO.addType(type);
//        List<Type> types = typeDAO.findAllTypes();
//        types.forEach(x -> System.out.println(x.getName()));
//        Genre genre = new Genre("jooo");
//        GenreDAO genreDAO = new GenreDAO(connection);
//        System.out.println(genreDAO.find_form_Name("g3"));
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM genre");
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        genreDAO.addGenre(genre);
//        List<Genre> genres =  genreDAO.findAllBooks();

//        try {
//            CSVMaker csvMaker = new CSVMaker(connection, "artists");
//        } catch (SQLException e) {
//            System.out.println("naboood");
//        }

}
