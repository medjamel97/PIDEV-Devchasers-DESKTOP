/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.front_end.candidat;

import app.controller.front_end.MainWindowController;
import app.entity.ExperienceDeTravail;
import static app.gui.front_end.candidat.ProfileController.experience_de_travailActuelle;
import app.service.ExperienceDeTravailCrud;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Faten
 */
public class ModifierExperienceDeTravailController implements Initializable {

    @FXML
    private Button ExperienceDeTravail;
    @FXML
    private TextField InputVille;
    @FXML
    private TextField InputNomEntreprise;
    @FXML
    private TextField InputDureeExp;
    @FXML
    private TextField InputTitreEmp;
    @FXML
    private TextArea InputDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ExperienceDeTravail experience_de_travail = ProfileController.experience_de_travailActuelle;
        InputDescription.setText(experience_de_travail.getDescription());

        InputTitreEmp.setText(experience_de_travail.getTitreEmploi());
        InputDureeExp.setText(experience_de_travail.getDuree());
        InputNomEntreprise.setText(experience_de_travail.getNomEntreprise());
        InputVille.setText(experience_de_travail.getVille());
    }

    @FXML
    private void ExperienceDeTravail(ActionEvent event) {
        ExperienceDeTravail experience_de_travail = new ExperienceDeTravail(ProfileController.experience_de_travailActuelle.getId(), ProfileController.experience_de_travailActuelle.getCandidatId(),
                InputDescription.getText(), InputTitreEmp.getText(), InputDureeExp.getText(), InputNomEntreprise.getText(), InputVille.getText());
        Notifications notificationBuilder = Notifications.create()
                .title("Succes").text("Votre experience de travail a été modifié avec succès").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                .position(Pos.CENTER_LEFT)
                .onAction((eventAjoutPub) -> {
                    System.out.println("clicked ON ");
                });
        notificationBuilder.show();
        ExperienceDeTravailCrud.getInstance().modifierExperienceDeTravail(experience_de_travail);
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/profile.fxml")
        );
    }

    @FXML
    private void drag(MouseEvent event) {
    }

    @FXML
    private void startDrag(MouseEvent event) {
    }

}
