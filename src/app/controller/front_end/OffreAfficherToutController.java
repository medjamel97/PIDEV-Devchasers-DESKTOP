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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import static javafx.application.Application.launch;
import javafx.event.Event;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class OffreAfficherToutController implements Initializable {

    @FXML
    private VBox offreContainer;
    @FXML
    private ImageView idqr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<OffreDeTravail> listOffre = OffreDeTravailCrud.getInstance().displayOffre();
        listOffre.forEach((offreDeTravail) -> {
            offreContainer.getChildren().add(creerOffreModele(offreDeTravail));
        });
        createQR("https://www.youtube.com/watch?v=yFpkdkibvfg");
    }

    public static void main(String[] args) {
        launch(args);
    }
  public void createQR(String Event) {
        // GENERATE QR CODE
        ByteArrayOutputStream out = QRCode.from(Event).to(ImageType.PNG).withSize(200, 200).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        idqr.setImage(new Image(in));

        // SHOW QR CODE
        BorderPane root = new BorderPane();
        Image image = new Image(in);
        ImageView view = new ImageView(image);
        view.setStyle("-fx-stroke-width: 2; -fx-stroke: blue");
        root.setCenter(view);
        Scene scene = new Scene(root, 200, 200);
        
    }

    private Parent creerOffreModele(OffreDeTravail offreDeTravail) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/societe/offre_de_travail/ModeleOffre.fxml"));
            ((Text) parent.lookup("#nomOffre")).setText(offreDeTravail.getNom());
            Button buttonPostuler = (Button) parent.lookup("#btnpost");

            if (MainApp.getSession() != null) {
                CandidatureOffre candidatureOffre = CandidatureOffreCrud.getInstance()
                        .getCandidatureOffreByCandidatOffre(offreDeTravail.getId(), MainApp.getSession().getCandidatId());

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

            if (((AnchorPane) parent).getChildren().contains(buttonPostuler)) {
                ((Button) parent.lookup("#btnpost")).setOnAction((a) -> {
                    System.out.println("Bouton click postuler");
                    CandidatureOffreCrud.getInstance().ajouterCandidature(
                            new CandidatureOffre(offreDeTravail.getId(), MainApp.getSession().getCandidatId(), "non traité")
                    );
                    ((AnchorPane) parent).getChildren().remove(((Button) parent.lookup("#btnpost")));
                    MainWindowController.chargerInterface(getClass().getResource(
                            "/app/gui/front_end/societe/offre_de_travail/AfficherTout.fxml")
                    );
                });
            };

            return parent;

        } catch (IOException ex) {
            Logger.getLogger(OffreAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
