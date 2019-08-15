package model.DAO;


import model.Logic.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeDAO {
    Type type;
    private final Connection connection;

    static enum TypeStatement {
        FIND_ALL,
        FIND_BY_Name,
        ADD,
        EDIT,
        REMOVE,
        FIND_BY_ID
    }

    private Map<TypeStatement, PreparedStatement> statements = new HashMap<>();

    public TypeDAO(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException();
        }
        this.connection = connection;

        prepareStatements();
    }

    private void prepareStatements() {
        try {
            PreparedStatement findAllStatement = this.connection.prepareStatement(
                    "select * from type"
            );
            statements.put(TypeStatement.FIND_ALL, findAllStatement);
            statements.put(TypeStatement.FIND_BY_Name, this.connection.prepareStatement(
                    "select * from type where name = ?"
            ));
            statements.put(TypeStatement.ADD, this.connection.prepareStatement(
                    "insert into type values (null,?)"
            ));
            statements.put(TypeStatement.EDIT, this.connection.prepareStatement(
                    "update type set name = ? where id=?"
            ));
            statements.put(TypeStatement.REMOVE, this.connection.prepareStatement(
                    "delete from type where name = ?"));
            statements.put(TypeStatement.FIND_BY_ID, this.connection.prepareStatement("SELECT * FROM type WHERE id=?"));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int addType(Type type) {
        PreparedStatement addTypeStatement = statements.get(TypeStatement.ADD);
        try {
            addTypeStatement.setString(1, type.getName());
            return addTypeStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int editType(Type type) {
        PreparedStatement editTypeStatement = statements.get(TypeStatement.EDIT);
        try {
            editTypeStatement.setString(1, type.getName());
            editTypeStatement.setInt(2, type.getId());
            return editTypeStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int removeType(String name) {
        try {
            statements.get(TypeStatement.REMOVE).setString(1, name);
            return statements.get(TypeStatement.REMOVE).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Type> findAllTypes() {
        //throw new UnsupportedOperationException();
        final List<Type> types = new ArrayList<>();
        return fetchTypes(types, statements.get(TypeStatement.FIND_ALL));
    }

    private List<Type> fetchTypes(List<Type> types, PreparedStatement findByName) {
        try (ResultSet resultSet = findByName.executeQuery()) {
            while (resultSet.next()) {
                Type type = new Type(resultSet.getInt(1), resultSet.getString(2));
                types.add(type);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return types;
    }

    public Type find_from_id(Integer id) {
        try (ResultSet resultSet = statements.get(TypeStatement.FIND_ALL).executeQuery()) {
            while (resultSet.next()) {
                if (resultSet.getInt(1) == id) {
                    Type type = new Type(resultSet.getInt(1), resultSet.getString(2));
                    return type;
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public Type find_form_Name(String name) {
        try (ResultSet resultSet = statements.get(TypeStatement.FIND_ALL).executeQuery()) {
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(name)) {
                    Type type = new Type(resultSet.getInt(1), resultSet.getString(2));
                    return type;
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }


//    public Type find_from_id(Integer id) {
////        PreparedStatement findByIdStatement = statements.get(TypeStatement.FIND_BY_ID);
////        try {
////            findByIdStatement.setInt(1, id);
////            ResultSet resultSet = findByIdStatement.executeQuery();
////            while (resultSet.next()) {
////                System.out.println(resultSet.getInt(1));
////                System.out.println(resultSet.getString(2));
////            }
////        } catch (SQLException ex) {
////            throw new RuntimeException(ex);
////        }
////        return null;
//        PreparedStatement findByIdStatement = statements.get(TypeStatement.FIND_ALL);
//        try {
//            ResultSet resultSet = findByIdStatement.executeQuery();
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt(1) + "  " + id);
//                if (resultSet.getInt(1)==id) {
//                    System.out.println("hasss");
//                }
//            }
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//        return null;
//    }
}
