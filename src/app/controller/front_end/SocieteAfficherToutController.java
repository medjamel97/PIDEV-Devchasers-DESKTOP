/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.entity.Societe;
import app.service.SocieteCrud;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class SocieteAfficherToutController implements Initializable {

    public static Societe societeActuelle;
    
    @FXML
    private VBox vboxSocieteContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Societe> listSocietes = SocieteCrud.getInstance().getSociete();
        listSocietes.forEach((societe) -> {
            vboxSocieteContainer.getChildren().add(creerSociete(societe));
        });
    }

    private Parent creerSociete(Societe societe) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/societe/ModeleSociete.fxml"));

            ((ImageView) parent.lookup("#photoSociete")).setImage(new Image(societe.getIdPhoto()));
            ((Text) parent.lookup("#nomSociete")).setText(societe.getNom());
            ((Text) parent.lookup("#dateCreation")).setText(societe.getDateCreation().toLocalDate().toString());
            ((Text) parent.lookup("#tel")).setText(societe.getTel());
            ((Button) parent.lookup("#buttonAfficherSociete")).setOnAction(value -> {
                societeActuelle = societe;
                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/societe/ProfilSociete.fxml")
                );
            });

            return parent;
        } catch (IOException ex) {
            Logger.getLogger(SocieteAfficherToutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
