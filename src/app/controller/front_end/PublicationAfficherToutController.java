/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import Entities.Pdf;
import app.MainApp;
import app.entity.Commentaire;
import app.entity.Publication;
import app.service.CommentaireCrud;
import app.service.PublicationCrud;
import app.utils.sms;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
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
import javafx.scene.layout.HBox;
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
        public  static String numTelephone ;

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
            pubvbox.getChildren().add(creepub(listPublication.get(i)));

        }
    }

    public Parent creepub(Publication publication) {
        Parent modelePub;
        try {
            
            modelePub = FXMLLoader.load(getClass().getResource("/app/gui/front_end/candidat/publication/ModelePublication.fxml"));
            ((Text) ((AnchorPane) modelePub).getChildren().get(0)).setText(publication.getTitre());
            ((Text) ((AnchorPane) modelePub).getChildren().get(1)).setText(publication.getDescription());
            ((Text) ((AnchorPane) modelePub).getChildren().get(8)).setText(publication.getDate().toLocalDateTime().toLocalDate().toString());
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
                Commentaire c = new Commentaire(publication.getId(), MainApp.getSession().getId(), textComm);
                CommentaireCrud.getInstance().AjouterCommentaire(c);
                VBox commentaireContainer = ((VBox) ((AnchorPane) modelePub).getChildren().get(4));               
                if (!commentaireContainer.getChildren().contains(commentaireContainer)) {
                    commentaireContainer.getChildren().add(creerCommentaire(c, commentaireContainer));
                }
                // SMS   
              numTelephone ="+21623292574";
              sms s = new sms();
              s.send("Un commentaire a ètè ajouté",numTelephone);
                
                
                
                
                
            });

            List<Commentaire> listCommentaire = CommentaireCrud.getInstance().getAllCommentairesByPub(publication.getId());
            if (!listCommentaire.isEmpty()) {

                for (int i = 0; i < listCommentaire.size(); i++) {
                    Commentaire commentaire = listCommentaire.get(i);

                    VBox commentaireContainer = ((VBox) ((AnchorPane) modelePub).getChildren().get(4));
                    commentaireContainer.getChildren().add(creerCommentaire(commentaire, commentaireContainer));
                }
            }
            
            return modelePub;

        } catch (IOException ex) {
            Logger.getLogger(PublicationAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Parent creerCommentaire(Commentaire commentaire, VBox commentaireContainer) {
        try {
            Parent modeleCommentaire = FXMLLoader.load(
                    getClass().getResource("/app/gui/front_end/candidat/publication/ModeleCommentaire.fxml")
            );

            HBox hboxContenuCommentaire = (HBox) ((StackPane) ((AnchorPane) modeleCommentaire).getChildren().get(0)).getChildren().get(0);

            //Strin nomDestinataire = UserCrud.getInstance().g
            ((Text) hboxContenuCommentaire.getChildren().get(0)).setText(commentaire.getDescription());

            Text storedDescription = ((Text) hboxContenuCommentaire.getChildren().get(0));
            ((Button) hboxContenuCommentaire.getChildren().get(1)).setOnAction(event -> {
                System.out.println("click mod");

                HBox hBox = new HBox();

                TextField textField = new TextField(storedDescription.getText());
                Button buttonConfirmer = new Button();
                buttonConfirmer.setText("Confirmer la modif");
                buttonConfirmer.setOnAction(value -> {
                    commentaire.setDescription(textField.getText());
                    CommentaireCrud.getInstance().ModiferCommentaire(commentaire);

                    commentaireContainer.getChildren().removeAll();
                    storedDescription.setText(textField.getText());
                    commentaireContainer.getChildren().remove(hBox);
                });

                hBox.getChildren().addAll(textField, buttonConfirmer);

                commentaireContainer.getChildren().remove(storedDescription);
                commentaireContainer.getChildren().add(hBox);
            });
            ((Button) hboxContenuCommentaire.getChildren().get(2)).setOnAction(event -> {
                System.out.println("click supp");
                System.out.println(commentaire.getId());
                commentaireContainer.getChildren().removeAll(modeleCommentaire);
                CommentaireCrud.getInstance().SupprimerCommentaire(commentaire);

            });
            return modeleCommentaire;
        } catch (IOException ex) {
            Logger.getLogger(PublicationAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
             System.out.println("test 1");
        pubvbox.getChildren().clear();
         System.out.println("test 2");
        List <Publication> listpublication = PublicationCrud.getInstance().findpubBytitre(txrecherche.getText());
        System.out.println("test 3");
        if (!listpublication.isEmpty()) {
            for (int i = 0; i < listpublication.size(); i++) {
                 pubvbox.getChildren().add(creepub(listpublication.get(i)));
            }
            System.out.println("test 4");
        }
    }

    private void pdf_fnct(ActionEvent event) {
        
    Pdf p = new Pdf();
  //  p.add("Mes",.getReclamtion(), tab_Recselected.getType(),tab_Recselected.getDate().toString(),tab_Recselected.getStatRec(),tab_Recselected.getSujet());
  
    }
}
