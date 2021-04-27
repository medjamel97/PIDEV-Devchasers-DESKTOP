/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.MainApp;
import app.entity.CandidatureOffre;
import app.entity.Categorie;
import app.entity.OffreDeTravail;
import app.service.CandidatureOffreCrud;
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
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class OffreAfficherParCategorieController implements Initializable {

    public static Categorie cats = null;
    @FXML
    private VBox offreContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<OffreDeTravail> LO = OffreDeTravailCrud.getInstance().getOffreDeTravailByCategorieId(cats.getId());
        LO.forEach((offreDeTravail) -> {
            offreContainer.getChildren().add(afficherparcat(offreDeTravail));
        });
    }

    private Parent afficherparcat(OffreDeTravail offreDeTravail) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/societe/offre_de_travail/ModeleOffre.fxml"));
            ((Text) parent.lookup("#nomOffre")).setText(offreDeTravail.getNom());
            if (MainApp.getSession() != null) {
                CandidatureOffre candidatureOffre = CandidatureOffreCrud.getInstance()
                        .getCandidatureOffreByCandidatOffre(offreDeTravail.getId(), MainApp.getSession().getCandidatId());
                Button buttonPostuler = (Button) parent.lookup("#btnpost");
                if (candidatureOffre != null) {
                    ((AnchorPane) parent).getChildren().remove(buttonPostuler);
                    switch (candidatureOffre.getEtat()) {
                        case "accepté":
                            ((Text) parent.lookup("#statusCandidature")).setText("Votre candidature a été accepté");
                            break;
                        case "refusé":
                            ((Text) parent.lookup("#statusCandidature")).setText("Votre candidature a été refusé");
                            break;
                        default:
                            ((Text) parent.lookup("#statusCandidature")).setText("En attente d'acceptation de candidature");
                            break;
                    }
                }

            } else {
                ((Text) parent.lookup("#statusCandidature")).setText("Veuillez vous connecter pour candidater");
            }
            Button buttonPostuler = (Button) parent.lookup("#btnpost");
            if (((AnchorPane) parent).getChildren().contains(buttonPostuler)) {
                ((Button) parent.lookup("#btnpost")).setOnAction((a) -> {
                    System.out.println("Bouton click postuler");
                    CandidatureOffreCrud.getInstance().ajouterCandidature(
                            new CandidatureOffre(offreDeTravail.getId(), MainApp.getSession().getCandidatId(), "non traité")
                    );
                    ((AnchorPane) parent).getChildren().remove(((Button) parent.lookup("#btnpost")));
                    MainWindowController.chargerInterface(getClass().getResource(
                            "/app/gui/front_end/societe/offre_de_travail/AfficherParCategorie.fxml")
                    );
                });
            };
            return parent;
        } catch (IOException ex) {
            Logger.getLogger(OffreAfficherParCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
