/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import app.service.RevueService;
import app.entity.Revue;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class RevueAfficherToutController implements Initializable {

    RevueService revueService = new RevueService();
    public static Revue revueActuelle;

    @FXML
    private VBox containerAllRevue;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button btnRetour;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView btnRetourIcon = new ImageView("app/images/mdi/arrow-left-dark.png");
        btnRetourIcon.setFitHeight(20);
        btnRetourIcon.setFitWidth(20);
        btnRetour.setGraphic(btnRetourIcon);

        List<Revue> listRevue = revueService.getRevues();
        if (!listRevue.isEmpty()) {
            for (int i = 0; i < listRevue.size(); i++) {
                StackPane stackPaneSingleRevue = makeRevueStackPane(
                        "prenomCandidat",
                        "nomCandidat",
                        "app/images/default.jpg",
                        "offreDeTravail",
                        "societe",
                        listRevue.get(i).getNbEtoiles(),
                        listRevue.get(i).getObjet(),
                        listRevue.get(i).getDescription(),
                        listRevue.get(i)
                );
                containerAllRevue.getChildren().add(stackPaneSingleRevue);
            }
        }
        // makes scrolling faster
        containerAllRevue.setOnScroll((event) -> {
            double deltaY = event.getDeltaY() * 1;
            double width = scrollPane.getContent().getBoundsInLocal().getWidth();
            double vvalue = scrollPane.getVvalue();
            scrollPane.setVvalue(vvalue + -deltaY / width);
        });
    }

    private StackPane makeRevueStackPane(
            String prenomCandidatText,
            String nomCandidatText,
            String idPhotoCandidat,
            String nomOffreDeTravail,
            String nomSociete,
            int nbEtoiles,
            String objetText,
            String descriptionText,
            Revue revue
    ) {

        Text prenomCandidat = new Text();
        prenomCandidat.setLayoutX(4);
        prenomCandidat.setLayoutY(24);
        prenomCandidat.setStrokeType(StrokeType.OUTSIDE);
        prenomCandidat.setStrokeWidth(0);
        prenomCandidat.setText(prenomCandidatText);
        prenomCandidat.setWrappingWidth(121);

        Text nomCandidat = new Text();
        nomCandidat.setLayoutX(9);
        nomCandidat.setLayoutY(54);
        nomCandidat.setStrokeType(StrokeType.OUTSIDE);
        nomCandidat.setStrokeWidth(0);
        nomCandidat.setText(nomCandidatText);
        nomCandidat.setWrappingWidth(111);

        ImageView imageCandidat = new ImageView(idPhotoCandidat);
        imageCandidat.setFitHeight(80);
        imageCandidat.setFitWidth(80);
        imageCandidat.setLayoutX(24);
        imageCandidat.setLayoutY(68);
        imageCandidat.setPickOnBounds(true);
        imageCandidat.setPreserveRatio(true);

        Text offre = new Text();
        offre.setLayoutX(9);
        offre.setLayoutY(175);
        offre.setStrokeType(StrokeType.OUTSIDE);
        offre.setStrokeWidth(0);
        offre.setText(nomOffreDeTravail);
        offre.setWrappingWidth(111);

        Text societe = new Text();
        societe.setLayoutX(10);
        societe.setLayoutY(204);
        societe.setStrokeType(StrokeType.OUTSIDE);
        societe.setStrokeWidth(0);
        societe.setText(nomSociete);
        societe.setWrappingWidth(111);

        Pane leftPane = new Pane();
        leftPane.setStyle("-fx-background-color : white");
        leftPane.getChildren().addAll(
                nomCandidat,
                prenomCandidat,
                imageCandidat,
                societe,
                offre
        );

        ImageView[] etoiles = new ImageView[5];
        for (int i = 0; i < 5; i++) {
            ImageView etoile;
            if (nbEtoiles > i) {
                etoile = new ImageView("app/images/mdi/star.png");
            } else {
                etoile = new ImageView("app/images/mdi/star-outline.png");
            }
            etoile.setFitHeight(25);
            etoile.setFitWidth(25);
            etoile.setLayoutX(20 + (i * 60));
            etoile.setLayoutY(10);
            etoile.setPickOnBounds(true);
            etoile.setPreserveRatio(true);

            etoiles[i] = etoile;
        }

        Text objet = new Text();
        objet.setLayoutX(24);
        objet.setLayoutY(68);
        objet.setStrokeType(StrokeType.OUTSIDE);
        objet.setStrokeWidth(0);
        objet.setText(objetText);
        objet.setWrappingWidth(213);
        Font font = new Font("System Bold", 15);
        objet.setFont(font);

        Text description = new Text();
        description.setLayoutX(23);
        description.setLayoutY(98);
        description.setStrokeType(StrokeType.OUTSIDE);
        description.setStrokeWidth(0);
        description.setText(descriptionText);
        description.setWrappingWidth(410);

        Button buttonModifier = new Button();
        ImageView buttonModifierIcon = new ImageView("app/images/mdi/pencil-dark.png");
        buttonModifierIcon.setFitHeight(20);
        buttonModifierIcon.setFitWidth(20);
        buttonModifier.setGraphic(buttonModifierIcon);
        buttonModifier.setStyle("-fx-background-color : transparent");
        buttonModifier.setLayoutX(380);
        buttonModifier.setLayoutY(20);
        buttonModifier.setMnemonicParsing(false);
        buttonModifier.setOnAction((event) -> {
            modifierRevue(event, revue);
        });

        Button buttonSupprimer = new Button();
        ImageView buttonSupprimerIcon = new ImageView("app/images/mdi/trash-dark.png");
        buttonSupprimerIcon.setFitHeight(20);
        buttonSupprimerIcon.setFitWidth(20);
        buttonSupprimer.setGraphic(buttonSupprimerIcon);
        buttonSupprimer.setStyle("-fx-background-color : transparent");
        buttonSupprimer.setLayoutX(430);
        buttonSupprimer.setLayoutY(20);
        buttonSupprimer.setMnemonicParsing(false);
        buttonSupprimer.setOnAction((event) -> {
            supprimerRevue(event, revue);
        });

        Pane rightPane = new Pane();
        rightPane.setStyle("-fx-background-color : white");
        rightPane.getChildren().addAll(
                etoiles[0],
                etoiles[1],
                etoiles[2],
                etoiles[3],
                etoiles[4],
                objet,
                description,
                buttonModifier,
                buttonSupprimer
        );

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.222);
        splitPane.setPrefHeight(237);
        splitPane.setPrefWidth(645);
        splitPane.setLayoutX(8);
        splitPane.setLayoutY(8);
        splitPane.getItems().addAll(leftPane, rightPane);

        Pane mainPane = new Pane();
        mainPane.getChildren().add(splitPane);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(mainPane);

        return stackPane;
    }

    @FXML
    private void ajouterRevue(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/revue/Manipuler.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void modifierRevue(ActionEvent event, Revue revue) {
        try {
            revueActuelle = revue;
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/societe/offre_de_travail/revue/Manipuler.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void supprimerRevue(ActionEvent event, Revue revue) {
        revueService.supprimerRevue(revue);
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
    private void accueil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/gui/MainWindow.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }

}
