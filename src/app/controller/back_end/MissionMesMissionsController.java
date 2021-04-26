/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.entity.Mission;
import app.service.MissionCrud;
import app.utils.ConnecteurBD;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import  app.service.MissionCrud;
import app.utils.ConnecteurBD;

/**
 * FXML Controller class
 *
 * @author Akram
 */
public class MissionMesMissionsController implements Initializable {

   
    MissionCrud missionCrud = new MissionCrud();
    ObservableList<Mission> observableListMission = FXCollections.observableArrayList();
    public static Mission missionActuelle;
    public static Mission missionClicked;

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
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Text textMissionIdActuelle;
    @FXML
    private TextField txrecherche;
    @FXML
    private Button btnexcel;
   
  



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        missionActuelle = null;
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
        Date date = new Date();

        List<Mission> listmission = MissionCrud.getInstance().getMission();
        if (!listmission.isEmpty()) {
            for (int i = 0; i < listmission.size(); i++) {
                if (date.before(listmission.get(i).getDate())) {
                    observableListMission.add(listmission.get(i));
                    System.out.println("Date-1 is before Date-2  " + listmission.get(i).getNom());
                } else {
                    System.out.println("non");
                    MissionCrud.getInstance().supprimerMission(listmission.get(i));
                }
            }
        }

//      DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//      System.out.println(format.format(date));
        loadDate();
        List<Mission> listFed = MissionCrud.getInstance().getMission();
        observableListMission.clear();
        observableListMission.addAll(listFed);
        idTableau.setItems(observableListMission);

        columnId.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("nombreHeures"));
        columnNbHeure.setCellValueFactory(new PropertyValueFactory<>("prixHeure"));
        columnPrixHeure.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("ville"));
        columnSociete.setCellValueFactory(new PropertyValueFactory<>("societeId"));
        idTableau.setItems(observableListMission);
        Recherche();
    }

    @FXML
    private void accueil(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/candidat/publication/Accueil.fxml")
        );
    }

    @FXML
    private void ajouterMission(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/mission/ajouterMission.fxml")
        );
    }

    @FXML
    private void modifier(ActionEvent event) {
        if (missionActuelle != null) {
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/back_end/societe/mission/modifierMission.fxml")
            );
        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        MissionCrud.getInstance().supprimerMission(missionActuelle);
        missionActuelle = null;
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/mission/affichermission.fxml")
        );
    }

    @FXML
    private void changerMissionActuelle(MouseEvent event) {
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
        missionActuelle = idTableau.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) {
            missionClicked = idTableau.getSelectionModel().getSelectedItem();
            textMissionIdActuelle.setText("somme totale= " + missionActuelle.getNombreHeures()*missionActuelle.getPrixHeure());
//                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                     alert.setTitle("erreur");
//                     alert.setHeaderText(null);
//                     alert.setContentText("cette mission deja existe!!! ");    
//                     alert.showAndWait();

            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            System.out.println(missionClicked.getLatitude());
            webEngine.load("http://127.0.0.1:8000/map/"+missionClicked.getLatitude()+"/"+missionClicked.getLongitude());

            Scene scene = new Scene(webView, 600, 600);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hello World");
            primaryStage.show();
            webView.getEngine().executeScript("document.body.innerHTML = '';");

        }

        textMissionIdActuelle.setText("somme totale= " + missionActuelle.getNombreHeures()*missionActuelle.getPrixHeure());
    }


    public void Recherche() {
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Mission> filteredData = new FilteredList<>(observableListMission, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(mission -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (mission.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (String.valueOf(mission.getId()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Mission> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(idTableau.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        idTableau.setItems(sortedData);
    }

    private void loadDate() {
        ObservableList<Mission> abList = FXCollections.observableArrayList();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnNbHeure.setCellValueFactory(new PropertyValueFactory<>("nombreHeures"));
        columnPrixHeure.setCellValueFactory(new PropertyValueFactory<>("prixHeure"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnSociete.setCellValueFactory(new PropertyValueFactory<>("societe"));

        List old = MissionCrud.getInstance().getMission();
        abList.addAll(old);
        idTableau.setItems(abList);
        idTableau.refresh();
    }

    @FXML
    private void recherche(ActionEvent event) {
    }
    
    @FXML
    private void excel(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(.xls)", ".xls");
        chooser.getExtensionFilters().add(filter);
        // Show save dialog
        File file = chooser.showSaveDialog(btnexcel.getScene().getWindow());
        MissionCrud m = new MissionCrud();
        if (file != null) {
           m.Excel(file);
        }
        
    }

}
