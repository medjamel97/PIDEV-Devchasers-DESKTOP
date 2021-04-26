/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.entity.OffreDeTravail;
import app.service.OffreDeTravailCrud;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class OffreModifierController implements Initializable {

    @FXML
    private TextField textJob;
    @FXML
    private TextArea textDesc;
    @FXML
    private Button modifieroffre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        OffreDeTravail c = OffreAfficherMesOffres.offreActuelle;
        textJob.setText(c.getNom());
        textDesc.setText(c.getDescription());

    }

    private void offre(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/offre_de_travail/AfficherMesOffres.fxml")
        );
    }

    @FXML
    private void ModiferOffre(ActionEvent event) {
        OffreDeTravail c = OffreAfficherMesOffres.offreActuelle;
        c.setNom(textJob.getText());
        c.setDescription(textDesc.getText());

        OffreDeTravailCrud.getInstance().ModifierOffre(c);

        offre(event);

    }

}
