/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevv;

import Entities.commentaire;
import Entities.publication;
import Service.commentaireService;
import Service.publicationService;
import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.TextField;

import javafx.scene.input.KeyEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 * FXML Controller class
 *
 * @author Maher
 */
public class AffichageController implements Initializable {

    public int idR = 0;
    public static publication pubActuelle;

    @FXML
    private Button btnAjout;
    @FXML
    private TextField txrecherche;
    @FXML
    private VBox pubvbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<publication> listFed = new ArrayList<publication>();
        publicationService rs = new publicationService();
        listFed = rs.getAll();
        for (int i = 0; i < listFed.size(); i++) {
            creepub(listFed.get(i));
        }

    }

    public void creepub(publication publication) {

        try {
            Parent modelePub = FXMLLoader.load(getClass().getResource("/pidevv/modelpub.fxml"));
            pubvbox.getChildren().add(modelePub);
            ((Text) ((AnchorPane) modelePub).getChildren().get(0)).setText(publication.getTitre());
            ((Text) ((AnchorPane) modelePub).getChildren().get(1)).setText(publication.getDescription());
            ((Button) ((AnchorPane) modelePub).getChildren().get(2)).setOnAction((event) -> {
                try {
                    Supprimer(event, publication);
                } catch (SQLDataException ex) {
                    Logger.getLogger(AffichageController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            ((Button) ((AnchorPane) modelePub).getChildren().get(3)).setOnAction((event) -> {

                Modifier(event, publication);

            });

            ((Button) ((AnchorPane) modelePub).getChildren().get(6)).setOnAction((event) -> {
                String textComm = ((TextField) ((AnchorPane) modelePub).getChildren().get(5)).getText();
                commentaire c = new commentaire(publication.getId(), textComm);
                commentaireService cs = new commentaireService();
                cs.AjouterCommentaire(c);
                Parent modeleComForAjout;
                try {
                    modeleComForAjout = FXMLLoader.load(getClass().getResource("/pidevv/modelcom.fxml"));
                    ((VBox) ((AnchorPane) modelePub).getChildren().get(4)).getChildren().add(modeleComForAjout);
                    ((Text) ((StackPane) ((AnchorPane) modeleComForAjout).getChildren().get(0)).getChildren().get(0))
                            .setText(c.getDescription());
                } catch (IOException ex) {
                    Logger.getLogger(AffichageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            try {
                List<commentaire> listFed = new ArrayList<commentaire>();
                commentaireService cs = new commentaireService();
                listFed = cs.getAllByPubId(publication.getId());

                if (listFed.size() != 0) {
                    for (int i = 0; i < listFed.size(); i++) {
                        Parent modeleCom = FXMLLoader.load(getClass().getResource("/pidevv/modelcom.fxml"));
                        ((VBox) ((AnchorPane) modelePub).getChildren().get(4)).getChildren().add(modeleCom);
                        ((Text) ((StackPane) ((AnchorPane) modeleCom).getChildren().get(0)).getChildren().get(0))
                                .setText(listFed.get(0).getDescription());
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(AffichageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(AffichageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pidevv/AjoutPub.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print("Erreur d'affichage : " + e.getMessage() + " " + e.getCause());
        }
    }

    private void Modifier(ActionEvent event, publication publication) {

        pubActuelle = publication;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pidevv/ModifPub.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print("Erreur d'affichage : " + e.getMessage() + " " + e.getCause());
        }

    }

    private void Supprimer(ActionEvent event, publication publication) throws SQLDataException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText(null);
        alert.setContentText("vous voulez vraiment supprimer cette publication ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            publicationService ps = new publicationService();
            ps.SupprimerPublication(publication);
            pubActuelle = null;

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/pidevv/Accueil.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.print(ex.getMessage());
            }
        }
    }

    @FXML
    private void recherche(KeyEvent event) {
        pubvbox.getChildren().clear();
        publicationService ps = new publicationService();
        List<publication> listpublication = ps.findpubBytitre(txrecherche.getText());

        if (!listpublication.isEmpty()) {
            for (int i = 0; i < listpublication.size(); i++) {
                creepub(listpublication.get(i));
            }

        }
    }

}
