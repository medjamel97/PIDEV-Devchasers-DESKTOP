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
import javafx.scene.Cursor;
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (MainApp.getSession().getRoles().equals("ROLE_ADMIN")) {
            adminButtons = new Button[]{
                makeButton("Candidats", "/app/gui/back_end/candidat/AfficherToutCandidat.fxml", true),
                makeButton("Societes", "/app/gui/back_end/societe/AfficherToutSociete.fxml", true),
                makeButton("Categories", "/app/gui/back_end/societe/offre_de_travail/categorie/AfficherTout.fxml", true),
                makeButton("Evenements", "", true),
                makeButton("Formations", "", true),
                makeButton("Revues", "/app/gui/back_end/societe/offre_de_travail/revue/AfficherTout.fxml", true),
                makeButton("Interviews", "/app/gui/back_end/societe/offre_de_travail/interview/AfficherTout.fxml", true)
            };
            buttonsContainer.getChildren().addAll(adminButtons);
        } else if (MainApp.getSession().getRoles().equals("ROLE_SOCIETE")) {
            societeButtons = new Button[]{
                makeButton("Candidats", "/app/gui/back_end/candidat/AfficherToutCandidat.fxml", false),
                makeButton("Mes offres", "/app/gui/back_end/societe/offre_de_travail/AfficherMesOffres.fxml", false),
                makeButton("Mes missions", "/app/gui/back_end/societe/mission/AfficherMesMissions.fxml", false),
                makeButton("Mes evenements", "/app/gui/back_end/societe/evenement/AfficherMesEvenements.fxml", false),
                makeButton("Mes formations", "/app/gui/back_end/societe/formation/AfficherMesFormations.fxml", false),
                makeButton("Mes candidatures", "/app/gui/back_end/societe/candidatureOffre/AffichertoutCandidatures.fxml", false)
            };
            buttonsContainer.getChildren().addAll(societeButtons);
        }

        Button boutonDeconnexion = makeButton("Deconnexion", null, false);
        boutonDeconnexion.setOnAction((event) -> {
            MainApp.getInstance().deconnexion();
        });
        bottomContainer.getChildren().add(boutonDeconnexion);
    }

    private Button makeButton(String text, String link, boolean isAdmin) {
        Button button = new Button();
        button.getStyleClass().add("link-button-blue");
        button.setTextFill(Color.WHITE);
        button.setText(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPadding(new Insets(25, 25, 25, 25));
        button.setCursor(Cursor.DEFAULT);
        button.setOnAction((event) -> {
            MainWindowController.chargerInterface(
                    getClass().getResource(link)
            );

            if (isAdmin) {
                for (Button adminButton : adminButtons) {
                    adminButton.getStyleClass().remove("link-button-blue-clicked");
                    adminButton.getStyleClass().add("link-button-blue");
                }
            } else {
                for (Button adminButton : societeButtons) {
                    adminButton.getStyleClass().remove("link-button-blue-clicked");
                    adminButton.getStyleClass().add("link-button-blue");
                }
            }
            button.getStyleClass().add("link-button-blue-clicked");
        });
        return button;
    }
}
