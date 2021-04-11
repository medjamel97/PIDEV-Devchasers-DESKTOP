/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.service.RevueService;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import app.entity.Revue;
import java.sql.Types;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class RevueManipulerController implements Initializable {

    @FXML
    private Button btnRetour;
    @FXML
    private TextField inputId;
    @FXML
    private TextField inputNbEtoiles;
    @FXML
    private TextField inputObjet;
    @FXML
    private TextField inputDescription;
    @FXML
    private Button btnAjout;
    @FXML
    private Text topText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Revue revue = RevueAfficherToutController.revueActuelle;
        if (revue != null) {
            topText.setText("Modifier revue");
            inputId.setText(String.valueOf(revue.getId()));
            inputNbEtoiles.setText(String.valueOf(revue.getId()));
            inputObjet.setText(revue.getObjet());
            inputDescription.setText(revue.getDescription());
        }
    }

    @FXML
    private void revue(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/revue/AfficherTout.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void ajout(ActionEvent event) {
        RevueService revueService = new RevueService();

        String id = inputId.getText();
        String nbEtoiles = inputNbEtoiles.getText();
        String objet = inputObjet.getText();
        String description = inputDescription.getText();

        /*if (!revueService.ControleObjet(revue)) {
            alertObjet.setText("CIN non valable. ");
            isValid = false;
        }
        if (!revueService.ControleDescription(revue)) {
            alertDescription.setText("Role non valable. ");
            retourstr = false;
        }

        if (isValid == true) {*/
        Revue revue = new Revue(Integer.parseInt(id), Integer.parseInt(nbEtoiles), objet, description, Types.NULL);
        if (RevueAfficherToutController.revueActuelle != null) {
            revueService.modifierRevue(revue);
            RevueAfficherToutController.revueActuelle = null;
        } else {
            revueService.ajouterRevue(revue);
        }
        //}

        // retour
        revue(event);
    }

}
