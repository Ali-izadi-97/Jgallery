package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DAO.*;
import model.Logic.ArtWork;
import model.Logic.Artist;
import model.Logic.Genre;
import model.Logic.Type;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ManagerController implements Initializable {
    @FXML
    AnchorPane artworkPane, artistPane, genrePane, typePane, eventPane, mainPane, manageEventsPane, showDetailOfEvent;
    @FXML
    JFXTextField txtGenreName, txtTypeName, txtArtistName, txtArtistFamily, txtArtworkName, txtArtworkPrice;
    @FXML
    JFXTextArea txtDescription;
    @FXML
    JFXComboBox comboxAge, comboBoxGenre, comboBoxType, comboBoxArtist;
    @FXML
    TableColumn<?, String> genre_col_name, type_col_name, artist_col_id, artist_col_name, artist_col_family,
            artist_col_age, artwork_col_id, artwork_col_name, artwork_col_artist,
            artwork_col_genre, artwork_col_type, artwork_col_price, artwork_col_state, artwork_col_desc;
    @FXML
    TableView<Genre> genreTable;
    @FXML
    TableView<Type> typeTable;
    @FXML
    TableView<Artist> artistTable;
    @FXML
    TableView<ArtWork> artworkTable;
    @FXML
    ImageView imageIv;

    private GenreDAO genreDAO;
    private TypeDAO typeDAO;
    private ArtistDAO artistDAO;
    private ArtWorkDAO artWorkDAO;

    private ObservableList<Genre> genreList = FXCollections.observableArrayList();
    private ObservableList<Type> typeList = FXCollections.observableArrayList();
    private ObservableList<Artist> artistList = FXCollections.observableArrayList();
    private ObservableList<ArtWork> artWorklist = FXCollections.observableArrayList();

    private DataBase db = DataBase.getConnection("root", "admin", "agm");
    private Connection connection = db.connect();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPane.getChildren().stream().filter(Pane -> !(Pane instanceof VBox)).forEach(node -> {
            node.setVisible(false);
            node.setDisable(true);
        });
//        Image image = new Image("images (1).png");
//        imageIv.setImage(image);


    }

    private void disableCurentPane() {
        for (int i = 0; i < mainPane.getChildren().size(); i++) {
            if (mainPane.getChildren().get(i) instanceof VBox) {
                continue;
            } else {
                if (mainPane.getChildren().get(i).isVisible()) {
                    mainPane.getChildren().get(i).setVisible(false);
                    mainPane.getChildren().get(i).setDisable(false);
                }
            }

        }
    }

    public void manageArtist(ActionEvent actionEvent) {
        disableCurentPane();
        artistPane.setVisible(true);
        artistPane.setDisable(false);
        if (artistDAO == null) {
            initArtistTable();
            artistDAO = new ArtistDAO(connection);
            artistList.addAll(artistDAO.findAllArtist());
            artistTable.setItems(artistList);
        } else {
            artistTable.setItems(artistList);

        }
        for (int i = 20; i < 90; i++) {
            ObservableList<Integer> ages = FXCollections.observableArrayList();
            ages.add(i);
            comboxAge.getItems().addAll(ages);
        }
    }


    public void showEvents(ActionEvent actionEvent) {
        disableCurentPane();
        eventPane.setVisible(true);
        eventPane.setDisable(false);

    }

    public void manageEvents(ActionEvent actionEvent) {
        disableCurentPane();
        manageEventsPane.setVisible(true);
        manageEventsPane.setDisable(false);


    }

    public void showDetails(ActionEvent actionEvent) {
        disableCurentPane();
        showDetailOfEvent.setVisible(true);
        showDetailOfEvent.setDisable(false);
    }

    public void manageArtwork(ActionEvent actionEvent) {
        manageArtist(actionEvent);
        manageGenre(actionEvent);
        manageType(actionEvent);
        disableCurentPane();
        artworkPane.setVisible(true);
        artworkPane.setDisable(false);
        if (artWorkDAO == null) {
            initArtWorkTable();
            artWorkDAO = new ArtWorkDAO(connection);
            artWorklist.addAll(artWorkDAO.findAllArtworks());
            artworkTable.setItems(artWorklist);
            comboBoxArtist.getItems().setAll(artistList.stream().map(x -> x.getFirstname() + " " + x.getLastname() + "(" + x.getID() + ")").collect(Collectors.toList()));
            comboBoxGenre.getItems().setAll(genreList.stream().map(Genre::getName).collect(Collectors.toList()));
            comboBoxType.getItems().setAll(typeList.stream().map(Type::getName).collect(Collectors.toList()));

        } else {
            artworkTable.setItems(artWorklist);
            comboBoxArtist.getItems().setAll(artistList.stream().map(x -> x.getFirstname() + " " + x.getLastname() + "(" + x.getID() + ")").collect(Collectors.toList()));
            comboBoxGenre.getItems().setAll(genreList.stream().map(Genre::getName).collect(Collectors.toList()));
            comboBoxType.getItems().setAll(typeList.stream().map(Type::getName).collect(Collectors.toList()));

        }

    }

    public void manageGenre(ActionEvent actionEvent) {
        disableCurentPane();
        genrePane.setVisible(true);
        genrePane.setDisable(false);
        if (genreDAO == null) {
            initGenreTable();
            genreDAO = new GenreDAO(connection);
            genreList.addAll(genreDAO.findAllGenres());
            genreTable.setItems(genreList);
        } else {
            genreTable.setItems(genreList);

        }


    }

    public void manageType(ActionEvent actionEvent) {
        disableCurentPane();
        typePane.setVisible(true);
        typePane.setDisable(false);
        if (typeDAO == null) {
            initTypeTable();
            typeDAO = new TypeDAO(connection);
            typeList.addAll(typeDAO.findAllTypes());
            typeTable.setItems(typeList);
        } else {
            typeTable.setItems(typeList);

        }
    }

    private void initArtistTable() {

        artist_col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        artist_col_name.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        artist_col_family.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        artist_col_age.setCellValueFactory(new PropertyValueFactory<>("age"));

    }

    private void initArtWorkTable() {
        artwork_col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        artwork_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        artwork_col_artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        artwork_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        artwork_col_type.setCellValueFactory(new PropertyValueFactory<>("type_artwork"));
        artwork_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        artwork_col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        artwork_col_state.setCellValueFactory(new PropertyValueFactory<>("check"));
    }

    private void initTypeTable() {
        type_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    private void initGenreTable() {
        genre_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void addType(ActionEvent actionEvent) {
        if (txtTypeName.getText() == null || txtTypeName.getText().length() < 3) {
            //Dialog Box
        } else {
            Type type = new Type(null, txtTypeName.getText());
            typeDAO.addType(type);
            typeList.add(type);


        }
    }

    public void addGenre(ActionEvent actionEvent) {
        if (txtGenreName.getText() == null || txtGenreName.getText().length() < 3) {
            //Dialog Box
        } else {
            Genre genre = new Genre(null, txtGenreName.getText());
            genreDAO.addGenre(genre);
            genreList.add(genre);

        }

    }

    public void addArtist(ActionEvent actionEvent) {
        if (txtArtistName.getText() == null || txtArtistFamily.getText() == null) {

        } else {
            Artist artist = new Artist(null, txtArtistName.getText(), txtArtistFamily.getText(), (Integer) comboxAge.getValue(), null);
            artistDAO.addArtist(artist);
            artistList.removeAll();
            artistTable.getItems().clear();
            artistList.addAll(artistDAO.findAllArtist());
        }
    }

    public void addArtWork(ActionEvent actionEvent) {
        String[] tmpArtist = comboBoxArtist.getValue().toString().split("\\(");
        Integer artistId = Integer.parseInt(tmpArtist[1].replace(")", "").trim());

        String tmpType = comboBoxType.getValue().toString();
        String tmpGenre = comboBoxGenre.getValue().toString();
        String name = txtArtworkName.getText();
        Double price = Double.parseDouble(txtArtworkPrice.getText());
        String desc = txtDescription.getText();


        Artist artist = artistDAO.find_from_Id(artistId);
        Genre genre = genreDAO.find_form_Name(tmpGenre);
        Type type = typeDAO.find_form_Name(tmpType);

        ArtWork artWork = new ArtWork(null, artist, name, desc, price, type, genre, null, null);
        System.out.println(artWork.getGenre().getName() + " " + artWork.getType_artwork().getName());
//        System.out.println(artWork);
        artWorkDAO.addartwork(artWork);
        artWorklist.removeAll();
        artworkTable.getItems().clear();
        artWorklist.addAll(artWorkDAO.findAllArtworks());

    }

    public void deleteGenre(ActionEvent actionEvent) {
        genreDAO.removeGenre(genreTable.getSelectionModel().getSelectedItem().getName());
        genreList.remove(genreTable.getSelectionModel().getSelectedItem());
    }

    public void deleteType(ActionEvent actionEvent) {
        typeDAO.removeType(typeTable.getSelectionModel().getSelectedItem().getName());
        typeList.remove(typeTable.getSelectionModel().getSelectedItem());
    }

    public void deleteArtistTbale(ActionEvent actionEvent) {
        artistDAO.removeArtist(artistTable.getSelectionModel().getSelectedItem().getID());
        artistList.remove(artistTable.getSelectionModel().getSelectedItem());
    }

    public void editGenre(ActionEvent actionEvent) {

        Genre genre = new Genre(genreTable.getSelectionModel().getSelectedItem().getId(), txtGenreName.getText());
        genreDAO.editGenre(genre);
        genreList.removeAll();
        genreTable.getItems().clear();
        genreList.addAll(genreDAO.findAllGenres());

    }

    public void editType(ActionEvent actionEvent) {

        Type type = new Type(typeTable.getSelectionModel().getSelectedItem().getId(), txtTypeName.getText());
        typeDAO.editType(type);
        typeList.removeAll();
        typeTable.getItems().clear();
        typeList.addAll(typeDAO.findAllTypes());
    }

    public void editArtist(ActionEvent actionEvent) {
        Artist artist = new Artist(artistTable.getSelectionModel().getSelectedItem().getID(),
                txtArtistName.getText(),
                txtArtistFamily.getText(),
                Integer.valueOf(comboxAge.getValue().toString()),
                null);
        artistDAO.editAtrist(artist);
        artistList.removeAll();
        artistTable.getItems().clear();
        artistList.addAll(artistDAO.findAllArtist());

    }

    public void GenreTableListener(MouseEvent mouseEvent) {
        if (genreTable.getSelectionModel().getSelectedItem() == null) {


        } else {
            txtGenreName.setText(genreTable.getSelectionModel().getSelectedItem().getName());
        }
    }

    public void ArtistTableListener(MouseEvent mouseEvent) {
        if (artistTable.getSelectionModel().getSelectedItem() == null) {

        } else {
            txtArtistName.setText(artistTable.getSelectionModel().getSelectedItem().getFirstname());
            txtArtistFamily.setText(artistTable.getSelectionModel().getSelectedItem().getLastname());
            comboxAge.setValue(artistTable.getSelectionModel().getSelectedItem().getAge());
        }
    }

    public void typeTableListener(MouseEvent mouseEvent) {
        if (typeTable.getSelectionModel().getSelectedItem() == null) {


        } else {
            txtTypeName.setText(typeTable.getSelectionModel().getSelectedItem().getName());
        }
    }


    public void editArtwork(ActionEvent actionEvent) {


    }


}
