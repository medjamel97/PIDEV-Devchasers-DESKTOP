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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class ModifierController implements Initializable {

    @FXML
    private TextField textJob;
    @FXML
    private TextArea textDesc;
    @FXML
    private Button modifieroffre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   OffreDeTravail c = OffreAfficherToutController.offreActuelle;
        textJob.setText(c.getJob());
        textDesc.setText(c.getDescription());
      
    }

    private void offre(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/AfficherTout.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

   

    @FXML
    private void ModiferOffre(ActionEvent event) {
         OffreDeTravail c =  OffreAfficherToutController.offreActuelle;
        c.setJob(textJob.getText());
        c.setDescription(textDesc.getText());
        

        OffreDeTravailCrud offres = new OffreDeTravailCrud();
        offres.ModifierOffre(c);

        offre(event);
   
}

    
}