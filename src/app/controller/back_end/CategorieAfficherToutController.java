/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.entity.Categorie;
import app.service.CategorieCrud;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class CategorieAfficherToutController implements Initializable {

    @FXML
    private TableColumn<Categorie, Integer> cat;
    @FXML
    private TableColumn<Categorie, String> ncat;
    @FXML
    private Button supcat;
    @FXML
    private Button modcat;
    @FXML
    private TableView<Categorie> tab;

    public static Categorie CategorieActuelle = null;
    ObservableList<Categorie> prds = FXCollections.observableArrayList();
    @FXML
    private Button btnajout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Categorie> listecat = CategorieCrud.getInstance().DisplayCat();
        if (!listecat.isEmpty()) {
            for (int i = 0; i < listecat.size(); i++) {
                prds.add(listecat.get(i));
            }
        }
        cat.setCellValueFactory(new PropertyValueFactory<>("id"));
        ncat.setCellValueFactory(new PropertyValueFactory<>("nom"));

        tab.setItems(prds);
    }

    @FXML
    private void SuppCat(ActionEvent event) {
        CategorieCrud.getInstance().SupprimerCat(CategorieActuelle.getId());
        CategorieActuelle = null;
        modcat.setDisable(true);
        supcat.setDisable(true);
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/offre_de_travail/categorie/AfficherTout.fxml")
        );
    }

    @FXML
    private void modifierCat(ActionEvent event) {
        if (CategorieActuelle != null) {
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/back_end/societe/offre_de_travail/categorie/Modifier.fxml")
            );
        }
    }

    @FXML
    private void choose(MouseEvent event) {

        CategorieActuelle = tab.getSelectionModel().getSelectedItem();
        modcat.setDisable(false);
        supcat.setDisable(false);
    }

    @FXML
    private void ajouter(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/offre_de_travail/categorie/Ajouter.fxml")
        );
    }
}
