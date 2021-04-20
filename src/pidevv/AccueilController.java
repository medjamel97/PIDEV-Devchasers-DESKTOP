/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevv;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author Maher
 */
public class AccueilController implements Initializable {

    @FXML
    private AnchorPane affichage;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
final java.net.URL resource = getClass().getResource("PIDEV-DESKTOP/utils/haux-accidents-official-audio.mp3");
        
     //   MediaPlayer mediaPlayer = new MediaPlayer(new javafx.scene.media.Media(resource.toString()));
    }

    @FXML
    private void aff_btn(ActionEvent event) throws IOException {
        AnchorPane p = FXMLLoader.load(getClass().getResource("Affichage.fxml"));
        affichage.getChildren().setAll(p);

    }

    @FXML
    private void acc_btn(ActionEvent event) throws IOException {
        AnchorPane p = FXMLLoader.load(getClass().getResource("AjoutPub.fxml"));
        affichage.getChildren().setAll(p);
    }

    @FXML
    private void tets_(ActionEvent event) throws IOException {

    }

    DropShadow shadow = new DropShadow();

    @FXML
    private void hover_off(MouseEvent event) {
        btn1.setEffect(null);

    }

    @FXML
    private void hover_on(MouseEvent event) {
        btn1.setCursor(Cursor.HAND);
        btn1.setEffect(shadow);

    }

    @FXML
    private void hover_off2(MouseEvent event) {
        btn2.setEffect(null);
    }

    @FXML
    private void hover_on2(MouseEvent event) {
        btn2.setCursor(Cursor.HAND);
        btn2.setEffect(shadow);
    }

}
