/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.entity.OffreDeTravail;
import app.entity.Societe;
import app.service.OffreDeTravailService;
import app.service.SocieteService;
import app.utils.ResizeHelper;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class SelectionnerSocieteController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane containerAllSociete;
    @FXML
    private TextField inputRecherche;

    private ResizeHelper resizeHelper;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SocieteService societeService = new SocieteService();
        List<Societe> listSocietes = societeService.getSociete();

        if (!listSocietes.isEmpty()) {
            int i = 0;
            int j = 0;
            for (int indexSociete = 0; indexSociete < listSocietes.size(); indexSociete++) {
                containerAllSociete.getChildren().add(creerSociete(listSocietes.get(indexSociete), i, j));
                i++;
                if (i > 3) {
                    i = 0;
                    j++;
                }
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            AnchorPane.setRightAnchor(stackPane, 0.0);
            AnchorPane.setLeftAnchor(stackPane, 0.0);
            stackPane.setPrefHeight(100);
            stackPane.getChildren().add(new Text("Aucune societe"));
            containerAllSociete.getChildren().add(stackPane);
        }
    }

    private AnchorPane creerSociete(Societe societe, int i, int j) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/revue/ModeleSociete.fxml"));
            ((Text) ((StackPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0)).setText(societe.getNomSociete());
            ((ImageView) ((StackPane) ((AnchorPane) parent).getChildren().get(1)).getChildren().get(1)).setImage(new Image("/app/images/default.jpg"));
            ((Text) ((StackPane) ((AnchorPane) parent).getChildren().get(2)).getChildren().get(0)).setText("Tel :" + societe.getNumTelSociete());
            GridPane.setColumnIndex(parent, i);
            GridPane.setRowIndex(parent, j);

            parent.setOnMouseClicked((event) -> {

                try {
                    Parent dialogParent = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/revue/DialogChoixOffre.fxml"));
                    Stage stageDialogOffres = new Stage();

                    OffreDeTravailService offreDeTravailService = new OffreDeTravailService();
                    List<OffreDeTravail> listOffre = offreDeTravailService.getOffreDeTravailBySociete(societe.getId());

                    if (!listOffre.isEmpty()) {
                        for (int indexListOffre = 0; indexListOffre < listOffre.size(); indexListOffre++) {
                            OffreDeTravail offreDeTravail = listOffre.get(indexListOffre);

                            Parent offreParent = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/revue/ModeleOffre.fxml"));
                            offreParent.setOnMouseClicked((eventClickOffre) -> {
                                stageDialogOffres.close();
                                RevueAfficherToutController.offreDeTravailActuelle = offreDeTravail;
                                RevueAfficherToutController.societeActuelle = societe;

                                MainWindowController.chargerInterface(
                                        getClass().getResource("/app/gui/societe/offre_de_travail/revue/AfficherTout.fxml")
                                );
                            });

                            ((Text) ((StackPane) ((AnchorPane) offreParent).getChildren().get(0)).getChildren().get(0))
                                    .setText(listOffre.get(indexListOffre).getJob());
                            ((Text) ((StackPane) ((AnchorPane) offreParent).getChildren().get(1)).getChildren().get(0))
                                    .setText(listOffre.get(indexListOffre).getDescription());
                            ((VBox) ((ScrollPane) ((AnchorPane) dialogParent).getChildren().get(1)).getContent()).getChildren().add(offreParent);
                        }
                    } else {
                        Parent offreParent = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/revue/ModeleOffre.fxml"));
                        ((Text) ((StackPane) ((AnchorPane) offreParent).getChildren().get(0)).getChildren().get(0))
                                .setText("Aucune offre");
                        ((Text) ((StackPane) ((AnchorPane) offreParent).getChildren().get(1)).getChildren().get(0))
                                .setText("Revenez plus tard");
                        ((VBox) ((ScrollPane) ((AnchorPane) dialogParent).getChildren().get(1)).getContent()).getChildren().add(offreParent);
                    }

                    ((Button) ((AnchorPane) dialogParent).getChildren().get(2)).setOnAction(eventDialogClose -> {
                        stageDialogOffres.close();
                    });

                    stageDialogOffres.initStyle(StageStyle.UNDECORATED);
                    stageDialogOffres.initModality(Modality.APPLICATION_MODAL);
                    stageDialogOffres.setScene(new Scene(dialogParent));

                    ResizeHelper resizeHelper = new ResizeHelper(stageDialogOffres, 40, 0);
                    stageDialogOffres.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(SelectionnerSocieteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return (AnchorPane) parent;
    }

    @FXML
    private void rechercher(KeyEvent event) {
        containerAllSociete.getChildren().clear();

        SocieteService societeService = new SocieteService();
        List<Societe> listSocietes = societeService.getSocieteParNom(inputRecherche.getText());

        if (!listSocietes.isEmpty()) {
            int i = 0;
            int j = 0;
            for (int indexSociete = 0; indexSociete < listSocietes.size(); indexSociete++) {
                containerAllSociete.getChildren().add(creerSociete(listSocietes.get(indexSociete), i, j));
                i++;
                if (i > 3) {
                    i = 0;
                    j++;
                }
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            AnchorPane.setRightAnchor(stackPane, 0.0);
            AnchorPane.setLeftAnchor(stackPane, 0.0);
            stackPane.setPrefHeight(100);
            stackPane.getChildren().add(new Text("Aucune societe"));
            containerAllSociete.getChildren().add(stackPane);
        }
    }

}
