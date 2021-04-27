/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.MainApp;
import app.entity.Categorie;
import app.entity.OffreDeTravail;
import app.service.CategorieCrud;
import app.service.OffreDeTravailCrud;
import java.net.URL;
import java.sql.Types;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class OffreAjouterController implements Initializable {

    @FXML
    private TextField tfJob;
    @FXML
    private TextArea tfDesc;
    @FXML
    private ComboBox<Categorie> tfCategorie;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Categorie defaultCategorie = new Categorie(0, "Aucune categorie");
        tfCategorie.getItems().add(defaultCategorie);
        tfCategorie.setValue(defaultCategorie);
        
        List<Categorie> listCategories = CategorieCrud.getInstance().DisplayCat();
        tfCategorie.getItems().addAll(listCategories);
    }

    private void offre(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/back_end/societe/offre_de_travail/AfficherMesOffres.fxml")
        );
    }

    @FXML
    private void manipulerOffre(ActionEvent event) {

        String nom = tfJob.getText();
        String desc = tfDesc.getText();
        int idCat;
        OffreDeTravail offreDeTravail;
        if (tfCategorie.getValue().getId() != 0) {
            idCat = tfCategorie.getValue().getId();
            offreDeTravail = new OffreDeTravail(idCat, MainApp.getSession().getSocieteId(), nom, desc);

            if (controleDeSaisie(offreDeTravail)) {
                OffreDeTravailCrud.getInstance().ajouterOffre(offreDeTravail);

                Notifications notificationBuilder = Notifications.create()
                        .title("Alerte").text("Offre ajouté avec succès").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                        .position(Pos.CENTER_LEFT)
                        .onAction((ActionEvent event1) -> {
                            System.out.println("clicked ON ");
                        });
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                offre(event);
            }
        } else {
            offreDeTravail = new OffreDeTravail(MainApp.getSession().getSocieteId(), nom, desc);

            if (controleDeSaisie(offreDeTravail)) {
                OffreDeTravailCrud.getInstance().ajouterOffreSansCategorie(offreDeTravail);

                Notifications notificationBuilder = Notifications.create()
                        .title("Alerte").text("Offre ajouté avec succès").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                        .position(Pos.CENTER_LEFT)
                        .onAction((ActionEvent event1) -> {
                            System.out.println("clicked ON ");
                        });
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                offre(event);
            }
        }

    }

    boolean controleDeSaisie(OffreDeTravail offre) {
        boolean isValid = true;

        if (!OffreDeTravailCrud.getInstance().controleJob(offre.getNom())) {
            creerAlerte("Objet vide");
            isValid = false;
        }

        if (!OffreDeTravailCrud.getInstance().controleDescription(offre.getDescription())) {
            creerAlerte("Description trés courte ou vide");
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
