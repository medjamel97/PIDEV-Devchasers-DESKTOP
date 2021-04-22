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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class AffichertoutCategorieController implements Initializable {
    
    CategorieCrud categorieCrud = new CategorieCrud();

    @FXML
    private TableColumn<Categorie, Integer> cat;
    @FXML
    private TableColumn<Categorie,String> ncat;
    @FXML
    private Button supcat;
    @FXML
    private Button modcat;
    @FXML
    private TableView<Categorie> tab;
    
    CategorieCrud controle = new CategorieCrud();
     public static Categorie CategorieActuelle = null;
    ObservableList<Categorie> prds = FXCollections.observableArrayList();
    @FXML
    private Button btnajout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         List<Categorie> listecat = controle.DisplayCat();
		if (!listecat.isEmpty()) {
			for (int i = 0; i < listecat.size(); i++) {
				prds.add(listecat.get(i));
			}
		}
        cat.setCellValueFactory(new PropertyValueFactory<>("id"));
        ncat.setCellValueFactory(new PropertyValueFactory<>("nomcategorie"));
       
        tab.setItems(prds);
    }    

   

    @FXML
    private void SuppCat(ActionEvent event) {
        categorieCrud.SupprimerCat(CategorieActuelle.getId());
        CategorieActuelle = null;
        modcat.setDisable(true);
        supcat.setDisable(true);
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
        if (CategorieActuelle != null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/app/gui/admin/categorie/ModifierCat.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print("Erreur d'affichage : " + e.getMessage() + " " + e.getCause());
            }

        }
    }

    @FXML
    private void choose(MouseEvent event) {
        
        CategorieActuelle = tab.getSelectionModel().getSelectedItem();
        modcat.setDisable(false);
        supcat.setDisable(false);
    }

    @FXML
    private void ajouter(ActionEvent event) {
           try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/admin/categorie/Ajouter.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
      
}
