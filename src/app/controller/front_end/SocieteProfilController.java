/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.OffreDeTravail;
import app.entity.Revue;
import app.entity.Societe;
import app.service.OffreDeTravailCrud;
import app.service.RevueCrud;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class SocieteProfilController implements Initializable {

    @FXML
    private ImageView photoSociete;
    @FXML
    private Text nomSociete;
    @FXML
    private Text dateCreation;
    @FXML
    private Text tel;
    @FXML
    private VBox listeOffre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Societe societe = SocieteAfficherToutController.societeActuelle;
        nomSociete.setText(societe.getNom());
        dateCreation.setText(societe.getDateCreation().toString());
        tel.setText(societe.getTel());

        List<OffreDeTravail> listOffreDeTravails = OffreDeTravailCrud.getInstance().getOffreDeTravailBySociete(societe.getId());
        listOffreDeTravails.forEach((offreDeTravail) -> {
            OffreAfficherToutController offreAfficherToutController = new OffreAfficherToutController();
            listeOffre.getChildren().add(offreAfficherToutController.creerOffreModele(offreDeTravail));

            List<Revue> listRevue = RevueCrud.getInstance().getRevuesParOffre(offreDeTravail.getId());
            if (listRevue.isEmpty()) {
                listeOffre.getChildren().add(new Text("Acune revue pour cette offre"));
            } else {
                listeOffre.getChildren().add(new Text("Revues pour cette offre"));
            }
            Pane pane = new Pane();
            pane.setMinHeight(100);
            listeOffre.getChildren().add(pane);
            RevueAfficherToutController revueAfficherToutController = new RevueAfficherToutController();
            VBox vBox = new VBox();
            listRevue.forEach((revue) -> {
                vBox.getChildren().add(revueAfficherToutController.creerRevue(
                        "prenomCandidatText", "nomCandidatText", "idPhotoCandidat", "nomOffreDeTravail", "nomSociete", revue
                ));
            });
            listeOffre.getChildren().add(vBox);
        });
    }

}
