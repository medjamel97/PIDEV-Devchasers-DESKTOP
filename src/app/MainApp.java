package app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import app.entity.User;
import app.utils.BadWords;
import app.utils.ResizeHelper;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Grim
 */
public class MainApp extends Application {

    public static ResizeHelper resizeHelper;
    public static Stage mainStage;

    private static MainApp instance;
    private static User session;

    public static MainApp getInstance() {
        if (instance == null) {
            instance = new MainApp();
        }
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Chargements des mots a filtrer ..");
        BadWords.loadConfigs();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        mainStage = primaryStage;

        loadConnexion();

    }

    private void loadScene(String fxmlLink, String title, int width, int height, boolean isAuthentification) {
        try {
            Stage primaryStage = mainStage;
            primaryStage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlLink)));
            scene.setFill(Color.TRANSPARENT);

            primaryStage.getIcons().add(new Image("app/images/app-icon.png"));
            primaryStage.setTitle(title);
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
            primaryStage.setMinWidth(width);
            primaryStage.setMinHeight(height);
            primaryStage.setScene(scene);
            primaryStage.setX((Screen.getPrimary().getBounds().getWidth() / 2) - (width / 2));
            primaryStage.setY((Screen.getPrimary().getBounds().getHeight() / 2) - (height / 2));

            if (!isAuthentification) {
                resizeHelper = new ResizeHelper(primaryStage, 0, 45);
            }
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void loadConnexion() {
        loadScene(
                "/app/gui/Connexion.fxml",
                "Connexion",
                550,
                650,
                true
        );
    }

    public void loadInscriptionCandidat() {
        loadScene(
                "/app/gui/InscriptionCandidat.fxml",
                "Connexion",
                750,
                750,
                true
        );
    }

    public void loadInscriptionSociete() {
        loadScene(
                "/app/gui/InscriptionSociete.fxml",
                "Connexion",
                750,
                750,
                true
        );
    }

    public void connexion(String role) {
        System.out.println("Connexion en tant que " + role);
        String title = "Khedemti - Visiteur";
        if (role.equals("ROLE_SOCIETE")) {
            title = "Espace societe";
        }
        if (role.equals("ROLE_ADMIN")) {
            title = "Espace admin";
        }
        if (role.equals("ROLE_CANDIDAT")) {
            title = "Khedemti";
        }

        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_SOCIETE")) {
            loadScene(
                    "/app/gui/back_end/MainWindow.fxml",
                    title,
                    1200,
                    800,
                    false
            );
        } else {
            loadScene(
                    "/app/gui/front_end/MainWindow.fxml",
                    title,
                    1300,
                    800,
                    false
            );
        }
    }

    public void deconnexion() {
        session = null;
        System.out.println("Deconnexion ..");
        loadConnexion();
    }

    public static User getSession() {
        return session;
    }

    public static void setSession(User session) {
        MainApp.session = session;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
