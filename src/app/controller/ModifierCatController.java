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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class ModifierCatController implements Initializable {

    @FXML
    private Button btnM;
    @FXML
    private TextField txtcat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           Categorie c = AffichertoutCategorieController.CategorieActuelle;
           txtcat.setText(c.getNomcategorie());
           
    }    
    
    
      private void categorie(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/admin/categorie/affichertoutCategorie.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void modifierCat(ActionEvent event) {
         Categorie c =  AffichertoutCategorieController.CategorieActuelle;
        c.setNomcategorie(txtcat.getText());
       
        

        CategorieCrud cats = new CategorieCrud();
        cats.ModifierCat(c);

        categorie(event);
    }
    
}
