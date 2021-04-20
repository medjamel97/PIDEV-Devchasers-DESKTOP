/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import java.net.URL;
import app.MainWindow;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class TopBarController implements Initializable {

    @FXML
    private Pane dragZone;
    @FXML
    private Button btnRevues;
    @FXML
    private Button btnInterviews;
    @FXML
    private Button OtherButton;
    @FXML
    private Button OtherButton1;
    @FXML
    private Button OtherButton2;
    @FXML
    private Button OtherButton3;
    @FXML
    private Button btnReduce;
    @FXML
    private Button btnMaximize;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnAccueil;

    public static boolean canDrag = true;
    private Button[] liens;
    private Color gray = new Color(0.9, 0.9, 0.9, 1);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        liens = new Button[]{btnRevues, btnInterviews, OtherButton, OtherButton1, OtherButton2, OtherButton3, btnAccueil};

        btnReduce.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        btnMaximize.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        btnClose.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        animateButton(btnReduce, gray, Color.BLACK, Color.BLUE, 0, true);
        animateButton(btnMaximize, gray, Color.BLACK, Color.BLUE, 0, true);
        animateButton(btnClose, new Color(0.92, 0.06, 0.15, 1), Color.BLACK, Color.BLUE, 1.0f, true);

        for (Button lien : liens) {
            lien.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            animateButton(lien, gray, Color.BLACK, Color.BLUE, 0, false);
        }

        final Delta dragDelta = new Delta();
        Stage stage = MainWindow.mainStage;
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

    @FXML
    private void revue(ActionEvent event) {
        for (Button lien : liens) {
            lien.setTextFill(Color.BLACK);
            animateButton(lien, gray, Color.BLACK, Color.BLUE, 0, false);
        }
        btnRevues.setTextFill(Color.BLUE);
        animateButton(btnRevues, gray, Color.BLUE, Color.BLUE, 0, false);

        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/societe/offre_de_travail/revue/SelectionnerSociete.fxml")
        );
    }

    @FXML
    private void interview(ActionEvent event) {
        for (Button lien : liens) {
            lien.setTextFill(Color.BLACK);
            animateButton(lien, gray, Color.BLACK, Color.BLUE, 0, false);
        }
        btnInterviews.setTextFill(Color.BLUE);
        animateButton(btnInterviews, gray, Color.BLUE, Color.BLUE, 0, false);

        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/societe/offre_de_travail/interview/AfficherTout.fxml")
        );
    }

    @FXML
    private void accueil(ActionEvent event) {
        for (Button lien : liens) {
            lien.setTextFill(Color.BLACK);
        }

        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/Accueil.fxml")
        );
    }

    // records relative x and y co-ordinates.
    class Delta {

        double x, y;
    }

    @FXML
    private void reduce(ActionEvent event
    ) {
        MainWindow.resizeHelper.minimize();
    }

    @FXML
    private void maximize(ActionEvent event
    ) {
        MainWindow.resizeHelper.switchWindowedMode();
    }

    @FXML
    private void close(ActionEvent event
    ) {
        Platform.exit();
    }

    public void animateButton(
            Button button,
            Color bgColor,
            Color fontColorStart,
            Color fontColorEnd,
            float imageBrightness,
            boolean changeGraphicNotText) {

        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(200));
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {
                Color vColor = new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 1 - frac);
                button.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };

        // on start hover
        button.setOnMouseEntered((event) -> {
            animation.stop();
            button.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));

            if (changeGraphicNotText) {
                ColorAdjust whiteEffect = new ColorAdjust();
                whiteEffect.setBrightness(imageBrightness);
                button.getGraphic().setEffect(whiteEffect);
            } else {
                button.setTextFill(fontColorEnd);
            }
        });
        // on end hover
        button.setOnMouseExited((event) -> {
            animation.play();
            if (changeGraphicNotText) {
                button.getGraphic().setEffect(null);
            } else {
                button.setTextFill(fontColorStart);
            }
        });
    }

}
