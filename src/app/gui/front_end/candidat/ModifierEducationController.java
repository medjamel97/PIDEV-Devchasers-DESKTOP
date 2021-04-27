/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.front_end.candidat;

import app.MainApp;
import app.controller.front_end.MainWindowController;
import app.entity.Education;
import app.service.EducationCrud;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Faten
 */
public class ModifierEducationController implements Initializable {

    @FXML
    private TextArea InputDescription;
    @FXML
    private Button ModifierEdu;
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
        Education education = ProfileController.educationActuelle;
        InputDescription.setText(education.getDescription());
        InputniveauEdu.setText(education.getNiveauEducation());
        InputFiliere.setText(education.getFiliere());
        InputEtabli.setText(education.getEtablissement());
        InputVille.setText(education.getVille());
        InputDuree.setText(education.getDuree());
    }    

    @FXML
    private void ModifierEducation(ActionEvent event) {
        
                Education education = new Education(ProfileController.educationActuelle.getId(),ProfileController.educationActuelle.getCandidatId(),
                    InputDescription.getText(), InputniveauEdu.getText(), InputFiliere.getText(), InputEtabli.getText(), InputVille.getText(), InputDuree.getText());
                EducationCrud.getInstance().modifierEducation(education);

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
