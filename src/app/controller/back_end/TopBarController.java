/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import app.MainApp;
import app.utils.Animations;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
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

    private final Color gray = new Color(0.9, 0.9, 0.9, 1);
    final Delta dragDelta = new Delta();

    private VBox panelUserActionsNode;

    @FXML
    private Pane dragZone;
    @FXML
    private Button btnReduce;
    @FXML
    private Button btnMaximize;
    @FXML
    private Button btnClose;
    @FXML
    private AnchorPane panelContainerUserActions;
    @FXML
    private Text userEmail;
    @FXML
    private VBox panelUserActions;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userEmail.setText(MainApp.getSession().getEmail());

        panelUserActionsNode = panelUserActions;
        panelContainerUserActions.getChildren().clear();

        btnReduce.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        btnMaximize.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        btnClose.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Animations.animateButton(btnReduce, gray, Color.BLACK, Color.BLUE, 0, true);
        Animations.animateButton(btnMaximize, gray, Color.BLACK, Color.BLUE, 0, true);
        Animations.animateButton(btnClose, new Color(0.92, 0.06, 0.15, 1), Color.BLACK, Color.BLUE, 1.0f, true);

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

    @FXML
    private void showUserAction(MouseEvent event) {
        if (panelContainerUserActions.getChildren().contains(panelUserActionsNode)) {
            panelContainerUserActions.getChildren().remove(panelUserActions);
        } else {
            panelContainerUserActions.getChildren().add(panelUserActionsNode);
        }
    }

    @FXML
    private void deconnexion(ActionEvent event) {
        MainApp.getInstance().deconnexion();
    }

    @FXML
    private void reduce(ActionEvent event) {
        MainApp.getInstance().resizeHelper.minimize();
    }

    @FXML
    private void maximize(ActionEvent event) {
        MainApp.getInstance().resizeHelper.switchWindowedMode();
    }

    @FXML
    private void close(ActionEvent event) {
        Platform.exit();
    }

    // records relative x and y co-ordinates.
    class Delta {

        double x, y;
    }

}
