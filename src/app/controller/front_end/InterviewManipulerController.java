/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.MainApp;
import app.entity.CandidatureOffre;
import app.entity.Interview;
import app.service.CandidatureOffreCrud;
import app.service.InterviewCrud;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class InterviewManipulerController implements Initializable {

    static int difficulte;

    @FXML
    private TextField inputObjet;
    @FXML
    private TextField inputDescription;
    @FXML
    private Text topText;
    @FXML
    private Button btnAjout;
    @FXML
    private ComboBox<Difficulte> comboboxDifficulte;

    public class Difficulte {

        String difficulte;
        int value;

        public Difficulte(String difficulte, int value) {
            this.difficulte = difficulte;
            this.value = value;
        }

        @Override
        public String toString() {
            return difficulte;
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Interview interview = InterviewAfficherToutController.interviewActuel;

        Difficulte[] listDifficulte = {
            new Difficulte("Trés facile", 0),
            new Difficulte("Facile", 1),
            new Difficulte("Moyen", 2),
            new Difficulte("Difficile", 3),
            new Difficulte("Trés difficile", 4)
        };

        comboboxDifficulte.getItems().addAll(listDifficulte);

        if (interview != null) {
            comboboxDifficulte.setValue(listDifficulte[interview.getDifficulte()]);
            topText.setText("Modifier votre interview");
            btnAjout.setText("Modifier");
            inputObjet.setText(interview.getObjet());
            inputDescription.setText(interview.getDescription());

            difficulte = interview.getDifficulte();
        } else {
            topText.setText("Ajouter une interview");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void interview(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/offre_de_travail/interview/AfficherTout.fxml")
        );
    }

    @FXML
    private void manipuler(ActionEvent event) {

        if (InterviewAfficherToutController.interviewActuel != null) {
            Interview interview = new Interview(
                    InterviewAfficherToutController.interviewActuel.getId(),
                    InterviewAfficherToutController.interviewActuel.getCandidatureOffreId(),
                    comboboxDifficulte.getValue().value,
                    inputObjet.getText(),
                    inputDescription.getText(),
                    Timestamp.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            );
            if (controleDeSaisie(interview)) {
                InterviewCrud.getInstance().modifierInterview(interview);
                InterviewAfficherToutController.interviewActuel = null;

                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/societe/offre_de_travail/interview/AfficherTout.fxml")
                );
                InterviewAfficherToutController.information("Interview modifié avec succés");
            }
        } else {
            CandidatureOffre candidatureOffre = CandidatureOffreCrud.getInstance()
                    .getCandidatureOffreByCandidatOffre(
                            InterviewAfficherToutController.offreDeTravailActuelle.getId(),
                            MainApp.getSession().getCandidatId()
                    );

            Interview interview = new Interview(
                    candidatureOffre.getId(),
                    difficulte,
                    inputObjet.getText(),
                    inputDescription.getText(),
                    Timestamp.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            );
            if (controleDeSaisie(interview)) {
                InterviewCrud.getInstance().ajouterInterview(interview);
                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/societe/offre_de_travail/interview/AfficherTout.fxml")
                );
                InterviewAfficherToutController.information("Interview ajouté avec succés");
            }
        }
    }

    private boolean controleDeSaisie(Interview interview) {
        boolean isValid = true;

        if (!InterviewCrud.getInstance().controleDifficulte(interview.getDifficulte())) {
            creerAlerte("Donnez une difficulte");
            isValid = false;
        }

        if (!InterviewCrud.getInstance().controleObjet(interview.getObjet())) {
            creerAlerte("Objet vide");
            isValid = false;
        }

        if (!InterviewCrud.getInstance().controleDescription(interview.getDescription())) {
            creerAlerte("Description trés courte ou vide");
            isValid = false;
        }

        if (!InterviewCrud.getInstance().controleBadWords(interview.getObjet())) {
            creerAlerte("Objet contient termes non autorisés");
            isValid = false;
        }

        if (!InterviewCrud.getInstance().controleBadWords(interview.getDescription())) {
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
}
