/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.entity.Mission;
import app.service.MissionCrud;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Akram
 */
public class MissionAfficherToutController implements Initializable {

    MissionCrud missionCrud = new MissionCrud();
    ObservableList<Mission> observableListMission = FXCollections.observableArrayList();

    @FXML
    private Button btnRetour;
    @FXML
    private Button btnAjout;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableColumn<Mission, Integer> columnId;
    @FXML
    private TableColumn<Mission, String> columnNom;
    @FXML
    private TableColumn<Mission, String> columnDescription;
    @FXML
    private TableColumn<Mission, Integer> columnNbHeure;
    @FXML
    private TableColumn<Mission, Float> columnPrixHeure;
    @FXML
    private TableColumn<Mission, Date> columnDate;
    @FXML
    private TableColumn<Mission, Integer> columnSociete;
    @FXML
    private TableView<Mission> idTableau;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Mission> listmission = missionCrud.getMission();
        if (!listmission.isEmpty()) {
            for (int i = 0; i < listmission.size(); i++) {
                observableListMission.add(listmission.get(i));
            }
        }

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnNbHeure.setCellValueFactory(new PropertyValueFactory<>("nombreHeures"));
        columnPrixHeure.setCellValueFactory(new PropertyValueFactory<>("prixHeure"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnSociete.setCellValueFactory(new PropertyValueFactory<>("societe"));;
        idTableau.setItems(observableListMission);

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
    private void ajouterMission(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/mission/ajouterMission.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
