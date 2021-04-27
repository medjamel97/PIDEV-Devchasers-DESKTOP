/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.MainApp;
import app.entity.Candidat;
import app.entity.CandidatureOffre;
import app.entity.Interview;
import app.entity.OffreDeTravail;
import app.entity.Societe;
import app.service.CandidatCrud;
import app.service.CandidatureOffreCrud;
import app.service.InterviewCrud;
import com.jafregle.Jafregle;
import com.jafregle.Language;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class InterviewAfficherToutController implements Initializable {

    public static Societe societeActuelle;
    public static OffreDeTravail offreDeTravailActuelle;
    public static Interview interviewActuel;
    private Difficulte defaultDifficulte;

    @FXML
    private VBox containerAllInterview;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button btnRetour;
    @FXML
    private Button btnAjout;
    @FXML
    private Text topText;
    @FXML
    private TextField inputRecherche;
    @FXML
    private ComboBox<Difficulte> comboBoxTri;

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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (MainApp.getSession() == null) {
            btnAjout.setDisable(true);
        }

        defaultDifficulte = new Difficulte("Afficher tout", -1);

        Difficulte[] listDifficulte = {
            defaultDifficulte,
            new Difficulte("Trés facile", 0),
            new Difficulte("Facile", 1),
            new Difficulte("Moyen", 2),
            new Difficulte("Difficile", 3),
            new Difficulte("Trés difficile", 4)
        };

        comboBoxTri.getItems().addAll(listDifficulte);
        comboBoxTri.valueProperty().addListener((action) -> {
            if (comboBoxTri.getValue().value != -1) {
                inputRecherche.setText("");
                List<Interview> listInterview = InterviewCrud.getInstance().getInterviewsParOffreParDifficulte(
                        offreDeTravailActuelle.getId(),
                        comboBoxTri.getValue().value
                );
                afficherInterviews(listInterview);
            } else {
                List<Interview> listInterview = InterviewCrud.getInstance().getInterviewsParOffre(
                        offreDeTravailActuelle.getId()
                );
                afficherInterviews(listInterview);
            }
        });

        ImageView btnRetourIcon = new ImageView("app/images/mdi/arrow-left-dark.png");
        btnRetourIcon.setFitHeight(20);
        btnRetourIcon.setFitWidth(20);
        btnRetour.setGraphic(btnRetourIcon);

        topText.setText("Interviews sur " + offreDeTravailActuelle.getNom() + " de " + societeActuelle.getNom());

        List<Interview> listInterview = InterviewCrud.getInstance().getInterviewsParOffre(offreDeTravailActuelle.getId());
        afficherInterviews(listInterview);
    }

    private void afficherInterviews(List<Interview> listInterview) {
        containerAllInterview.getChildren().clear();
        if (!listInterview.isEmpty()) {
            listInterview.forEach((interview) -> {
                CandidatureOffre candidatureOffre = CandidatureOffreCrud.getInstance().getCandidaturesOffreById(
                        interview.getCandidatureOffreId()
                );
                Candidat candidat = CandidatCrud.getInstance().getCandidatById(candidatureOffre.getCandidatId());
                containerAllInterview.getChildren().add(creerInterview(
                        candidat.getNom(),
                        candidat.getPrenom(),
                        candidat.getIdPhoto(),
                        offreDeTravailActuelle.getNom(),
                        societeActuelle.getNom(),
                        interview
                ));
            });
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune interview"));
            containerAllInterview.getChildren().add(stackPane);
        }

        /* makes scrolling faster
            containerAllInterview.setOnScroll((event) -> {
            double deltaY = event.getDeltaY() * 1;
            double width = scrollPane.getContent().getBoundsInLocal().getWidth();
            double vvalue = scrollPane.getVvalue();
            scrollPane.setVvalue(vvalue + -deltaY / width);
            });*/
    }

    private Parent creerInterview(
            String prenomCandidatText,
            String nomCandidatText,
            String idPhotoCandidat,
            String nomOffreDeTravail,
            String nomSociete,
            Interview interview
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/societe/offre_de_travail/interview/ModeleInterview.fxml"));

            AnchorPane leftAnchorPane = ((AnchorPane) ((HBox) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) leftAnchorPane.lookup("#prenomCandidat")).setText(prenomCandidatText);
            ((Text) leftAnchorPane.lookup("#nomCandidat")).setText(nomCandidatText);
            try {
                ((ImageView) leftAnchorPane.lookup("#imageCandidat")).setImage(new Image(idPhotoCandidat));
            } catch (Exception e) {
                ((ImageView) leftAnchorPane.lookup("#imageCandidat")).setImage(new Image("app/images/default.jpg"));
            }
            ((Text) leftAnchorPane.lookup("#nomSociete")).setText(nomOffreDeTravail);
            ((Text) leftAnchorPane.lookup("#nomOffre")).setText(nomSociete);

            AnchorPane rightAnchorPane = ((AnchorPane) ((HBox) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(2));

            HBox hBoxDifficulte = (HBox) rightAnchorPane.lookup("#hboxDifficulte");

            for (int indexDifficulte = 0; indexDifficulte < 5; indexDifficulte++) {
                if (indexDifficulte > interview.getDifficulte()) {
                    hBoxDifficulte.getChildren().remove((Rectangle) hBoxDifficulte.lookup("#rectangleDifficulte" + indexDifficulte));
                }
            }

            switch (interview.getDifficulte()) {
                case 0:
                    ((Text) hBoxDifficulte.lookup("#textDifficulte")).setText("Trés facile");
                    break;
                case 1:
                    ((Text) hBoxDifficulte.lookup("#textDifficulte")).setText("Facile");
                    break;
                case 2:
                    ((Text) hBoxDifficulte.lookup("#textDifficulte")).setText("Moyen");
                    break;
                case 3:
                    ((Text) hBoxDifficulte.lookup("#textDifficulte")).setText("Difficile");
                    break;
                case 4:
                    ((Text) hBoxDifficulte.lookup("#textDifficulte")).setText("Trés difficile");
                    break;
            }

            HBox hBoxContenu = (HBox) rightAnchorPane.lookup("#hboxContenu");
            VBox contenuContainer = (VBox) hBoxContenu.lookup("#contenuContainer");
            Pane separateur = (Pane) hBoxContenu.lookup("#separateur");
            VBox traductionContainer = (VBox) hBoxContenu.lookup("#traductionContainer");

            ((Text) contenuContainer.lookup("#objet")).setText(interview.getObjet());
            ((Text) contenuContainer.lookup("#description")).setText(interview.getDescription());
            Button btnTraduire = (Button) contenuContainer.lookup("#btnTraduire");

            Text objetTraduit = (Text) (traductionContainer.lookup("#textTraduitLabel"));
            Text descriptionTraduite = (Text) (traductionContainer.lookup("#translatedDescription"));
            Button btnMasquerTraduction = (Button) traductionContainer.lookup("#btnMasquerTraduction");

            hBoxContenu.getChildren().remove(separateur);
            traductionContainer.getChildren().remove(objetTraduit);
            traductionContainer.getChildren().remove(descriptionTraduite);
            traductionContainer.getChildren().remove(btnMasquerTraduction);
            ((Text) contenuContainer.lookup("#description")).setWrappingWidth(800);

            btnTraduire.setOnAction(action -> {
                try {
                    Jafregle jafregle = new Jafregle(Language.ENGLISH, Language.FRENCH);

                    objetTraduit.setText(jafregle.translate(interview.getObjet()));
                    descriptionTraduite.setText(jafregle.translate(interview.getDescription()));
                } catch (IOException e) {
                    System.out.println("Erreur de traduction");
                    System.out.println(e.getMessage());
                }

                hBoxContenu.getChildren().add(1, separateur);
                traductionContainer.getChildren().add(objetTraduit);
                traductionContainer.getChildren().add(descriptionTraduite);
                traductionContainer.getChildren().add(btnMasquerTraduction);
                contenuContainer.getChildren().remove(btnTraduire);

                ((Text) contenuContainer.lookup("#description")).setWrappingWidth(400);
            });
            btnMasquerTraduction.setOnAction(action -> {
                hBoxContenu.getChildren().remove(separateur);
                traductionContainer.getChildren().remove(objetTraduit);
                traductionContainer.getChildren().remove(descriptionTraduite);
                traductionContainer.getChildren().remove(btnMasquerTraduction);
                contenuContainer.getChildren().add(btnTraduire);

                ((Text) contenuContainer.lookup("#description")).setWrappingWidth(800);
            });

            HBox hBoxActionsDate = (HBox) rightAnchorPane.lookup("#hboxActionsDate");

            try {
                ((Text) hBoxActionsDate.lookup("#texteDateCreation"))
                        .setText(interview.getDateCreation().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e) {
                System.out.println("Date nulle");
            }
            Button btnModifier = (Button) hBoxActionsDate.lookup("#btnModifier");
            Button btnSupprimer = (Button) hBoxActionsDate.lookup("#btnSupprimer");

            if (MainApp.getSession() != null) {
                CandidatureOffre candidatureAssocie = CandidatureOffreCrud.getInstance().getCandidatureOffreByCandidatOffre(
                        offreDeTravailActuelle.getId(),
                        MainApp.getSession().getCandidatId()
                );
                if (candidatureAssocie != null) {
                    if (interview.getCandidatureOffreId() == candidatureAssocie.getId()) {
                        btnModifier.setOnAction((event) -> {
                            modifierInterview(event, interview);
                        });
                        btnSupprimer.setOnAction((event) -> {
                            supprimerInterview(event, interview);
                        });
                    } else {
                        hBoxActionsDate.getChildren().remove(btnModifier);
                        hBoxActionsDate.getChildren().remove(btnSupprimer);
                    }
                } else {
                    hBoxActionsDate.getChildren().remove(btnModifier);
                    hBoxActionsDate.getChildren().remove(btnSupprimer);
                }
            } else {
                hBoxActionsDate.getChildren().remove(btnModifier);
                hBoxActionsDate.getChildren().remove(btnSupprimer);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void modifierInterview(ActionEvent event, Interview interview) {
        interviewActuel = interview;
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/offre_de_travail/interview/Manipuler.fxml")
        );
    }

    private void supprimerInterview(ActionEvent event, Interview interview) {
        interviewActuel = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer votre interview ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            InterviewCrud.getInstance().supprimerInterview(interview);
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/front_end/societe/offre_de_travail/interview/AfficherTout.fxml")
            );
        }
    }

    @FXML
    private void ajouterInterview(ActionEvent event) {
        CandidatureOffre candidatureOffre;
        if (CandidatureOffreCrud.getInstance().getCandidatureOffreByCandidatOffre(
                InterviewAfficherToutController.offreDeTravailActuelle.getId(),
                MainApp.getSession().getCandidatId()
        ) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur d'ajout revue");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez d'abord avoir une candidature accepté pour cette offre pour publier une revue");
            alert.showAndWait();
        } else {
            candidatureOffre = CandidatureOffreCrud.getInstance()
                    .getCandidatureOffreByCandidatOffre(
                            InterviewAfficherToutController.offreDeTravailActuelle.getId(),
                            MainApp.getSession().getCandidatId()
                    );
            System.out.println(
                    "Candidature : " + candidatureOffre.getId()
                    + " / Candidat : " + candidatureOffre.getCandidatId()
                    + " / Offre : " + candidatureOffre.getOffreDeTravailId()
                    + " / Etat : " + candidatureOffre.getEtat());

            if (candidatureOffre.getEtat().equals("accepté")) {
                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/societe/offre_de_travail/interview/Manipuler.fxml")
                );
            }
        }
    }

    @FXML
    private void selectionnerSociete(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/offre_de_travail/interview/SelectionnerSociete.fxml")
        );
    }

    public static void information(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("information");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void rechercheParNomPrenom(KeyEvent event) {
        comboBoxTri.setValue(defaultDifficulte);

        List<Interview> listInterview = InterviewCrud.getInstance().getInterviewsParOffreParCandidat(
                offreDeTravailActuelle.getId(),
                inputRecherche.getText()
        );
        afficherInterviews(listInterview);
    }

}
