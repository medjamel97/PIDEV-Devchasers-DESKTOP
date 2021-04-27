/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.entity.Candidat;
import app.service.CandidatCrud;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author
 */
public class CandidatAfficherToutController implements Initializable {

    ObservableList<Candidat> candidats = FXCollections.observableArrayList();
    public static Candidat candidatActuel;
    private final static int rowsPerPage = 2;

    @FXML
    private TextField filterField;
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
    private TableColumn<Candidat, String> age;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //pagination.setPageFactory(this::createPage);
        List<Candidat> listCandidats = CandidatCrud.getInstance().getCandiadats();

        if (!listCandidats.isEmpty()) {
            for (int i = 0; i < listCandidats.size(); i++) {
                Candidat candidat = listCandidats.get(i);
                candidat.setAge(getAge(listCandidats.get(i)));
                candidats.add(candidat);
            }
        }

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tabPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tabSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        tabTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));

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

    public int getAge(Candidat candidat) {

        int age = 0;
        int dateactuel = LocalDate.now().getYear();
        age = (dateactuel - (candidat.getDateNaissance().toLocalDate().getYear()));

        return age;
    }

    @FXML
    private void selectCandidat(MouseEvent event) {
        candidatActuel = tab.getSelectionModel().getSelectedItem();
    }

    //method to create page inside pagination view
    /*private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, candidats.size());
        tab.setItems(FXCollections.observableArrayList(candidats.subList(fromIndex, toIndex)));
        if ((candidats.size() % 2) == 0) {
            pagination.setPageCount(candidats.size() / rowsPerPage);
        } else {
            pagination.setPageCount(candidats.size() / rowsPerPage + 1);
        }
        return tab;
    }*/
//    @FXML
//    private void excel(ActionEvent event) throws IOException {
//              FileChooser chooser = new FileChooser();
//        Set extension filter
//        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(.xls)", ".xls");
//        chooser.getExtensionFilters().add(filter);
//        Show save dialog
//        File file = chooser.showSaveDialog(excel.getScene().getWindow());
//        CandidatCrud m = new CandidatCrud();
//       if (file != null) {
//           m.Excel(file) ; }    
}
