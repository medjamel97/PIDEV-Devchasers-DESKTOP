/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.back_end;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class MainWindowController implements Initializable {

    static AnchorPane staticContent;

    @FXML
    private AnchorPane content;
    @FXML
    private AnchorPane sideBar;
    @FXML
    private AnchorPane topBar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            staticContent = content;

            Parent sideBarParent = FXMLLoader.load(getClass().getResource("/app/gui/back_end/SideBar.fxml"));
            AnchorPane.setTopAnchor(sideBarParent, 0.0);
            AnchorPane.setBottomAnchor(sideBarParent, 0.0);
            AnchorPane.setRightAnchor(sideBarParent, 0.0);
            AnchorPane.setLeftAnchor(sideBarParent, 0.0);
            sideBar.getChildren().add(sideBarParent);

            Parent topBarParent = FXMLLoader.load(getClass().getResource("/app/gui/back_end/TopBar.fxml"));
            AnchorPane.setTopAnchor(topBarParent, 0.0);
            AnchorPane.setBottomAnchor(topBarParent, 0.0);
            AnchorPane.setRightAnchor(topBarParent, 0.0);
            AnchorPane.setLeftAnchor(topBarParent, 0.0);
            topBar.getChildren().add(topBarParent);

        } catch (IOException e) {
            System.out.println(e.getMessage() + "/" + e.getCause());
            e.printStackTrace();
        }
    }

    public static void chargerInterface(URL location) {
        staticContent.getChildren().clear();
        System.out.println("Loading content in back_end : " + location);
        try {
            Parent parent = FXMLLoader.load(location);
            AnchorPane.setTopAnchor(parent, 0.0);
            AnchorPane.setBottomAnchor(parent, 0.0);
            AnchorPane.setRightAnchor(parent, 0.0);
            AnchorPane.setLeftAnchor(parent, 0.0);
            staticContent.getChildren().add(parent);
        } catch (IOException e) {
            System.out.print("Erreur d'affichage : " + e.getMessage() + " " + e.getCause());
        }
    }

}
