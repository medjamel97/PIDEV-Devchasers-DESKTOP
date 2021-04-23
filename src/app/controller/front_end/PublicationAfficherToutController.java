/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.Commentaire;
import app.entity.Publication;
import app.service.CommentaireCrud;
import app.service.PublicationCrud;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLDataException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Maher
 */
public class PublicationAfficherToutController implements Initializable {

    public int idR = 0;
    public static Publication pubActuelle;

    @FXML
    private Button btnAjout;
    @FXML
    private TextField txrecherche;
    @FXML
    private VBox pubvbox;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Publication> listPublication = PublicationCrud.getInstance().getAll();
        for (int i = 0; i < listPublication.size(); i++) {
            creepub(listPublication.get(i));
        }
    }

    public void creepub(Publication publication) {

        try {
            Parent modelePub = FXMLLoader.load(getClass().getResource("/app/gui/front_end/candidat/publication/ModelePublication.fxml"));
            pubvbox.getChildren().add(modelePub);
            ((Text) ((AnchorPane) modelePub).getChildren().get(0)).setText(publication.getTitre());
            ((Text) ((AnchorPane) modelePub).getChildren().get(1)).setText(publication.getDescription());
            ((Button) ((AnchorPane) modelePub).getChildren().get(2)).setOnAction((event) -> {
                try {
                    Supprimer(event, publication);
                } catch (SQLDataException ex) {
                    Logger.getLogger(PublicationAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            ((Button) ((AnchorPane) modelePub).getChildren().get(3)).setOnAction((event) -> {

                Modifier(event, publication);

            });

            ((Button) ((AnchorPane) modelePub).getChildren().get(6)).setOnAction((event) -> {
                String textComm = ((TextField) ((AnchorPane) modelePub).getChildren().get(5)).getText();
                Commentaire c = new Commentaire(publication.getId(), Types.NULL, textComm);
                CommentaireCrud.getInstance().AjouterCommentaire(c);
                Parent modeleComForAjout;
                try {
                    modeleComForAjout = FXMLLoader.load(getClass().getResource("/app/gui/front_end/candidat/publication/ModeleCommentaire.fxml"));
                    ((VBox) ((AnchorPane) modelePub).getChildren().get(4)).getChildren().add(modeleComForAjout);
                    ((Text) ((StackPane) ((AnchorPane) modeleComForAjout).getChildren().get(0)).getChildren().get(0))
                            .setText(c.getDescription());
                } catch (IOException ex) {
                    Logger.getLogger(PublicationAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            try {
                List<Commentaire> listCommentaire = CommentaireCrud.getInstance().getAllCommentairesByPub(publication.getId());

                if (!listCommentaire.isEmpty()) {
                    for (int i = 0; i < listCommentaire.size(); i++) {
                        Parent modeleCommentaire = FXMLLoader.load(
                                getClass().getResource("/app/gui/front_end/candidat/publication/ModeleCommenatire.fxml")
                        );
                        ((VBox) ((AnchorPane) modelePub).getChildren().get(4)).getChildren().add(modeleCommentaire);
                        ((Text) ((StackPane) ((AnchorPane) modeleCommentaire).getChildren().get(0)).getChildren().get(0))
                                .setText(listCommentaire.get(0).getDescription());
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(PublicationAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(PublicationAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajouter(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/publication/Ajout.fxml")
        );
    }

    private void Modifier(ActionEvent event, Publication publication) {

        pubActuelle = publication;
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/publication/ModifierPublication.fxml")
        );
    }

    private void Supprimer(ActionEvent event, Publication publication) throws SQLDataException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText(null);
        alert.setContentText("vous voulez vraiment supprimer cette publication ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            PublicationCrud.getInstance().SupprimerPublication(publication);
            pubActuelle = null;
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/front_end/candidat/publication/Accueil.fxml")
            );
        }
    }

    @FXML
    private void recherche(KeyEvent event) {
        pubvbox.getChildren().clear();
        List<Publication> listpublication = PublicationCrud.getInstance().findpubBytitre(txrecherche.getText());

        if (!listpublication.isEmpty()) {
            for (int i = 0; i < listpublication.size(); i++) {
                creepub(listpublication.get(i));
            }

        }
    }

}
