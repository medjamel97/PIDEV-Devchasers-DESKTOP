/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevv;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Entities.publication;
import Service.publicationService;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Maher
 */
public class ModifPubController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextArea description;
    @FXML
    private TextField titre;
    @FXML
    private Label res;
    @FXML
    private Button modifier_btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        publication pub = AffichageController.pubActuelle;
        description.setText(pub.getDescription());
        titre.setText(pub.getTitre());
    }

 
    @FXML
    private void modifier(ActionEvent event) {
           publication p = new publication();

        LocalDate dd = LocalDate.now();
        Date date = java.sql.Date.valueOf(dd);
        p.setDescription(description.getText());
        //p.setCandidat_id(1);
        p.setTitre(titre.getText());
        //  p.setDate(date);
        //   p.setCandidat_id(1);
        publicationService ps = new publicationService();
        ps.AjouterPublication(p);

       try {
            Parent root = FXMLLoader.load(getClass().getResource("/pidevv/Affichage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

    }

}
