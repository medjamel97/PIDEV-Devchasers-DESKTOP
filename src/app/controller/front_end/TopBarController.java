/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.MainApp;
import app.entity.Categorie;
import app.service.CategorieCrud;
import java.net.URL;
import app.utils.Animations;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class TopBarController implements Initializable {

    public static boolean canDrag = true;
    private Button[] liens;
    private final Color gray = new Color(0.9, 0.9, 0.9, 1);
    private final Color blue = new Color(0.14, 0.26, 0.67, 1);
    private final Color dark = new Color(0.20, 0.23, 0.25, 1);

    private VBox panelUserActionsNode;
    private VBox panelOffreActionsNode;

    @FXML
    private Pane dragZone;
    @FXML
    private Button btnReduce;
    @FXML
    private Button btnMaximize;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnAccueil;
    @FXML
    private Button btnProfil;
    @FXML
    private Button btnSociete;
    @FXML
    private Button btnOffreDeTravail;
    @FXML
    private Button btnMission;
    @FXML
    private Button btnRevue;
    @FXML
    private Button btnInterview;
    @FXML
    private Button btnFormations;
    @FXML
    private Button btnEvenement;
    @FXML
    private HBox hboxAthentification;
    @FXML
    private Button btnAuthentification;
    @FXML
    private HBox userBadge;
    @FXML
    private Text userEmail;
    @FXML
    private VBox categoriesContainer;
    @FXML
    private Button btnOffreExpandable;
    @FXML
    private AnchorPane panelContainerUserActions;
    @FXML
    private AnchorPane panelContainerOffreActions;
    @FXML
    private VBox panelUserActions;
    @FXML
    private VBox panelOffreActions;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        panelUserActionsNode = panelUserActions;
        panelContainerUserActions.getChildren().clear();

        panelOffreActionsNode = panelOffreActions;
        panelContainerOffreActions.getChildren().clear();

        List<Categorie> listCategories = CategorieCrud.getInstance().DisplayCat();
        if (listCategories.isEmpty()) {
            categoriesContainer.getChildren().add(new StackPane(new Text("Pas de categories")));
        }
        listCategories.forEach(listCategorie -> {
            try {
                Parent parentCategorie = FXMLLoader.load(
                        getClass().getResource("/app/gui/front_end/societe/offre_de_travail/categorie/ModeleCategorie.fxml")
                );
                ((Text) ((StackPane) ((AnchorPane) parentCategorie).getChildren().get(0)).getChildren().get(0))
                        .setText(listCategorie.getNom());

                ((AnchorPane) parentCategorie)
                        .setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                ((AnchorPane) parentCategorie).setOnMouseMoved(event -> {
                    ((AnchorPane) parentCategorie)
                            .setBackground(new Background(new BackgroundFill(gray, CornerRadii.EMPTY, Insets.EMPTY)));
                });

                ((AnchorPane) parentCategorie).setOnMouseExited(event -> {
                    ((AnchorPane) parentCategorie)
                            .setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                });

                ((AnchorPane) parentCategorie).setPadding(new Insets(10, 0, 10, 0));

                ((AnchorPane) parentCategorie).setOnMouseClicked(event -> {
                    goToLink("/app/gui/front_end/societe/offre_de_travail/AfficherParCategorie.fxml");
                    panelContainerOffreActions.getChildren().clear();
                    btnOffreExpandable.setTextFill(blue);
                    Animations.animateButton(btnOffreExpandable, gray, blue, blue, 0, false);
                });

                categoriesContainer.getChildren().add(parentCategorie);
            } catch (IOException ex) {
                Logger.getLogger(TopBarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        if (MainApp.getSession() == null) {
            hboxAthentification.setVisible(true);
            btnProfil.setVisible(false);
            userBadge.setVisible(false);

            btnAuthentification.setText("Connexion");
        } else {
            hboxAthentification.setVisible(false);
            btnProfil.setVisible(true);
            userBadge.setVisible(true);

            userEmail.setText(MainApp.getSession().getEmail());
        }

        liens = new Button[]{
            btnAccueil, btnProfil, btnSociete,
            btnOffreDeTravail, btnMission, btnRevue,
            btnInterview, btnFormations, btnEvenement,
            btnOffreExpandable
        };

        btnReduce.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        btnMaximize.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        btnClose.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Animations.animateButton(btnReduce, gray, dark, blue, 0, true);
        Animations.animateButton(btnMaximize, gray, dark, blue, 0, true);
        Animations.animateButton(btnClose, new Color(0.92, 0.06, 0.15, 1), dark, blue, 1.0f, true);

        for (Button lien : liens) {
            lien.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, gray, dark, blue, 0, false);
        }

        final Delta dragDelta = new Delta();
        Stage stage = MainApp.mainStage;
        dragZone.setOnMousePressed((MouseEvent mouseEvent) -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
        });
        dragZone.setOnMouseDragged((MouseEvent mouseEvent) -> {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });
    }

    // records relative x and y co-ordinates.
    class Delta {

        double x, y;
    }

    @FXML
    private void expandOffreDeTravail(ActionEvent event) {
        if (panelContainerOffreActions.getChildren().contains(panelOffreActionsNode)) {
            panelContainerOffreActions.getChildren().remove(panelOffreActions);
        } else {
            panelContainerOffreActions.getChildren().add(panelOffreActionsNode);
        }
    }

    @FXML
    private void showUserAction(MouseEvent event) {
        if (panelContainerUserActions.getChildren().contains(panelUserActionsNode)) {
            panelContainerUserActions.getChildren().remove(panelUserActions);
        } else {
            panelContainerUserActions.getChildren().add(panelUserActionsNode);
        }
    }

    @FXML
    private void messagerie(ActionEvent event) {
        goToLink("/app/gui/front_end/candidat/messagerie/Messagerie.fxml");
        
        showUserAction(null);
    }

    @FXML
    private void deconnexion(ActionEvent event) {
        MainApp.getInstance().deconnexion();
    }

    @FXML
    private void authentification(ActionEvent event) {
        MainApp.getInstance().deconnexion();
    }

    @FXML
    private void accueil(ActionEvent event) {
        goToLink("/app/gui/front_end/candidat/publication/Accueil.fxml");

        btnAccueil.setTextFill(blue);
        Animations.animateButton(btnAccueil, gray, blue, blue, 0, false);
    }

    @FXML
    private void profil(ActionEvent event) {
        goToLink("/app/gui/front_end/candidat/AfficherToutCandidat.fxml");

        btnProfil.setTextFill(blue);
        Animations.animateButton(btnProfil, gray, blue, blue, 0, false);
    }

    @FXML
    private void societe(ActionEvent event) {
        goToLink("/app/gui/front_end/societe/AfficherTout.fxml");

        btnSociete.setTextFill(blue);
        Animations.animateButton(btnSociete, gray, blue, blue, 0, false);
    }

    @FXML
    private void offreDeTravail(ActionEvent event) {
        goToLink("/app/gui/front_end/societe/offre_de_travail/AfficherTout.fxml");

        panelContainerOffreActions.getChildren().clear();
        btnOffreExpandable.setTextFill(blue);
        Animations.animateButton(btnOffreExpandable, gray, blue, blue, 0, false);
    }

    @FXML
    private void revue(ActionEvent event) {
        goToLink("/app/gui/front_end/societe/offre_de_travail/revue/SelectionnerSociete.fxml");

        panelContainerOffreActions.getChildren().clear();
        btnOffreExpandable.setTextFill(blue);
        Animations.animateButton(btnOffreExpandable, gray, blue, blue, 0, false);
    }

    @FXML
    private void interview(ActionEvent event) {
        goToLink("/app/gui/front_end/societe/offre_de_travail/revue/SelectionnerSociete.fxml");

        panelContainerOffreActions.getChildren().clear();
        btnOffreExpandable.setTextFill(blue);
        Animations.animateButton(btnOffreExpandable, gray, blue, blue, 0, false);
    }

    @FXML
    private void mission(ActionEvent event) {
        goToLink("/app/gui/front_end/societe/mission/affichermission.fxml");

        btnMission.setTextFill(blue);
        Animations.animateButton(btnMission, gray, blue, blue, 0, false);
    }

    @FXML
    private void formation(ActionEvent event) {
        goToLink("/app/gui/front_end/societe/formation/AfficherTout.fxml");

        btnFormations.setTextFill(blue);
        Animations.animateButton(btnFormations, gray, blue, blue, 0, false);
    }

    @FXML
    private void evenement(ActionEvent event) {
        goToLink("/app/gui/front_end/societe/evenement/AfficherTout.fxml");

        btnEvenement.setTextFill(blue);
        Animations.animateButton(btnEvenement, gray, blue, blue, 0, false);
    }

    @FXML
    private void reduce(ActionEvent event) {
        MainApp.resizeHelper.minimize();
    }

    @FXML
    private void maximize(ActionEvent event) {
        MainApp.resizeHelper.switchWindowedMode();
    }

    @FXML
    private void close(ActionEvent event) {
        Platform.exit();
    }

    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(dark);
            Animations.animateButton(lien, gray, dark, blue, 0, false);
        }
        MainWindowController.chargerInterface(
                getClass().getResource(link)
        );
    }
}
