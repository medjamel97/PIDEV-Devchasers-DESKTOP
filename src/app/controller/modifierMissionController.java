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
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Akram
 */
public class modifierMissionController implements Initializable {

    @FXML
    private Text topText;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField inputId;
    @FXML
    private TextField inputnbheure;
    @FXML
    private TextField inputDescription;
    @FXML
    private Button btnModif;
    @FXML
    private TextField inputprix;
    @FXML
    private DatePicker Dateid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Mission mission = MissionAfficherToutController.missionActuelle;
        inputId.setText(String.valueOf(mission.getNom()));
        inputnbheure.setText(String.valueOf(mission.getNombreHeures()));
        inputDescription.setText(String.valueOf(mission.getDescription()));
        inputprix.setText(String.valueOf(mission.getPrixHeure()));
        Dateid.setValue(mission.getDate().toLocalDate());

    }

    @FXML
    private void mission(ActionEvent event) {
             try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/mission/afficherMission.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Félicitation");
            alert.setHeaderText(null);
            alert.setContentText("Mission modifier !!");    
            alert.showAndWait();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
        Mission m = new Mission();
        m.setId(MissionAfficherToutController.missionActuelle.getId());
        m.setNom(inputId.getText());
        m.setDescription(inputDescription.getText());
        Date date = java.sql.Date.valueOf(Dateid.getValue());
        m.setDate(date);
        m.setNombreHeures(Integer.parseInt(inputnbheure.getText()));
        m.setPrixHeure(Float.parseFloat(inputprix.getText()));

        MissionCrud missionCrud = new MissionCrud();
        missionCrud.modifierMission(m);
        mission(event);
    }

}
