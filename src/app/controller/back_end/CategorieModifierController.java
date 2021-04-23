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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class CategorieModifierController implements Initializable {

    @FXML
    private Button btnM;
    @FXML
    private TextField txtcat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Categorie c = CategorieAfficherToutController.CategorieActuelle;
        txtcat.setText(c.getNom());
    }

    private void categorie(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/offre_de_travail/categorie/AfficherTout.fxml")
        );
    }

    @FXML
    private void modifierCat(ActionEvent event) {
        Categorie c = CategorieAfficherToutController.CategorieActuelle;
        c.setNom(txtcat.getText());

        CategorieCrud.getInstance().ModifierCat(c);

        categorie(event);
    }

}
