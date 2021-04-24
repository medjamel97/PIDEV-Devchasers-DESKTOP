/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.MainApp;
import app.entity.Mission;
import app.service.MissionCrud;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Akram
 */
public class MissionModifierController implements Initializable {

    @FXML
    private Text topText;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField inputNom;
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
    @FXML
    private TextField inputVille;
    @FXML
    private TextField inputLong;
    @FXML
    private TextField inputLat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputNom.setText(MissionMesMissionsController.missionActuelle.getNom());
        inputDescription.setText(MissionMesMissionsController.missionActuelle.getDescription());
        Dateid.setValue(MissionMesMissionsController.missionActuelle.getDate().toLocalDate());
        inputnbheure.setText(String.valueOf(MissionMesMissionsController.missionActuelle.getNombreHeures()));
        inputprix.setText(String.valueOf(MissionMesMissionsController.missionActuelle.getPrixHeure()));
        inputVille.setText(MissionMesMissionsController.missionActuelle.getVille());
        inputLong.setText(MissionMesMissionsController.missionActuelle.getLongitude());
        inputLat.setText(MissionMesMissionsController.missionActuelle.getLatitude());

    }

    @FXML
    private void mission(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/mission/AfficherMesMissions.fxml")
        );
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Félicitation");
        alert.setHeaderText(null);
        alert.setContentText("Mission modifier !!");
        alert.showAndWait();
    }

    @FXML
    private void modifier(ActionEvent event) {
        Mission m = new Mission(
                MissionMesMissionsController.missionActuelle.getId(),
                MainApp.getSession().getSocieteId(),
                inputNom.getText(),
                inputDescription.getText(),
                java.sql.Date.valueOf(Dateid.getValue()),
                Integer.parseInt(inputnbheure.getText()),
                Float.parseFloat(inputprix.getText()),
                inputVille.getText(),
                inputLong.getText(),
                inputLat.getText()
        );

        MissionCrud.getInstance().modifierMission(m);
        mission(event);
    }

}
