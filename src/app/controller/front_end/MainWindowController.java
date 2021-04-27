/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class MainWindowController implements Initializable {

    static AnchorPane staticContent;

    @FXML
    private AnchorPane topBar;
    @FXML
    private AnchorPane content;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            staticContent = content;

            Parent parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/TopBar.fxml"));
            AnchorPane.setTopAnchor(parent, 0.0);
            AnchorPane.setBottomAnchor(parent, 0.0);
            AnchorPane.setRightAnchor(parent, 0.0);
            AnchorPane.setLeftAnchor(parent, 0.0);
            topBar.getChildren().add(parent);

        } catch (IOException e) {
            System.out.println(e.getMessage() + "/" + e.getCause());
        }

        chargerInterface(getClass().getResource("/app/gui/front_end/candidat/publication/Accueil.fxml"));
    }

    public static void chargerInterface(URL location) {
        staticContent.getChildren().clear();
        if (location == null) {
            System.out.println("Erreur de chargement du contenu (front_end) verifiez l'url du FXML dans votre controller");
        } else {
            System.out.println("Chargement du contenu (front_end) : " + location);
            try {
                Parent parent = FXMLLoader.load(location);
                AnchorPane.setTopAnchor(parent, 0.0);
                AnchorPane.setBottomAnchor(parent, 0.0);
                AnchorPane.setRightAnchor(parent, 0.0);
                AnchorPane.setLeftAnchor(parent, 0.0);
                staticContent.getChildren().add(parent);
            } catch (IOException e) {
                System.out.print("Erreur d'affichage : " + e.getMessage() + " Erreur dans le controlleur");
                e.printStackTrace();
            }
        }
    }
}
