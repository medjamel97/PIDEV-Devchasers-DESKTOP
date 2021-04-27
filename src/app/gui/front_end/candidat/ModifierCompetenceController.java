/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.front_end.candidat;

import app.controller.front_end.MainWindowController;
import app.entity.Competence;
import app.service.CompetenceCrud;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Faten
 */
public class ModifierCompetenceController implements Initializable {

    @FXML
    private TextField InputLevel;
    @FXML
    private TextField Inputname;
    @FXML
    private Button ModifierComp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Competence competence = ProfileController.competenceActuelle;
        Inputname.setText(competence.getName());
        InputLevel.setText(String.valueOf(competence.getLevel()));

    }

    @FXML
    private void ModifierCompetence(ActionEvent event) {
        Competence competence = new Competence(ProfileController.competenceActuelle.getId(), ProfileController.competenceActuelle.getCandidatId(),
                Inputname.getText(), Integer.parseInt(InputLevel.getText()));
        CompetenceCrud.getInstance().modifierCompetence(competence);

        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/profile.fxml")
        );
    }

}
