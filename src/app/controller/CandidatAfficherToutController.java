/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.entity.Candidat;
import app.service.CandidatCrud;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class CandidatAfficherToutController implements Initializable {

    CandidatCrud candidatCrud = new CandidatCrud();
    ObservableList<Candidat> candidats = FXCollections.observableArrayList();
    public static Candidat candidatActuel;

    @FXML
    private TableView<Candidat> tab;
    @FXML
    private TableColumn<Candidat, Integer> tabId;
    @FXML
    private TableColumn<Candidat, String> tabNom;
    @FXML
    private TableColumn<Candidat, String> tabPrenom;
    @FXML
    private TableColumn<Candidat, Date> tabDateNaiss;
    @FXML
    private TableColumn<Candidat, String> tabSexe;
    @FXML
    private TableColumn<Candidat, String> tabTel;
    @FXML
    private Text idChoisi;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
        
        List<Candidat> listCandidats = candidatCrud.getCandiadat();

        if (!listCandidats.isEmpty()) {
            for (int i = 0; i < listCandidats.size(); i++) {
                candidats.add(listCandidats.get(i));
            }
        }

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tabPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tabDateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        tabSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        tabTel.setCellValueFactory(new PropertyValueFactory<>("tel"));

        candidatActuel = null;

        tab.setItems(candidats);
    }

    @FXML
    private void ajoutCandidat(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/AjoutCandidat.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print("Erreur d'affichage : " + e.getMessage() + " " + e.getCause());
        }
    }

    @FXML
    private void supprimerCandidat(ActionEvent event) {
        candidatCrud.supprimerCandidat(candidatActuel);
        candidatActuel = null;
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/AfficherToutCandidat.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
        
    }

    @FXML
    private void modifierCandidat(ActionEvent event) {
        if (candidatActuel != null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/app/gui/ModifierCandidat.fxml"));
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
    private void selectCandidat(MouseEvent event) {
        candidatActuel = tab.getSelectionModel().getSelectedItem();
        idChoisi.setText("id choisi : " + candidatActuel.getId());
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
    }
}
