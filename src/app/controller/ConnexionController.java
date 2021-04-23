/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.MainApp;
import app.entity.User;
import app.service.UserCrud;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class ConnexionController implements Initializable {

    public String role;
    final Delta dragDelta = new Delta();
    Stage stage;

    @FXML
    private ComboBox<String> comboBoxConnexion;
    @FXML
    private Button btnInscription;
    @FXML
    private TextField inputEmail;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Button btnVisiteur;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<User> listUsers = UserCrud.getInstance().getAll();
        listUsers.forEach(listUser -> {
            comboBoxConnexion.getItems().add(listUser.getEmail());
        });

        btnVisiteur.setOnMouseMoved((event) -> {
            btnVisiteur.setTextFill(Color.BLUE);
        });
        btnVisiteur.setOnMouseExited((event) -> {
            btnVisiteur.setTextFill(Color.BLACK);
        });

        btnInscription.setOnMouseMoved((event) -> {
            btnInscription.setTextFill(Color.BLUE);
        });
        btnInscription.setOnMouseExited((event) -> {
            btnInscription.setTextFill(Color.BLACK);
        });

        stage = MainApp.mainStage;
        role = "ROLE_VISITOR";
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
    private void connexion(ActionEvent event) {
        if (UserCrud.getInstance().verifierEmail(inputEmail.getText())) {
            if (UserCrud.getInstance().verifierMotDePasse(inputEmail.getText(), inputPassword.getText())) {
                User user = UserCrud.getInstance().getUserByEmail(inputEmail.getText());

                if (user.getRoles().contains("ROLE_ADMIN")) {
                    role = "ROLE_ADMIN";
                    user.setRoles(role);
                    MainApp.setSession(user);
                    MainApp.getInstance().connexion(role);
                }
                if (user.getRoles().contains("ROLE_SOCIETE")) {
                    role = "ROLE_SOCIETE";
                    user.setRoles(role);
                    MainApp.setSession(user);
                    MainApp.getInstance().connexion(role);
                }
                if (user.getRoles().contains("ROLE_CANDIDAT")) {
                    role = "ROLE_CANDIDAT";
                    user.setRoles(role);
                    MainApp.setSession(user);
                    MainApp.getInstance().connexion(role);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de connexion");
                alert.setHeaderText(null);
                alert.setContentText("Mot de passe incorrect");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Email inexistant");
            alert.showAndWait();
        }
    }

    @FXML
    private void inscription(ActionEvent event) {
        MainApp.getInstance().loadInscriptionCandidat();
    }

    @FXML
    private void connexionPourTest(ActionEvent event) {
        if (comboBoxConnexion.getValue() != null) {
            User user = UserCrud.getInstance().getUserByEmail(comboBoxConnexion.getValue());
            MainApp.setSession(user);
            if (user.getRoles().contains("ROLE_ADMIN")) {
                role = "ROLE_ADMIN";
                user.setRoles(role);
                MainApp.setSession(user);
                MainApp.getInstance().connexion(role);
            }
            if (user.getRoles().contains("ROLE_SOCIETE")) {
                role = "ROLE_SOCIETE";
                user.setRoles(role);
                MainApp.setSession(user);
                MainApp.getInstance().connexion(role);
            }
            if (user.getRoles().contains("ROLE_CANDIDAT")) {
                role = "ROLE_CANDIDAT";
                user.setRoles(role);
                MainApp.setSession(user);
                MainApp.getInstance().connexion(role);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Choisir utilisateur");
            alert.showAndWait();
        }
    }

    @FXML
    private void connexionVisiteur(ActionEvent event) {
        MainApp.getInstance().connexion("ROLE_VISITOR");
    }

    // records relative x and y co-ordinates.
    class Delta {

        double x, y;
    }
}
