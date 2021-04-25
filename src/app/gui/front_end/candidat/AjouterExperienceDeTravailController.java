/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.front_end.candidat;

import app.controller.front_end.MainWindowController;
import app.entity.ExperienceDeTravail;
import app.service.ExperienceDeTravailCrud;
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
public class AjouterExperienceDeTravailController implements Initializable {

    @FXML
    private Button ExperienceDeTravail;
    @FXML
    private TextArea InputDescription;
    @FXML
    private TextField InputTitreEmp;
    @FXML
    private TextField InputDureeExp;
    @FXML
    private TextField InputNomEntreprise;
    @FXML
    private TextField InputVille;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ExperienceDeTravail(ActionEvent event) {
         ExperienceDeTravail experience_de_travail = new ExperienceDeTravail( 
                InputDescription.getText(), InputTitreEmp.getText(),InputDureeExp.getText(),InputNomEntreprise.getText(),InputVille.getText()); 
        
        ExperienceDeTravailCrud.getInstance().ajouterExperienceDeTravail(experience_de_travail);
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
