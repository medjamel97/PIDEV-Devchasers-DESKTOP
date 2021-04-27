/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.front_end.candidat;

import app.MainApp;
import app.controller.InscriptionCandidatController;
import app.controller.front_end.MainWindowController;
import app.entity.Education;
import app.service.EducationCrud;
import java.net.URL;
import java.sql.Types;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Faten
 */
public class AjouterEducationController implements Initializable {

    @FXML
    private Button ajouterEdu;
    @FXML
    private TextArea InputDescription;
    @FXML
    private TextField InputniveauEdu;
    @FXML
    private TextField InputFiliere;
    @FXML
    private TextField InputVille;
    @FXML
    private TextField InputDuree;
    @FXML
    private TextField InputEtabli;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void AjouterEducation(ActionEvent event) {

        if (InputDescription.getText().isEmpty() | InputniveauEdu.getText().isEmpty() | InputFiliere.getText().isEmpty() | InputEtabli.getText().isEmpty()
                | InputVille.getText().isEmpty() | InputDuree.getText().isEmpty()) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Erreur").text("veillez remplir les champs vides ").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.show();

            Parent root;
        } else {
            Education education = new Education(MainApp.getSession().getCandidatId(),
                    InputDescription.getText(), InputniveauEdu.getText(), InputFiliere.getText(), InputEtabli.getText(), InputVille.getText(), InputDuree.getText());
            Notifications notificationBuilder = Notifications.create()
                    .title("Succes").text("Votre education a été ajoutè avec succès").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction((eventAjoutPub) -> {
                        System.out.println("clicked ON ");
                    });
            notificationBuilder.show();
            EducationCrud.getInstance().ajouterEducation(education);
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/front_end/candidat/profile.fxml")
            );
        }
    }

    @FXML
    private void drag(MouseEvent event) {

    }

    @FXML
    private void startDrag(MouseEvent event) {
    }

}
