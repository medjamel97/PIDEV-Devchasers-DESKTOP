/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.CandidatureOffre;
import app.service.RevueCrud;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import app.entity.Revue;
import app.service.CandidatureOffreCrud;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class RevueManipulerController implements Initializable {

    static int nbEtoiles;

    @FXML
    private TextField inputObjet;

    @FXML
    private TextField inputDescription;

    @FXML
    private Text topText;

    @FXML
    private Button star1;

    @FXML
    private Button star2;

    @FXML
    private Button star3;

    @FXML
    private Button star4;

    @FXML
    private Button star5;

    @FXML
    private Button btnAjout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Revue revue = RevueAfficherToutController.revueActuelle;

        if (revue != null) {
            setStars(revue.getNbEtoiles());
            topText.setText("Modifier votre revue");
            btnAjout.setText("Modifier");
            inputObjet.setText(revue.getObjet());
            inputDescription.setText(revue.getDescription());

            nbEtoiles = revue.getNbEtoiles();
        } else {
            topText.setText("Ajouter une revue");
            btnAjout.setText("Ajouter");
            setStars(0);
        }
    }

    @FXML
    private void revue(ActionEvent event) {
        MainWindowController.chargerInterface(getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/AfficherTout.fxml"));
    }

    @FXML
    private void manipuler(ActionEvent event) {

        String objet = inputObjet.getText();
        String description = inputDescription.getText();

        if (RevueAfficherToutController.revueActuelle != null) {
            Revue revue = new Revue(
                    RevueAfficherToutController.revueActuelle.getId(),
                    nbEtoiles,
                    RevueAfficherToutController.revueActuelle.getCandidatureOffreId(),
                    objet,
                    description
            );
            if (controleDeSaisie(revue)) {
                RevueCrud.getInstance().modifierRevue(revue);
                RevueAfficherToutController.revueActuelle = null;

                MainWindowController.chargerInterface(getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/AfficherTout.fxml"));
                RevueAfficherToutController.information("Revue modifié avec succés");
            }
        } else {
            CandidatureOffre candidatureOffre;
            if (CandidatureOffreCrud.getInstance().getCandidatureOffreByCandidatOffre(2, RevueAfficherToutController.offreDeTravailActuelle.getId()) == null) {
                candidatureOffre = new CandidatureOffre(RevueAfficherToutController.offreDeTravailActuelle.getId(), 2);
                CandidatureOffreCrud.getInstance().ajouterCandidature(candidatureOffre);
            }

            candidatureOffre = CandidatureOffreCrud.getInstance()
                    .getCandidatureOffreByCandidatOffre(2, RevueAfficherToutController.offreDeTravailActuelle.getId());

            Revue revue = new Revue(candidatureOffre.getId(), nbEtoiles, objet, description);
            if (controleDeSaisie(revue)) {
                RevueCrud.getInstance().ajouterRevue(revue);
                MainWindowController.chargerInterface(getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/AfficherTout.fxml"));
                RevueAfficherToutController.information("Revue ajouté avec succés");
            }
        }
    }

    private boolean controleDeSaisie(Revue revue) {
        boolean isValid = true;

        if (!RevueCrud.getInstance().controleEtoiles(revue.getNbEtoiles())) {
            creerAlerte("Donnez une note");
            isValid = false;
        }

        if (!RevueCrud.getInstance().controleObjet(revue.getObjet())) {
            creerAlerte("Objet vide");
            isValid = false;
        }

        if (!RevueCrud.getInstance().controleDescription(revue.getDescription())) {
            creerAlerte("Description trés courte ou vide");
            isValid = false;
        }

        if (!RevueCrud.getInstance().controleBadWords(revue.getObjet())) {
            creerAlerte("Objet contient termes non autorisés");
            isValid = false;
        }

        if (!RevueCrud.getInstance().controleBadWords(revue.getDescription())) {
            creerAlerte("Description contient termes non autorisés");
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

    @FXML
    private void setStar1(ActionEvent event) {
        setStars(1);
    }

    @FXML
    private void setStar2(ActionEvent event) {
        setStars(2);
    }

    @FXML
    private void setStar3(ActionEvent event) {
        setStars(3);
    }

    @FXML
    private void setStar4(ActionEvent event) {
        setStars(4);
    }

    @FXML
    private void setStar5(ActionEvent event) {
        setStars(5);
    }

    private void setStars(int nbEtoilesLocal) {
        nbEtoiles = nbEtoilesLocal;
        Button[] stars = {star1, star2, star3, star4, star5};
        for (int i = 0; i < 5; i++) {
            if (i < nbEtoiles) {
                stars[i].setGraphic(new ImageView("app/images/mdi/star.png"));
            } else {
                stars[i].setGraphic(new ImageView("app/images/mdi/star-outline.png"));
            }
        }
    }
}
