/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.OffreDeTravail;
import app.service.OffreDeTravailCrud;
import java.net.URL;
import java.sql.Types;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class OffreAjouterController implements Initializable {

    @FXML
    private TextField tfJob;
    @FXML
    private TextArea tfDesc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void offre(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/offre_de_travail/AfficherTout.fxml")
        );
    }

    @FXML
    private void manipulerOffre(ActionEvent event) {

        String nom = tfJob.getText();
        String desc = tfDesc.getText();

        OffreDeTravail offreDeTravail = new OffreDeTravail(Types.NULL, Types.NULL, nom, desc);
        OffreDeTravailCrud.getInstance().ajouterOffre(offreDeTravail);

        offre(event);

    }

}
