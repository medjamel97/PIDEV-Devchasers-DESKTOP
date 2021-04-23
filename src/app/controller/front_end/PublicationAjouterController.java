/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.Publication;
import app.service.PublicationCrud;
import app.utils.BadWords;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Maher
 */
public class PublicationAjouterController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextArea description;
    @FXML
    private TextField titre;
    @FXML
    private Button ajouter_btn;
    @FXML
    private Label res;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ajouter(ActionEvent event) {
        LocalDate dd = LocalDate.now();
        Date date = java.sql.Date.valueOf(dd);

        if (BadWords.filterText(description.getText()) || BadWords.filterText(titre.getText())) {

            Notifications notificationBuilder = Notifications.create()
                    .title("Alert").text("Khedemti n'autorise pas ces termes").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();

            Parent root;
        } else if (titre.getText().isEmpty()) {
            label.setText("Veuillez saisir le titre ");
        } else if (description.getText().isEmpty()) {
            label.setText("Veuillez remplir la description ");
        } else {

            Notifications notificationBuilder = Notifications.create()
                    .title("Succes").text("Votre Publication est ajoutè avec succès").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction((eventAjoutPub) -> {
                        System.out.println("clicked ON ");
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();

            Publication publication = new Publication(Types.NULL, titre.getText(), description.getText(), date, 0);
            PublicationCrud.getInstance().AjouterPublication(publication);

            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/front_end/candidat/publication/Accueil.fxml")
            );
        }
    }
}
