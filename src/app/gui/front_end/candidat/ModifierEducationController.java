/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.front_end.candidat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        // TODO
    }    

    @FXML
    private void ModifierEducation(ActionEvent event) {
    }
    
}
