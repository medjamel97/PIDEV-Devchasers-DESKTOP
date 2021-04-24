/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.MainApp;
import app.entity.Mission;
import app.service.MissionCrud;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
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
public class MissionAjouterController implements Initializable {

    @FXML
    private Text topText;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField inputNom;
    @FXML
    private TextField inputDescription;
    @FXML
    private DatePicker Dateid;
    @FXML
    private TextField inputnbheure;
    @FXML
    private TextField inputprix;
    @FXML
    private TextField inputVille;
    @FXML
    private TextField inputLong;
    @FXML
    private TextField inputLat;
    @FXML
    private Button btnAjout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void mission(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/mission/AfficherMesMissions.fxml")
        );
    }

//    private void loadDate(){
//    ObservableList<Mission> abList = FXCollections.observableArrayList();
//        colnumsalle.setCellValueFactory(new PropertyValueFactory<>("num_salle"));
//        colnomoffre.setCellValueFactory(new PropertyValueFactory<>("nom_offre"));
//        coldatedebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
//        coldatefin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
//        colspecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
//        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
//
//        Service_Evenement rt = new Service_Evenement();
//        List old = rt.listeventid();
//        abList.addAll(old);
//        tab.setItems(abList);
//        tab.refresh();
//    }
    @FXML
    private void ajout(ActionEvent event) {
        if (inputNom.getText().isEmpty() | inputnbheure.getText().isEmpty() | inputprix.getText().isEmpty() | inputDescription.getText().isEmpty()) {
            //JOptionPane.showMessageDialog(null, "Remplir les champs vides");
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText(null);
            al.setContentText("Remplir les champs vides");
            al.showAndWait();
        } else {
            java.util.Date date = new java.util.Date();
            Boolean update = true;
            MissionCrud missionCrud = new MissionCrud();
            String nom = inputNom.getText();
            String nbheure = inputnbheure.getText();
            String prix = inputprix.getText();
            String description = inputDescription.getText();
            Mission mission = new Mission(
                    MainApp.getSession().getSocieteId(),
                    nom,
                    description,
                    Date.valueOf(Dateid.getValue()),
                    Integer.parseInt(nbheure),
                    Integer.parseInt(prix),
                    inputVille.getText(),
                    inputLong.getText(),
                    inputLat.getText()
            );

            List<Mission> listmission = missionCrud.getMission();
            for (int i = 0; i < listmission.size(); i++) {
                if (nom.equals(listmission.get(i).getNom())) {
                    System.out.println("erreur!!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("cette mission deja existe!!! ");
                    alert.showAndWait();
                    update = false;
                }
//                 System.out.println(listmission.get(i).getNom());
            }
            if (mission.getDate().before(date)) {
                System.out.println("erreur!!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("erreur");
                alert.setHeaderText(null);
                alert.setContentText("on a dépasser cette date !!! ");
                alert.showAndWait();
                update = false;
            }
            if (update) {
                missionCrud.ajouterMission(mission);
                mission(event);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Félicitation");
                alert.setHeaderText(null);
                alert.setContentText("Mission ajoutée !!");
                alert.showAndWait();
            }

//MissionCrud rt = new MissionCrud();
//        rt.(new mission(inputId.getText(),inputDescription.getText(), Date.valueOf(Dateid.getValue()), inputnbheure.getText(), inputprix.getText()));
//        JOptionPane.showMessageDialog(null, "evenement ajouté");
        }
    }

}
