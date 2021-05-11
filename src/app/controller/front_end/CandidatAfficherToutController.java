/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.Candidat;
import app.service.CandidatCrud;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class CandidatAfficherToutController implements Initializable {

    ObservableList<Candidat> candidats = FXCollections.observableArrayList();
    public static Candidat candidatActuel;

    @FXML
    private TextField filterField;
    private Pagination pagination;
    private final static int rowsPerPage = 2;

    @FXML
    private TableView<Candidat> tab;
    @FXML
    private TableColumn<Candidat, Integer> tabId;
    @FXML
    private TableColumn<Candidat, String> tabNom;
    @FXML
    private TableColumn<Candidat, String> tabPrenom;
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
    @FXML
    private Button Education;
    @FXML
    private TableColumn<?, ?> tabDateNaiss;
    @FXML
    private Button exptravail;
    @FXML
    private Button competence;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //pagination.setPageFactory(this::createPage);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);

        List<Candidat> listCandidats = CandidatCrud.getInstance().getCandidats();

        if (!listCandidats.isEmpty()) {
            for (int i = 0; i < listCandidats.size(); i++) {
                candidats.add(listCandidats.get(i));
            }
        }

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tabPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tabSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        tabTel.setCellValueFactory(new PropertyValueFactory<>("tel"));

        candidatActuel = null;

        tab.setItems(candidats);

        //// Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Candidat> filteredData = new FilteredList<>(candidats, c -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(candidat -> {
                // If filter text is empty, display all candidats.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare nom and prenom of every candidat with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (candidat.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches  name.
                } else if (candidat.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches prenom.

                } else {
                    return false; // Does not match.
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Candidat> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tab.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tab.setItems(sortedData);
    }

    @FXML
    private void ajoutCandidat(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjoutCandidat.fxml")
        );

    }

    @FXML
    private void supprimerCandidat(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText(null);
        alert.setContentText("vous voulez vraiment supprimer ce candidat ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            CandidatCrud.getInstance().supprimerCandidat(candidatActuel);
            candidatActuel = null;
            btnModifier.setDisable(true);
            btnSupprimer.setDisable(true);
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/front_end/candidat/AfficherToutCandidat.fxml")
            );

        }
    }

    @FXML
    private void modifierCandidat(ActionEvent event) {
        if (candidatActuel != null) {
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/front_end/candidat/ModifierCandidat.fxml")
            );
        }
    }

    @FXML
    private void selectCandidat(MouseEvent event) {
        candidatActuel = tab.getSelectionModel().getSelectedItem();
        idChoisi.setText("id choisi : " + candidatActuel.getId());
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
    }

//    //method to create page inside pagination view
//    private Node createPage(int pageIndex) {
//        int fromIndex = pageIndex * rowsPerPage;
//        int toIndex = Math.min(fromIndex + rowsPerPage, candidats.size());
//        tab.setItems(FXCollections.observableArrayList(candidats.subList(fromIndex, toIndex)));
//        if ((candidats.size() % 2) == 0) {
//            pagination.setPageCount(candidats.size() / rowsPerPage);
//        } else {
//            pagination.setPageCount(candidats.size() / rowsPerPage + 1);
//        }
//        return tab;
//    }
    public int getAge(Candidat candidat) {

        int age = 0;
        int dateactuel = LocalDate.now().getYear();
        age = (dateactuel - (candidat.getDateNaissance().toLocalDate().getYear()));

        return age;
    }

    @FXML
    private void Education(ActionEvent event) {

        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjouterEducation.fxml")
        );
    }

    @FXML
    private void Competence(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjoutCompetence.fxml")
        );
    }

    @FXML
    private void Experiencedetravail(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjouterExperienceDeTravail.fxml")
        );

    }

}
