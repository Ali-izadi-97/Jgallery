package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.sun.javafx.application.LauncherImpl;
import javafx.stage.StageStyle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Manager/ManagerView.fxml"));
        primaryStage.setTitle("Art Gallery");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
