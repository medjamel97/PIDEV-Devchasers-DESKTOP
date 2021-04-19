/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevv;

import Entities.publication;
import Service.publicationService;
import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 * FXML Controller class
 *
 * @author Maher
 */
public class AffichageController implements Initializable {

    public int idR = 0;
    @FXML
    private TableColumn<publication, String> description;
    @FXML
    private TableColumn<publication, String> titre;
    @FXML
    private TableView<publication> affichage;

    private ObservableList<publication> RecData = FXCollections.observableArrayList();
    @FXML
    private TableColumn<publication, Date> Date;
    @FXML
    private TableColumn<publication, String> nbr_like;
    @FXML
    private TextField txrecherche;
    @FXML
    private Button btn_recherche;
    @FXML
    private TableColumn<publication, String> nbr_dislike;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    public static publication pubActuelle;
    @FXML
    private Text idPubActuelle;
    @FXML
    private Button btnAjout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pubActuelle = null;
        btnSupprimer.setDisable(true);
        btnModifier.setDisable(true);
//         List<publication> listRec= new ArrayList<publication>();
//        publicationService rs =  new publicationService();
//        listRec = rs.getAll();
//        RecData.clear();
//        RecData.addAll(listRec);
//        affichage.setItems(RecData);
//        
//        description.setCellValueFactory(
//            new PropertyValueFactory<>("description")
//        );
//        titre.setCellValueFactory(
//            new PropertyValueFactory<>("titre")
//        );
//        Date.setCellValueFactory(
//            new PropertyValueFactory<>("Date")
//        );
//          nbr_like.setCellValueFactory(
//            new PropertyValueFactory<>("nbr_like")
//        );
//      
        loadDate();
        List<publication> listFed = new ArrayList<publication>();
        publicationService rs = new publicationService();
        listFed = rs.getAll();
        RecData.clear();
        RecData.addAll(listFed);
        affichage.setItems(RecData);
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        nbr_like.setCellValueFactory(new PropertyValueFactory<>("nbr_like"));
        nbr_dislike.setCellValueFactory(new PropertyValueFactory<>("all_like"));
        Recherche();

    }

    private void jm_btn(ActionEvent event) {
//        publication p = new publication();
//        
//       int nb = p.getNbr_like();
//       nb++;
        publicationService ps = new publicationService();
//        
        publication pubSelected = (publication) affichage.getSelectionModel().getSelectedItem();
        publication p = new publication();
        p = ps.findpublicationById((pubSelected.getId()));
//        ps.Add_nbr_like(idR, nb);
        ps.incrementerjaime(p.getId());
        affichage.refresh();

    }

    @FXML
    private void recherche(ActionEvent event) {

    }

    public void Recherche() {
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<publication> filteredData = new FilteredList<>(RecData, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(publication -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (publication.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (String.valueOf(publication.getId()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<publication> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(affichage.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        affichage.setItems(sortedData);
    }

    private void resetTableData() {

        List<publication> listRec = new ArrayList<>();
        publicationService rs = new publicationService();
        listRec = rs.getAll();
        ObservableList<publication> data = FXCollections.observableArrayList(listRec);
        affichage.setItems(data);

    }

    private void loadDate() {
        ObservableList<publication> abList = FXCollections.observableArrayList();
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        nbr_like.setCellValueFactory(new PropertyValueFactory<>("nbr_like"));
        nbr_dislike.setCellValueFactory(new PropertyValueFactory<>("all_like"));

        publicationService rt = new publicationService();
        //List old = rt.listAbonnement();
        List old = rt.getAll();
        abList.addAll(old);
        affichage.setItems(abList);
        affichage.refresh();
    }

    @FXML
    private void ajouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pidevv/AjoutPub.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print("Erreur d'affichage : " + e.getMessage() + " " + e.getCause());
        }
    }

    @FXML
    private void Modifier(ActionEvent event) {
        if (pubActuelle != null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/pidevv/ModifPub.fxml"));
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
    private void Supprimer(ActionEvent event) throws SQLDataException {
        publicationService ps = new publicationService();
        ps.SupprimerPublication();
        pubActuelle = null;
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pidevv/Affichage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }

    }

    @FXML
    private void changerPubActuelle(MouseEvent event) {

        pubActuelle = affichage.getSelectionModel().getSelectedItem();
        idPubActuelle.setText("id choisi : " + pubActuelle.getId());
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
    }

}
