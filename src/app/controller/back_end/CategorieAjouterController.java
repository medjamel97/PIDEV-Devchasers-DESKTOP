/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.entity.Categorie;
import app.service.CategorieCrud;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class CategorieAjouterController implements Initializable {

    @FXML
    private TextField cat;
    @FXML
    private Button ajout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void categorie(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/offre_de_travail/categorie/AfficherTout.fxml")
        );
    }

    @FXML
    private void ajouterCat(ActionEvent event) {

        String nomcategorie = cat.getText();

        Categorie categorie = new Categorie(nomcategorie);

        if (controleDeSaisie(categorie)) {

            CategorieCrud.getInstance().ajouterCat(categorie);

            categorie(event);
        }
    }

    private boolean controleDeSaisie(Categorie cat) {
        boolean isValid = true;

        if (!CategorieCrud.getInstance().controleNomcat(cat.getNom())) {
            creerAlerte("Objet vide");
            isValid = false;
        }

        return isValid;
    }

    private void creerAlerte(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
