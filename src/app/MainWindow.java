package app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import app.utils.BadWords;
import app.utils.ResizeHelper;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Grim
 */
public class MainWindow extends Application {

    public static ResizeHelper resizeHelper;
    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        BadWords.loadConfigs();

        primaryStage.getIcons().add(new Image("app/images/app-icon.png"));
        mainStage = primaryStage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/MainWindow.fxml"));

            Scene scene = new Scene(root);

            primaryStage.setTitle("Khedemti");
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setMinHeight(555);
            primaryStage.setMinWidth(1000);
            primaryStage.setHeight(800);

            primaryStage.setScene(scene);

            resizeHelper = new ResizeHelper(primaryStage, 0, 20);

            primaryStage.show();

        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
