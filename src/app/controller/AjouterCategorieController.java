/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.entity.Categorie;
import app.service.CategorieCrud;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class AjouterCategorieController implements Initializable {

    @FXML
    private TextField cat;
    @FXML
    private Button ajout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void categorie(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/admin/categorie/affichertoutCategorie.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }
    

    @FXML
    private void ajouterCat(ActionEvent event) {
         CategorieCrud cate = new CategorieCrud();

        
        String nomcategorie = cat.getText();

        Categorie cat = new Categorie(nomcategorie);
           if(controleDeSaisie(cat)){

        cate.ajouterCat(cat);
   
        
        categorie(event);
    }}
    private boolean controleDeSaisie(Categorie cat) {
        boolean isValid = true;
        CategorieCrud cats = new CategorieCrud();


        if (!cats.controleNomcat(cat.getNomcategorie())) {
            creerAlerte("Objet vide");
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
