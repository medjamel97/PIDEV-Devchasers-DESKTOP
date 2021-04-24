/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.OffreDeTravail;
import app.service.OffreDeTravailCrud;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class OffreAfficherToutController implements Initializable {

    @FXML
    private VBox offreContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<OffreDeTravail> listOffre = OffreDeTravailCrud.getInstance().displayOffre();
        listOffre.forEach((offreDeTravail) -> {
            offreContainer.getChildren().add(creerOffreModele(offreDeTravail));
        });
    }

    private Parent creerOffreModele(OffreDeTravail offreDeTravail) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/societe/offre_de_travail/ModeleOffre.fxml"));
            ((Text) parent.lookup("#nomOffre")).setText(offreDeTravail.getNom());

            return parent;

        } catch (IOException ex) {
            Logger.getLogger(OffreAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
