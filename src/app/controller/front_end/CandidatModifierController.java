/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.Candidat;
import app.service.CandidatCrud;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class CandidatModifierController implements Initializable {

    @FXML
    private Button btCand;
    @FXML
    private TextField inputNom;
    @FXML
    private TextField inputPrenom;
    @FXML
    private DatePicker inputDate;
    @FXML
    private TextField inputTelephone;
    @FXML
    private ComboBox<String> inputSexe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Candidat c = CandidatAfficherToutController.candidatActuel;
        inputNom.setText(c.getNom());
        inputPrenom.setText(c.getPrenom());
        //inputDate.setValue(c.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        inputTelephone.setText(c.getTel());
        inputSexe.getItems().add("feminin");
        inputSexe.getItems().add("masculin");
        inputSexe.setValue(c.getSexe());
    }

    private void candidat(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AfficherToutCandidat.fxml")
        );
    }

    @FXML
    private void modifier(ActionEvent event) {
        Candidat candidat = new Candidat(
                CandidatAfficherToutController.candidatActuel.getId(),
                inputNom.getText(),
                inputPrenom.getText(),
                java.sql.Date.valueOf(inputDate.getValue()),
                inputSexe.getValue(),
                inputTelephone.getText(),
                null
        );
        
        CandidatCrud.getInstance().modifierCandidat(candidat);
        candidat(event);
    }
}
