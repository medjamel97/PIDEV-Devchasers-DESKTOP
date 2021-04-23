/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.MainApp;
import app.entity.Societe;
import app.entity.User;
import app.service.SocieteCrud;
import app.service.UserCrud;
import java.net.URL;
import java.sql.Types;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class InscriptionSocieteController implements Initializable {

    final Delta dragDelta = new Delta();
    Stage stage;

    @FXML
    private TextField inputEmail;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private TextField inputNom;
    @FXML
    private TextField inputTelephone;
    @FXML
    private DatePicker inputDate;
    @FXML
    private Button btnPageConnexion;
    @FXML
    private Button btnEspaceCandidat;

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

        btnEspaceCandidat.setOnMouseMoved((event) -> {
            btnEspaceCandidat.setTextFill(new Color(0.18, 0.33, 0.83, 1));
        });
        btnEspaceCandidat.setOnMouseExited((event) -> {
            btnEspaceCandidat.setTextFill(Color.BLACK);
        });

        stage = MainApp.mainStage;
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

        int idSociete = SocieteCrud.getInstance().getLastId() + 1;

        Societe societe = new Societe(
                idSociete,
                inputNom.getText(),
                java.sql.Date.valueOf(inputDate.getValue()),
                inputTelephone.getText(),
                null
        );

        User user = new User(
                Types.NULL,
                idSociete,
                inputEmail.getText(),
                "[\n" + "\"ROLE_SOCIETE\"\n" + "]",
                UserCrud.getInstance().encodePassword(inputPassword.getText()),
                true,
                true
        );

        SocieteCrud.getInstance().ajouterSociete(societe);
        UserCrud.getInstance().ajouterUtilisateur(user);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succés d'inscription");
        alert.setHeaderText(null);
        alert.setContentText("Vous êtes bien inscrit");
        alert.showAndWait();

        System.out.println();
        System.out.println("USER : " + user.toString());
        System.out.println();
        System.out.println("SOCIETE : " + societe.toString());


        MainApp.getInstance().loadConnexion();
    }

    @FXML
    private void pageConnexion(ActionEvent event) {
        MainApp.getInstance().loadConnexion();
    }

    @FXML
    private void espaceCandidat(ActionEvent event) {
        MainApp.getInstance().loadInscriptionCandidat();
    }

    // records relative x and y co-ordinates.
    class Delta {

        double x, y;
    }
}
