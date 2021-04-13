/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.service.RevueService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import app.entity.Revue;
import java.sql.Types;
import javafx.scene.control.TextArea;
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
    private TextArea inputDescription;

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
        Button[] stars = {star1, star2, star3, star4, star5};

        if (revue != null) {
            for (int i = 0; i < 5; i++) {
                if (i < revue.getNbEtoiles()) {
                    stars[i].setGraphic(new ImageView("app/images/mdi/star.png"));
                } else {
                    stars[i].setGraphic(new ImageView("app/images/mdi/star-outline.png"));
                }
            }
            topText.setText("Modifier revue");
            btnAjout.setText("Modifier");
            inputObjet.setText(revue.getObjet());
            inputDescription.setText(revue.getDescription());

            nbEtoiles = revue.getNbEtoiles();
        } else {
            for (int i = 0; i < 5; i++) {
                stars[i].setGraphic(new ImageView("app/images/mdi/star-outline.png"));
            }
        }

    }

    @FXML
    private void revue(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/revue/AfficherTout.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void manipuler(ActionEvent event) {
        RevueService revueService = new RevueService();

        String objet = inputObjet.getText();
        String description = inputDescription.getText();

        /*if (!revueService.ControleObjet(revue)) {
            alertObjet.setText("CIN non valable. ");
            isValid = false;
        }
        if (!revueService.ControleDescription(revue)) {
            alertDescription.setText("Role non valable. ");
            retourstr = false;
        }

        if (isValid == true) {*/
        if (RevueAfficherToutController.revueActuelle != null) {
            Revue revue = new Revue(RevueAfficherToutController.revueActuelle.getId(), nbEtoiles, objet, description, Types.NULL);
            revueService.modifierRevue(revue);
            RevueAfficherToutController.revueActuelle = null;
        } else {
            Revue revue = new Revue(nbEtoiles, objet, description, Types.NULL);
            revueService.ajouterRevue(revue);
        }
        //}

        // retour
        revue(event);
    }

    @FXML
    private void setStar1(ActionEvent event) {
        star1.setGraphic(new ImageView("app/images/mdi/star.png"));
        star2.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        star3.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        star4.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        star5.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        nbEtoiles = 1;
    }

    @FXML
    private void setStar2(ActionEvent event) {
        star1.setGraphic(new ImageView("app/images/mdi/star.png"));
        star2.setGraphic(new ImageView("app/images/mdi/star.png"));
        star3.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        star4.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        star5.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        nbEtoiles = 2;
    }

    @FXML
    private void setStar3(ActionEvent event) {
        star1.setGraphic(new ImageView("app/images/mdi/star.png"));
        star2.setGraphic(new ImageView("app/images/mdi/star.png"));
        star3.setGraphic(new ImageView("app/images/mdi/star.png"));
        star4.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        star5.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        nbEtoiles = 3;
    }

    @FXML
    private void setStar4(ActionEvent event) {
        star1.setGraphic(new ImageView("app/images/mdi/star.png"));
        star2.setGraphic(new ImageView("app/images/mdi/star.png"));
        star3.setGraphic(new ImageView("app/images/mdi/star.png"));
        star4.setGraphic(new ImageView("app/images/mdi/star.png"));
        star5.setGraphic(new ImageView("app/images/mdi/star-outline.png"));
        nbEtoiles = 4;
    }

    @FXML
    private void setStar5(ActionEvent event) {
        star1.setGraphic(new ImageView("app/images/mdi/star.png"));
        star2.setGraphic(new ImageView("app/images/mdi/star.png"));
        star3.setGraphic(new ImageView("app/images/mdi/star.png"));
        star4.setGraphic(new ImageView("app/images/mdi/star.png"));
        star5.setGraphic(new ImageView("app/images/mdi/star.png"));
        nbEtoiles = 5;
    }

}
