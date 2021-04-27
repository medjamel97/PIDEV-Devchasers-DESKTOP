/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.Publication;
import app.service.PublicationCrud;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Maher
 */
public class PublicationModifierController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextArea description;
    @FXML
    private TextField titre;
    @FXML
    private Label res;
    @FXML
    private Button modifier_btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Publication publication = PublicationAfficherToutController.pubActuelle;
        description.setText(publication.getDescription());
        titre.setText(publication.getTitre());
    }

    @FXML
    private void modifier(ActionEvent event) {

        String objet = titre.getText();
        String desc = description.getText();

        Publication publication = new Publication(
                PublicationAfficherToutController.pubActuelle.getId(),
                Types.NULL,
                objet,
                desc,
                Timestamp.valueOf(LocalDateTime.now()),
                Types.NULL
        );
        PublicationCrud.getInstance().ModiferPublication(publication);
        PublicationAfficherToutController.pubActuelle = null;

        // retour
        publication(event);

    }

    private void publication(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/publication/Accueil.fxml")
        );
    }

}
