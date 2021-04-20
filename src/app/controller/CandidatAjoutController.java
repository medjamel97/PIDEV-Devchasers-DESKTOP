/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.service.CandidatCrud;
import app.entity.Candidat;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Faten
 */
public class CandidatAjoutController implements Initializable {

    @FXML
    private Button btCand;
    @FXML
    private TextField inputNom;
    @FXML
    private TextField inputPrenom;
    @FXML
    private TextField inputTelephone;
    @FXML
    private DatePicker inputDate;
    @FXML
    private ComboBox<String> inputSexe;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputSexe.getItems().add("feminin");
        inputSexe.getItems().add("masculin");
    }

    private void candidat(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/AfficherToutCandidat.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void ajout(ActionEvent event) {
        Candidat c = new Candidat();
        c.setNom(inputNom.getText());
        c.setPrenom(inputPrenom.getText());
        c.setTel(inputTelephone.getText());
        Date date = java.sql.Date.valueOf(inputDate.getValue());
        c.setDateNaissance(date);
        c.setSexe(inputSexe.getValue());

        CandidatCrud candidatCrud = new CandidatCrud();
        candidatCrud.ajouterCandidat(c);

        candidat(event);
    }
}
