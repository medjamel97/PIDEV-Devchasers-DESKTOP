/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.MainApp;
import app.entity.Candidat;
import app.entity.User;
import app.service.CandidatCrud;
import app.service.UserCrud;
import java.net.URL;
import java.sql.Types;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class InscriptionCandidatController implements Initializable {

    Delta dragDelta = new Delta();
    Stage stage;

    @FXML
    private TextField inputEmail;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private TextField inputPrenom;
    @FXML
    private TextField inputNom;
    @FXML
    private ComboBox<String> inputSexe;
    @FXML
    private TextField inputTelephone;
    @FXML
    private DatePicker inputDate;
    @FXML
    private Button btnPageConnexion;
    @FXML
    private Button btnEspaceSociete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnPageConnexion.setOnMouseMoved((event) -> {
            btnPageConnexion.setTextFill(new Color(0.18, 0.33, 0.83, 1));
        });
        btnPageConnexion.setOnMouseExited((event) -> {
            btnPageConnexion.setTextFill(Color.BLACK);
        });

        btnEspaceSociete.setOnMouseMoved((event) -> {
            btnEspaceSociete.setTextFill(new Color(0.18, 0.33, 0.83, 1));
        });
        btnEspaceSociete.setOnMouseExited((event) -> {
            btnEspaceSociete.setTextFill(Color.BLACK);
        });

        stage = MainApp.mainStage;

        inputSexe.getItems().add("feminin");
        inputSexe.getItems().add("masculin");
    }

    @FXML
    private void startDrag(MouseEvent event) {
        dragDelta.x = stage.getX() - event.getScreenX();
        dragDelta.y = stage.getY() - event.getScreenY();
    }

    @FXML
    private void drag(MouseEvent event) {
        stage.setX(event.getScreenX() + dragDelta.x);
        stage.setY(event.getScreenY() + dragDelta.y);
    }

    @FXML
    private void inscription(ActionEvent event) {
      
        int idCandidat = CandidatCrud.getInstance().getLastId() + 1;
    

        Candidat candidat = new Candidat(
                idCandidat,
                inputNom.getText(),
                inputPrenom.getText(),
                java.sql.Date.valueOf(inputDate.getValue()),
                inputSexe.getValue(),
                inputTelephone.getText(),
                null
        );

        User user = new User(
                idCandidat,
                Types.NULL,
                inputEmail.getText(),
                "[\n" + "\"ROLE_CANDIDAT\"\n" + "]",
                UserCrud.getInstance().encodePassword(inputPassword.getText()),
                true,
                true
        );
  if (inputNom.getText().isEmpty() 
			|inputPrenom.getText().isEmpty() | inputSexe.getValue().isEmpty() | inputTelephone.getText().isEmpty()) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Erreur").text("veillez remplir les champs vides ").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.CENTER_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked ON ");
                        }
                    });
            notificationBuilder.show();

            Parent root;
        } 
 
  
  
        CandidatCrud.getInstance().ajouterCandidat(candidat);
        UserCrud.getInstance().ajouterUtilisateur(user);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succés d'inscription");
        alert.setHeaderText(null);
        alert.setContentText("Vous êtes bien inscrit");
        alert.showAndWait();

        System.out.println();
        System.out.println("USER : " + user.toString());
        System.out.println();
        System.out.println("CANDIDAT : " + candidat.toString());

        MainApp.getInstance().loadConnexion();
    }

    @FXML
    private void pageConnexion(ActionEvent event) {
        MainApp.getInstance().loadConnexion();
    }

    @FXML
    private void espaceSociete(ActionEvent event) {
        MainApp.getInstance().loadInscriptionSociete();
    }

    // records relative x and y co-ordinates.
    class Delta {

        double x, y;
    }
}
