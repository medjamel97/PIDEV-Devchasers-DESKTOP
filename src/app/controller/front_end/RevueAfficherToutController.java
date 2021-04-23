/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.OffreDeTravail;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import app.service.RevueCrud;
import app.entity.Revue;
import app.entity.Societe;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class RevueAfficherToutController implements Initializable {

    public static Societe societeActuelle;
    public static OffreDeTravail offreDeTravailActuelle;
    public static Revue revueActuelle;

    @FXML
    private VBox containerAllRevue;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnAjout;

    @FXML
    private TextField inputRecherche;

    @FXML
    private Text topText;

    @FXML
    private Text noteTotale;
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;
    @FXML
    private ImageView star3;
    @FXML
    private ImageView star4;
    @FXML
    private ImageView star5;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if ((offreDeTravailActuelle != null) && (societeActuelle != null)) {

            topText.setText("Revues sur " + offreDeTravailActuelle.getNom() + " de " + societeActuelle.getNom());

            ImageView btnRetourIcon = new ImageView("app/images/mdi/arrow-left-dark.png");
            btnRetourIcon.setFitHeight(20);
            btnRetourIcon.setFitWidth(20);
            btnRetour.setGraphic(btnRetourIcon);

            List<Revue> listRevue = RevueCrud.getInstance().getRevuesParOffre(offreDeTravailActuelle.getId());

            if (!listRevue.isEmpty()) {
                float sommeNote = 0;
                int i;
                for (i = 0; i < listRevue.size(); i++) {
                    sommeNote += listRevue.get(i).getNbEtoiles();
                    containerAllRevue.getChildren().add(creerRevue(
                            "prenomCandidat",
                            "nomCandidat",
                            "app/images/default.jpg",
                            "offreDeTravail",
                            "societe",
                            listRevue.get(i)
                    ));
                }
                noteTotale.setText(String.format("%.1f", (sommeNote / i)) + "/5");
                setStarsFloat(sommeNote / i);
            } else {
                StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);
                stackPane.setPrefHeight(200);
                stackPane.getChildren().add(new Text("Aucune revue"));
                containerAllRevue.getChildren().add(stackPane);
            }

            /* makes scrolling faster
            containerAllRevue.setOnScroll((event) -> {
            double deltaY = event.getDeltaY() * 1;
            double width = scrollPane.getContent().getBoundsInLocal().getWidth();
            double vvalue = scrollPane.getVvalue();
            scrollPane.setVvalue(vvalue + -deltaY / width);
            });*/
        }
    }

    private Parent creerRevue(
            String prenomCandidatText,
            String nomCandidatText,
            String idPhotoCandidat,
            String nomOffreDeTravail,
            String nomSociete,
            Revue revue
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/ModeleRevue.fxml"));
            AnchorPane leftAnchorPane = ((AnchorPane) ((SplitPane) ((AnchorPane) parent).getChildren().get(0)).getItems().get(0));
            ((Text) leftAnchorPane.getChildren().get(0)).setText(prenomCandidatText);
            ((Text) leftAnchorPane.getChildren().get(1)).setText(nomCandidatText);
            ((ImageView) ((AnchorPane) leftAnchorPane.getChildren().get(2)).getChildren().get(0)).setImage(new Image(idPhotoCandidat));
            ((Text) leftAnchorPane.getChildren().get(3)).setText(nomOffreDeTravail);
            ((Text) leftAnchorPane.getChildren().get(4)).setText(nomSociete);

            AnchorPane rightAnchorPane = ((AnchorPane) ((SplitPane) ((AnchorPane) parent).getChildren().get(0)).getItems().get(1));
            for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
                if (indexEtoile >= revue.getNbEtoiles()) {
                    ((ImageView) rightAnchorPane.getChildren().get(indexEtoile)).setImage(new Image("/app/images/mdi/star-outline.png"));
                } else {
                    ((ImageView) rightAnchorPane.getChildren().get(indexEtoile)).setImage(new Image("/app/images/mdi/star.png"));
                }
            }
            ((Text) rightAnchorPane.getChildren().get(5)).setText(revue.getObjet());
            ((Label) ((GridPane) rightAnchorPane.getChildren().get(6)).getChildren().get(0)).setText(revue.getDescription());
            ((Button) rightAnchorPane.getChildren().get(7)).setOnAction((event) -> {
                modifierRevue(event, revue);
            });
            ((Button) rightAnchorPane.getChildren().get(8)).setOnAction((event) -> {
                supprimerRevue(event, revue);
            });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterRevue(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/Manipuler.fxml")
        );
    }

    @FXML
    private void selectionnerSociete(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/SelectionnerSociete.fxml")
        );
    }

    private void modifierRevue(ActionEvent event, Revue revue) {
        revueActuelle = revue;
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/Manipuler.fxml")
        );
    }

    private void supprimerRevue(ActionEvent event, Revue revue) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer votre revue avec id : " + revue.getId() + "?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            RevueCrud.getInstance().supprimerRevue(revue);
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/AfficherTout.fxml")
            );
        }

    }

    @FXML
    private void rechercher(KeyEvent event) {

        containerAllRevue.getChildren().clear();

        List<Revue> listRevue = RevueCrud.getInstance().getRevuesParOffreParObjet(offreDeTravailActuelle.getId(), inputRecherche.getText());

        if (!listRevue.isEmpty()) {
            for (int i = 0; i < listRevue.size(); i++) {
                containerAllRevue.getChildren().add(creerRevue(
                        "prenomCandidat",
                        "nomCandidat",
                        "app/images/default.jpg",
                        "offreDeTravail",
                        "societe",
                        listRevue.get(i)
                ));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune revue"));
            containerAllRevue.getChildren().add(stackPane);
        }
    }

    public static void information(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("information");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void setStarsFloat(float nbEtoiles) {
        ImageView[] stars = {star1, star2, star3, star4, star5};
        for (int i = 0; i < 5; i++) {
            if (nbEtoiles > i + 1) {
                stars[i].setImage(new Image("app/images/mdi/star.png"));
            } else if ((nbEtoiles > i) && (nbEtoiles < i + 1)) {
                stars[i].setImage(new Image("app/images/mdi/star-half.png"));
            } else {
                stars[i].setImage(new Image("app/images/mdi/star-outline.png"));
            }
            ColorAdjust blackEffect = new ColorAdjust();
            blackEffect.setBrightness(-1.0);
            stars[i].setEffect(blackEffect);
        }
    }
}
