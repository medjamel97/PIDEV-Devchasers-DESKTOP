/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.entity.CandidatureOffre;
import app.entity.Interview;
import app.service.CandidatCrud;
import app.service.CandidatureOffreCrud;
import app.service.InterviewCrud;
import app.service.OffreDeTravailCrud;
import app.service.SocieteCrud;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class InterviewAfficherToutController implements Initializable {

    @FXML
    private VBox tableau;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Parent firstRow = FXMLLoader.load(getClass().getResource("/app/gui/back_end/societe/offre_de_travail/interview/TableRowModel.fxml"));
            tableau.getChildren().add(firstRow);
        } catch (IOException ex) {
            Logger.getLogger(InterviewAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Interview> listInterview = InterviewCrud.getInstance().getAllInterviews();
        listInterview.forEach((interview) -> {
            tableau.getChildren().add(creerLigneInterview(interview));
        });
    }

    private Parent creerLigneInterview(Interview interview) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/app/gui/back_end/societe/offre_de_travail/interview/TableRowModel.fxml"));

            CandidatureOffre candidatureOffre = CandidatureOffreCrud.getInstance().getCandidaturesOffreById(interview.getCandidatureOffreId());
            String nomSociete = SocieteCrud.getInstance().getSocieteById(
                    OffreDeTravailCrud.getInstance().getOffreDeTravailById(candidatureOffre.getOffreDeTravailId()).getSocieteId()
            ).getNom();
            String nomOffre = OffreDeTravailCrud.getInstance().getOffreDeTravailById(candidatureOffre.getOffreDeTravailId()).getNom();
            String nomCandidat = CandidatCrud.getInstance().getCandidatById(candidatureOffre.getCandidatId()).getNom();

            ((Text) parent.lookup("#textSociete")).setText(nomSociete);
            ((Text) parent.lookup("#textSociete")).setFont(Font.font(null, FontWeight.NORMAL, 12));
            ((Text) parent.lookup("#textSociete")).setWrappingWidth(70);
            ((Text) parent.lookup("#textOffre")).setText(nomOffre);
            ((Text) parent.lookup("#textOffre")).setFont(Font.font(null, FontWeight.NORMAL, 12));
            ((Text) parent.lookup("#textOffre")).setWrappingWidth(70);
            ((Text) parent.lookup("#textCandidat")).setText(nomCandidat);
            ((Text) parent.lookup("#textCandidat")).setFont(Font.font(null, FontWeight.NORMAL, 12));
            ((Text) parent.lookup("#textCandidat")).setWrappingWidth(70);
            ((Text) parent.lookup("#textObjet")).setText(interview.getObjet());
            ((Text) parent.lookup("#textObjet")).setFont(Font.font(null, FontWeight.NORMAL, 12));
            ((Text) parent.lookup("#textObjet")).setWrappingWidth(70);
            ((Text) parent.lookup("#textContenu")).setText(interview.getDescription());
            ((Text) parent.lookup("#textContenu")).setFont(Font.font(null, FontWeight.NORMAL, 12));
            ((Text) parent.lookup("#textContenu")).setWrappingWidth(220);
            ((Text) parent.lookup("#textDifficulte")).setText(String.valueOf(interview.getDifficulte()));
            ((Text) parent.lookup("#textDifficulte")).setFont(Font.font(null, FontWeight.NORMAL, 12));
            ((Text) parent.lookup("#textDifficulte")).setWrappingWidth(70);
            ((Text) parent.lookup("#textDate")).setText(interview.getDateCreation().toLocalDateTime().toString());
            ((Text) parent.lookup("#textDate")).setFont(Font.font(null, FontWeight.NORMAL, 12));
            ((Text) parent.lookup("#textDate")).setWrappingWidth(70);
            ((StackPane) parent.lookup("#actionsParent")).getChildren().remove((Text) parent.lookup("#textActions"));

            for (int i = 0; i < ((HBox) parent).getChildren().size(); i++) {
                ((AnchorPane) ((HBox) parent).getChildren().get(i)).getStyleClass().clear();
                ((AnchorPane) ((HBox) parent).getChildren().get(i)).getStyleClass().add("column");
            }

            Button deleteButton = new Button("Supprimer");
            deleteButton.setTextFill(Color.WHITE);
            deleteButton.getStyleClass().add("delete-button");
            deleteButton.setOnAction((event) -> {
                supprimerInterview(interview);
            });
            ((StackPane) parent.lookup("#actionsParent")).getChildren().add(deleteButton);

            return parent;
        } catch (IOException ex) {
            Logger.getLogger(InterviewAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void supprimerInterview(Interview interview) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer votre interview ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            InterviewCrud.getInstance().supprimerInterview(interview);
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/back_end/societe/offre_de_travail/interview/AfficherTout.fxml")
            );
        }
    }
}
