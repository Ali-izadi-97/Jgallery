package controller;

import animatefx.animation.BounceIn;
import animatefx.animation.BounceOut;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.DAO.ArtWorkDAO;
import model.DAO.ArtistDAO;
import model.DAO.DataBase;
import model.Logic.ArtWork;
import model.Logic.Artist;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VisitorController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    ImageView imageView;
    @FXML
    Button nextbtn, prevbtn;

    @FXML
    TextArea descriptionArea;
    @FXML
    Label artistName, artistAge, price;


    private DataBase db = DataBase.getConnection("root", "root", "agm");
    private Connection connection = db.connect();

    List<ArtWork> list;
    Integer lenList;
    Integer current = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadImage();
        current = current + 1;
        handleAction(list.get(current));
    }


    public void loadImage() {
        ArtWorkDAO artWorkDAO = new ArtWorkDAO(connection);
        list = artWorkDAO.findAllArtworks();
        lenList = list.size() - 1;
    }


    public void prevButton() {
        current = current + 1;
        if (current <= lenList && current >= 0) {
            handleAction(list.get(current));
        } else {
            current = 0;
            handleAction(list.get(current));
        }
    }

    public void nextButton() {
        current = current - 1;
        if (current >= 0) {
            handleAction(list.get(current));
        } else {
            current = lenList;
            handleAction(list.get(current));
        }
    }

    private void handleAction(ArtWork artWork)
    {
        ArtistDAO artistDAO = new ArtistDAO(connection);
        Artist a = artistDAO.find_from_Id(artWork.getArtist().getID());

        artistAge.setText(a.getAge().toString());
        artistName.setText(a.getFirstname() + " " + a.getLastname());

        price.setText(artWork.getPrice().toString());

        descriptionArea.setText(artWork.getDescription());

        File file = new File(artWork.getAddress());
//        System.out.println(artWork.getAddress());
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }
}
