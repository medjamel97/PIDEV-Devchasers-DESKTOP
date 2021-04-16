/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.entity.OffreDeTravail;
import app.service.OffreDeTravailCrud;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class OffreManipulerController implements Initializable {

    @FXML
    private TextField tfJob;
    @FXML
    private TextArea tfDesc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void offre(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/AfficherTout.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }
    @FXML
    private void manipulerOffre(ActionEvent event) {
        OffreDeTravailCrud offreDeTravailCrud = new OffreDeTravailCrud();

        String job = tfJob.getText();
        String desc = tfDesc.getText();

        OffreDeTravail offreDeTravail = new OffreDeTravail(job, desc);
        offreDeTravailCrud.ajouterOffre(offreDeTravail);
   
        
        offre(event);
        
    }

}
