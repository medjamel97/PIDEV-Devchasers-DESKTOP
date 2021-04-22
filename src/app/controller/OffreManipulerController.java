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
import javafx.scene.control.Alert;
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
        
        if(controleDeSaisie(offreDeTravail)){
        
        offreDeTravailCrud.ajouterOffre(offreDeTravail);
   
        offre(event);
        }
    }
    
private boolean controleDeSaisie(OffreDeTravail offre) {
        boolean isValid = true;
        OffreDeTravailCrud offresdetravail = new OffreDeTravailCrud();


        if (!offresdetravail.controleJob(offre.getJob())) {
            creerAlerte("Objet vide");
            isValid = false;
        }

        if (!offresdetravail.controleDescription(offre.getDescription())) {
            creerAlerte("Description trés courte ou vide");
            isValid = false;
        }

      

        return isValid;
    }

    private void creerAlerte(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
