/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.Mission;
import app.service.MissionCrud;
import java.net.URL;
import java.sql.Date;
import java.sql.Types;
import java.util.List;
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
public class MissionAjouterController implements Initializable {

    @FXML
    private Text topText;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField inputId;
    @FXML
    private TextField inputDescription;
    @FXML
    private Button btnAjout;
    @FXML
    private DatePicker Dateid;
    @FXML
    private TextField inputnbheure;
    @FXML
    private TextField inputprix;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void mission(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/mission/afficherMission.fxml")
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
        if (inputId.getText().isEmpty() | inputnbheure.getText().isEmpty() | inputprix.getText().isEmpty() | inputDescription.getText().isEmpty()) {
            //JOptionPane.showMessageDialog(null, "Remplir les champs vides");
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText(null);
            al.setContentText("Remplir les champs vides");
            al.showAndWait();
        } else {
            Boolean update = true;
            String nom = inputId.getText();
            String nbheure = inputnbheure.getText();
            String prix = inputprix.getText();
            String description = inputDescription.getText();
            Date date = null;
            Date d = null;
            try {
                d = java.sql.Date.valueOf(Dateid.getValue());
            } catch (Exception e) {
                System.out.println(e.getMessage() + " " + e.getCause());
            }
            Mission mission = new Mission(Types.NULL, nom, description, date, Integer.parseInt(nbheure), Integer.parseInt(prix));
            List<Mission> listmission = MissionCrud.getInstance().getMission();
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
            if (d.before(date)) {
                System.out.println("erreur!!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("erreur");
                alert.setHeaderText(null);
                alert.setContentText("on a dépasser cette date !!! ");
                alert.showAndWait();
                update = false;
            }
            if (update) {
                MissionCrud.getInstance().ajouterMission(mission);
                mission(event);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Félicitation");
                alert.setHeaderText(null);
                alert.setContentText("Mission ajoutée !!");
                alert.showAndWait();
            }

            //MissionCrud.getInstance().ajouterMission(new mission(inputId.getText(), inputDescription.getText(), Date.valueOf(Dateid.getValue()), inputnbheure.getText(), inputprix.getText()));
            //JOptionPane.showMessageDialog(null, "evenement ajouté");
        }
    }

}
