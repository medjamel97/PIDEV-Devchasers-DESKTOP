/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.Publication;
import app.service.PublicationCrud;
import app.utils.BadWords;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.captcha.Captcha;
import org.controlsfx.control.Notifications;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Maher
 */
public class PublicationAjouterController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextArea description;
    @FXML
    private TextField titre;
    @FXML
    private Button ajouter_btn;
    @FXML
    private Label res;
    @FXML
    private Label label1;
    @FXML
    private TextField code;
    @FXML
    private Button reset;
    @FXML
    private ImageView cap;

    public Captcha setCaptcha() {
        Captcha captcha = new Captcha.Builder(250, 200)
                .addText()
                .addBackground()
                .addNoise()
                .gimp()
                .addBorder()
                .build();

        System.out.println(captcha.getImage());
        Image image = SwingFXUtils.toFXImage(captcha.getImage(), null);

        cap.setImage(image);

        return captcha;
    }
    Captcha captcha;

    public void initialize(URL url, ResourceBundle rb) {

        captcha = setCaptcha();

    }

    @FXML
    private void ajouter(ActionEvent event) {

        if (BadWords.filterText(description.getText()) || BadWords.filterText(titre.getText())) {

            String tilte = "Alerte";
            String message = "Cette application n'autorise pas ces termes";

            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            tray.setAnimationType(type);
            tray.setTitle(tilte);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.millis(3000));

            Parent root;
        } else if (titre.getText().isEmpty()) {
            // label.setText("Veuillez saisir le titre ");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir le titre");
            alert.showAndWait();
        } else if (description.getText().isEmpty()) {
            //  label.setText("Veuillez remplir la description ");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir la description");
            alert.showAndWait();
        } else if (!captcha.isCorrect(code.getText())) {

            String tilte = "Captcha";
            String message = "Non valide";
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            tray.setAnimationType(type);
            tray.setTitle(tilte);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.millis(3000));

            captcha = setCaptcha();
            code.setText("");

        } else {
            if (captcha.isCorrect(code.getText())) {

                String tilte = "Alerte";
                String message = "Votre publication est ajoutée avec succès";
                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.POPUP;

                tray.setAnimationType(type);
                tray.setTitle(tilte);
                tray.setMessage(message);
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(3000));

//            Notifications notificationBuilder = Notifications.create()
//                    .title("Succes").text("Votre publication est ajoutée avec succès").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
//                    .position(Pos.CENTER_LEFT)
//                    .onAction((eventAjoutPub) -> {
//                        System.out.println("clicked ON ");
//                    });
//            notificationBuilder.darkStyle();
//            notificationBuilder.show();
                Publication publication = new Publication(Types.NULL, titre.getText(), description.getText(), Timestamp.valueOf(LocalDateTime.now()), 0);
                PublicationCrud.getInstance().AjouterPublication(publication);

                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/candidat/publication/Accueil.fxml")
                );
            }
        }
    }

    @FXML
    private void reseting(ActionEvent event) {
        captcha = setCaptcha();
        code.setText("");
    }

}
