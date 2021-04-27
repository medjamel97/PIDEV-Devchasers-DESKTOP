/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.MainApp;
import app.entity.Candidat;
import app.entity.CandidatureOffre;
import app.entity.OffreDeTravail;
import app.service.CandidatCrud;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class AffichertoutCandidaturesController implements Initializable {

    @FXML
    private VBox canditsContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 545619 tetbadal b afficherParSociete
        List<CandidatureOffre> listecandits = CandidatureOffreCrud.getInstance().getCandidaturesOffresBySociete(MainApp.getSession().getSocieteId());
        listecandits.forEach((candidatureOffre) -> {
            canditsContainer.getChildren().add(creerCandidatureOffreModele(candidatureOffre));
        });

    }

    private Parent creerCandidatureOffreModele(CandidatureOffre candidatureOffre) {
        try {
            AnchorPane parent = FXMLLoader.load(getClass().getResource("/app/gui/back_end/societe/candidatureOffre/ModeleCandidature.fxml"));
            Candidat candidat = CandidatCrud.getInstance().getCandidatById(candidatureOffre.getCandidatId());
            OffreDeTravail offre = OffreDeTravailCrud.getInstance().getOffreDeTravailById(candidatureOffre.getOffreDeTravailId());

            if (candidat != null) {

                Button buttonAccepter = ((Button) ((AnchorPane) parent.lookup("#ap1")).lookup("#btnconf"));
                Button buttonRefuser = ((Button) ((AnchorPane) parent.lookup("#ap1")).lookup("#btnref"));

                HBox hboxButtons = ((HBox) ((AnchorPane) parent.lookup("#ap1")).lookup("#hboxButtons"));

                switch (candidatureOffre.getEtat()) {
                    case "accepté":
                        ((AnchorPane) parent.lookup("#ap1")).getChildren().remove(hboxButtons);
                        ((Text) parent.lookup("#statusCandidature")).setText("Vous avez accepté cette candidature");
                        break;
                    case "refusé":
                        ((AnchorPane) parent.lookup("#ap1")).getChildren().remove(hboxButtons);
                        ((Text) parent.lookup("#statusCandidature")).setText("Vous avez refusé cette candidature");
                        break;
                    default:
                        ((Text) parent.lookup("#statusCandidature")).setText("En attente d'acceptation de candidature");
                        break;
                }

                ((Text) ((AnchorPane) parent.lookup("#ap1")).lookup("#nomCandidature")).setText(
                        "Candidat : " + candidat.getPrenom()
                        + " " + candidat.getNom() + " | Offre : " + offre.getNom()
                );

                buttonAccepter.setOnAction((a) -> {
                    System.out.println("acceptation candidature : " + candidatureOffre.getId());
                    candidatureOffre.setEtat("accepté");
                    CandidatureOffreCrud.getInstance().modifierEtat(candidatureOffre);
                    MainWindowController.chargerInterface(
                            getClass().getResource("/app/gui/back_end/societe/candidatureOffre/AffichertoutCandidatures.fxml")
                    );
                });
                buttonRefuser.setOnAction((a) -> {
                    System.out.println("refus candidature : " + candidatureOffre.getId());
                    candidatureOffre.setEtat("refusé");
                    CandidatureOffreCrud.getInstance().modifierEtat(candidatureOffre);
                    MainWindowController.chargerInterface(
                            getClass().getResource("/app/gui/back_end/societe/candidatureOffre/AffichertoutCandidatures.fxml")
                    );
                });

            } else {
                System.out.println("candidat is null");
            }

            return parent;

        } catch (IOException ex) {
            Logger.getLogger(AffichertoutCandidaturesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
