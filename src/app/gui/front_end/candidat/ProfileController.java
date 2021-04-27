/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.front_end.candidat;

import app.controller.front_end.MainWindowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Faten
 */
public class ProfileController implements Initializable {

    @FXML
    private Button ajoutComp;
    @FXML
    private Button Education;
    @FXML
    private Button ExpdeTravail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Competence(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjoutCompetence.fxml")
        );
    }

    @FXML
    private void Education(ActionEvent event) {
         MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjouterEducation.fxml")
        );
    }

    @FXML
    private void ExperiencedeTravail(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjouterExperienceDeTravail.fxml")
        );
    }
    
}
