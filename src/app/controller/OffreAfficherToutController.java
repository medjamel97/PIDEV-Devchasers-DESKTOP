/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.entity.OffreDeTravail;
import app.service.OffreDeTravailCrud;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class OffreAfficherToutController implements Initializable {

    OffreDeTravailCrud offreDeTravailCrud = new OffreDeTravailCrud();

    /**
     *
     */
    public static OffreDeTravail offreActuelle = null;
   
    @FXML        
    private Button btnRetour;
    @FXML
    private Button btnAjout;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableColumn<OffreDeTravail, Integer> c1;
    @FXML
    private TableColumn<OffreDeTravail, String> c2;
    @FXML
    private TableColumn<OffreDeTravail, String> c3;
    @FXML
    private TableColumn<OffreDeTravail, Integer> c4;
    
    ObservableList<OffreDeTravail> prds = FXCollections.observableArrayList();
    
    OffreDeTravailCrud controle = new OffreDeTravailCrud();
    @FXML
    private TableView<OffreDeTravail> T;
    @FXML
    private Button btns;
    @FXML
    private Button btnM;
    @FXML
    private TextField recherche;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<OffreDeTravail> liste = controle.DisplayOffre();
		if (!liste.isEmpty()) {
			for (int i = 0; i < liste.size(); i++) {
				prds.add(liste.get(i));
			}
		}
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        c2.setCellValueFactory(new PropertyValueFactory<>("job"));
        c3.setCellValueFactory(new PropertyValueFactory<>("description"));
        c4.setCellValueFactory(new PropertyValueFactory<>("idCat"));
        T.setItems(prds);
        
    }
private void initTable(List<OffreDeTravail> listoffre) {
        OffreDeTravailCrud of = new OffreDeTravailCrud();
        if (listoffre == null) {
            listoffre = of.DisplayOffre();
        }
        List<OffreDeTravail> lf = new ArrayList<OffreDeTravail>();
        listoffre.forEach(e -> {

            try {
                OffreDeTravail addoffre = new OffreDeTravail(e.getId(), e.getJob(), e.getDescription());
                addoffre.setId(0);
                String description = null;
                addoffre.setDescription(description);
                lf.add(addoffre);
            } catch (MalformedURLException ex) {
                System.out.print(ex.getMessage());
            }

        });
        //System.out.println("ev " + lIm);

        ObservableList<OffreDeTravail> listoffre = FXCollections.observableArrayList(lf);

        tableCours.setItems(listoffre);
    }
    @FXML
    private void accueil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/MainWindow.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void ajouterOffre(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/Manipuler.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

   
 @FXML
    private void select(MouseEvent event) {
        offreActuelle = T.getSelectionModel().getSelectedItem();
        btnM.setDisable(false);
        btns.setDisable(false);
        
    }
    @FXML
    private void supprimerOffre(ActionEvent event) {
        offreDeTravailCrud.SupprimerOffre(offreActuelle.getId());
        offreActuelle = null;
        btnM.setDisable(true);
        btns.setDisable(true);
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
    private void modifierOffre(ActionEvent event) {
         if (offreActuelle != null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/Modifier.fxml"));
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
    private void rechercheOffre(KeyEvent event) {
        OffreDeTravailCrud o = new OffreDeTravailCrud();

        initTable(o.rechercheOffre(recherche.getText()));
    }
  
}

  
    
    
    
    
    


