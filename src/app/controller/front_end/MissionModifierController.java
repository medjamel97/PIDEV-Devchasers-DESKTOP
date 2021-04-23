/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

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
        Dateid.setValue(mission.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

    }

    @FXML
    private void mission(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/mission/afficherMission.fxml")
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
                MissionAfficherToutController.missionActuelle.getId(),
                null,
                inputDescription.getText(),
                java.sql.Date.valueOf(Dateid.getValue()),
                Integer.parseInt(inputnbheure.getText()),
                Float.parseFloat(inputprix.getText())
        );

        MissionCrud.getInstance().modifierMission(m);
        mission(event);
    }

}
