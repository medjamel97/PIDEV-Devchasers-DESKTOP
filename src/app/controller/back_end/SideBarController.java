/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.MainApp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class SideBarController implements Initializable {

    private Button[] adminButtons;

    private Button[] societeButtons;

    @FXML
    private VBox buttonsContainer;
    @FXML
    private VBox bottomContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (MainApp.getSession().getRoles().equals("ROLE_ADMIN")) {
            adminButtons = new Button[]{
                makeButton("Candidats", "/app/gui/back_end/candidat/AfficherToutCandidat.fxml"),
                makeButton("Categories", "/app/gui/back_end/societe/offre_de_travail/categorie/AfficherTout.fxml")
            };
            buttonsContainer.getChildren().addAll(adminButtons);
        } else if (MainApp.getSession().getRoles().equals("ROLE_SOCIETE")) {
            societeButtons = new Button[]{
                makeButton("Mes candidats", "/app/gui/back_end/candidat/AfficherMesCandidats.fxml"),
                makeButton("Mes offres", "/app/gui/back_end/societe/offre_de_travail/AfficherMesOffres.fxml"),
                makeButton("Mes missions", "/app/gui/back_end/societe/mission/AfficherMesMissions.fxml"),
                makeButton("Mes evenements", "/app/gui/back_end/societe/evenement/AfficherMesEvenements.fxml"),
                makeButton("Mes formations", "/app/gui/back_end/societe/formation/AfficherMesFormations.fxml")
            };
            buttonsContainer.getChildren().addAll(societeButtons);
        }

        Button boutonDeconnexion = makeButton("Deconnexion", null);
        boutonDeconnexion.setOnAction((event) -> {
            MainApp.getInstance().deconnexion();
        });
        bottomContainer.getChildren().add(boutonDeconnexion);
    }

    private Button makeButton(String text, String link) {
        Button button = new Button();
        button.getStyleClass().add("link-button-blue");
        button.setTextFill(Color.WHITE);
        button.setText(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPadding(new Insets(25, 25, 25, 25));
        button.setOnAction((event) -> {
            MainWindowController.chargerInterface(
                    getClass().getResource(link)
            );
        });
        return button;
    }
}
