/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.entity.Categorie;
import app.entity.OffreDeTravail;
import app.service.CategorieCrud;
import app.service.OffreDeTravailCrud;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class OffreModifierController implements Initializable {

    Categorie defaultCategorie;

    @FXML
    private TextField tfJob;
    @FXML
    private TextArea tfDesc;
    @FXML
    private ComboBox<Categorie> tfCategorie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        OffreDeTravail c = OffreAfficherMesOffres.offreActuelle;

        defaultCategorie = new Categorie(0, "Aucune categorie");
        tfCategorie.getItems().add(defaultCategorie);
        if (CategorieCrud.getInstance().getCatById(c.getCategorieId()) != null) {
            tfCategorie.setValue(CategorieCrud.getInstance().getCatById(c.getCategorieId()));
        } else {
            tfCategorie.setValue(defaultCategorie);
        }

        List<Categorie> listCategories = CategorieCrud.getInstance().DisplayCat();
        tfCategorie.getItems().addAll(listCategories);

        tfJob.setText(c.getNom());
        tfDesc.setText(c.getDescription());

    }

    private void offre(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/offre_de_travail/AfficherMesOffres.fxml")
        );
    }

    @FXML
    private void ModifierOffre(ActionEvent event) {
        OffreDeTravail c = OffreAfficherMesOffres.offreActuelle;
        c.setNom(tfJob.getText());
        c.setDescription(tfDesc.getText());

        if (tfCategorie.getValue() != defaultCategorie) {
            Categorie categorie = tfCategorie.getValue();
            c.setCategorieId(categorie.getId());
            OffreDeTravailCrud.getInstance().ModifierOffre(c);
            offre(event);
        } else {
            OffreDeTravailCrud.getInstance().ModifierOffreSansCategorie(c);
            offre(event);
        }
    }
}
