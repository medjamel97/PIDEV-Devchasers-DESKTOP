/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevv;

import Entities.BadWords;
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
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.controlsfx.control.Notifications;

/**
 *
 * @author Maher
 */
public class AjoutPubController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

 
    @FXML
    private void ajouter(ActionEvent event) {

        publication p = new publication();

        LocalDate dd = LocalDate.now();
        Date date = java.sql.Date.valueOf(dd);
        
         
                 BadWords.loadConfigs();
                if (BadWords.filterText(description.getText()) || BadWords.filterText(titre.getText())) {
            
                    Notifications notificationBuilder = Notifications.create()
               .title("Alert").text("Khedemti n'autorise pas ces termes").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
               .position(Pos.CENTER_LEFT)
               .onAction(new EventHandler<ActionEvent>(){
                   public void handle(ActionEvent event)
                   {
                       System.out.println("clicked ON ");
               }});
       notificationBuilder.darkStyle();
       notificationBuilder.show();
                       
           Parent root ;
                } else
                    
              
                    
                    
                
                   
    if(titre.getText().isEmpty())
    {
        label.setText("Veuillez saisir le titre ");
    }
                else
                 
        if(description.getText().isEmpty())
    {
        label.setText("Veuillez remplir la description ");
    }
                else
                {
                    
                          
       Notifications notificationBuilder = Notifications.create()
               .title("Succes").text("Votre Publication est ajoutè avec succès").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
               .position(Pos.CENTER_LEFT)
               .onAction(new EventHandler<ActionEvent>(){
                   public void handle(ActionEvent event)
                   {
                       System.out.println("clicked ON ");
               }});
       notificationBuilder.darkStyle();
       notificationBuilder.show();
        p.setDescription(description.getText());
        //p.setCandidat_id(1);
        p.setTitre(titre.getText());
          p.setDate(date);
        //   p.setCandidat_id(1);
        publicationService ps = new publicationService();
        ps.AjouterPublication(p);

       try {
            Parent root = FXMLLoader.load(getClass().getResource("/pidevv/Accueil.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

    }}}


