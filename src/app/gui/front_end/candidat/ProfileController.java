/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.front_end.candidat;

import app.MainApp;
import app.controller.front_end.MainWindowController;
import app.entity.Competence;
import app.entity.Education;
import app.entity.ExperienceDeTravail;
import app.service.CompetenceCrud;
import app.service.EducationCrud;
import app.service.ExperienceDeTravailCrud;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Faten
 */
public class ProfileController implements Initializable {

    public static Education educationActuelle = null;
    public static Competence competenceActuelle = null;
    public static ExperienceDeTravail experience_de_travailActuelle = null;

    @FXML
    private PieChart pieChart;

    @FXML
    private Button ajoutComp;
    @FXML
    private Button Education;
    @FXML
    private Button ExpdeTravail;
    @FXML
    private VBox educationContainer;
    @FXML
    private VBox experiencedetravailContainer;
    @FXML
    private VBox CompetenceContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<Education> listEducation = EducationCrud.getInstance().getEducationsByCandidat(MainApp.getSession().getCandidatId());
        listEducation.forEach((education) -> {
            educationContainer.getChildren().add(creeEducation(education));
        });

        List<ExperienceDeTravail> listExpdetravail = ExperienceDeTravailCrud.getInstance().getExperienceDeTravailByCandidat(MainApp.getSession().getCandidatId());
        listExpdetravail.forEach((experience_de_travail) -> {
            experiencedetravailContainer.getChildren().add(creeExpdeTravail(experience_de_travail));
        });

        List<Competence> listCompetence = CompetenceCrud.getInstance().getCompetenceByCandidat(MainApp.getSession().getCandidatId());
        listCompetence.forEach((competence) -> {
            educationContainer.getChildren().add(creeCompetence(competence));
        });
        
ObservableList<PieChart.Data> data =
         FXCollections.observableArrayList();
      data.addAll(new PieChart.Data("Asia", 30.0),
         new PieChart.Data("Africa", 20.3),
         new PieChart.Data("North America", 16.3),
         new PieChart.Data("South America", 12.0),
         new PieChart.Data("Antartica", 8.9),
         new PieChart.Data("Europe", 6.7),
         new PieChart.Data("Australia", 5.2));

      pieChart.setData(data);
      pieChart.setTitle("The Continents: Land Area");
      
    }

    private Parent creeEducation(Education e) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/candidat/ModeleEducation.fxml"));
            ((ImageView) parent.lookup("#EducationIMG")).setImage(new Image("/app/images/award.png"));
            ((Text) parent.lookup("#nomEtablissement")).setText(e.getEtablissement());
            ((Text) parent.lookup("#description")).setText(e.getDescription());
            ((Text) parent.lookup("#niveauEducation")).setText(e.getNiveauEducation());
            ((Text) parent.lookup("#ville")).setText(e.getVille());
            ((Text) parent.lookup("#filiere")).setText(e.getFiliere());
            ((Button) parent.lookup("#modifier")).setOnAction(value -> {
                educationActuelle = e;
                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/candidat/ModifierEducation.fxml")
                );
            });
            ((Button) parent.lookup("#supprimer")).setOnAction(value -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Suppression");
                alert.setHeaderText(null);
                alert.setContentText("vous voulez vraiment supprimer cette education ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                EducationCrud.getInstance().supprimerEducation(e);
                                MainWindowController.chargerInterface(
                                getClass().getResource("/app/gui/front_end/candidat/profile.fxml")
                );
            
        }
               
            });

            return parent;
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Parent creeCompetence(Competence c) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/candidat/ModeleCompetence.fxml"));

            ((Text) parent.lookup("#CompName")).setText(c.getName());
            ((ProgressBar) parent.lookup("#level")).setProgress((double) (Double.valueOf(c.getLevel()) / 100));
             ((Button) parent.lookup("#modifier")).setOnAction(value -> {
                competenceActuelle = c;
                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/candidat/ModifierCompetence.fxml")
                );
            });
            ((Button) parent.lookup("#supprimer")).setOnAction(value -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Suppression");
                alert.setHeaderText(null);
                alert.setContentText("vous voulez vraiment supprimer cette Competence ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                 CompetenceCrud.getInstance().supprimerCompetence(c);
                                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/candidat/profile.fxml")
                );
            
        }
          
            });
            
            
            return parent;
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Parent creeExpdeTravail(ExperienceDeTravail exp) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/candidat/ModelExperienceDeTravail.fxml"));
            ((ImageView) parent.lookup("#workEXPIMG")).setImage(new Image("/app/images/suitcase.png"));

            ((Text) parent.lookup("#titreEmploi")).setText(exp.getTitreEmploi());
            ((Text) parent.lookup("#nomEntreprise")).setText(exp.getNomEntreprise());
            ((Text) parent.lookup("#descriptionEmp")).setText(exp.getDescription());
            ((Text) parent.lookup("#villeEmp")).setText(exp.getVille());
            ((Button) parent.lookup("#modifier")).setOnAction(value -> {
                experience_de_travailActuelle = exp;
                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/candidat/ModifierExperienceDeTravail.fxml")
                );
            });
            ((Button) parent.lookup("#supprimer")).setOnAction(value -> {
                
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Suppression");
                alert.setHeaderText(null);
                alert.setContentText("vous voulez vraiment supprimer cette experience de travail ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
               ExperienceDeTravailCrud.getInstance().supprimerExperienceDeTravail(exp);
                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/candidat/profile.fxml")
                );
            
        }
                
            });

            return parent;
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FXML
    private void Competence(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjoutCompetence.fxml")
        );
    }

    @FXML
    private void Education(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjouterEducation.fxml")
        );
    }

    @FXML
    private void ExperiencedeTravail(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/candidat/AjouterExperienceDeTravail.fxml")
        );
    }
    @FXML
    public   PieChart createPieChart() {
      ObservableList<PieChart.Data> data =
         FXCollections.observableArrayList();
      data.addAll(new PieChart.Data("Asia", 30.0),
         new PieChart.Data("Africa", 20.3),
         new PieChart.Data("North America", 16.3),
         new PieChart.Data("South America", 12.0),
         new PieChart.Data("Antartica", 8.9),
         new PieChart.Data("Europe", 6.7),
         new PieChart.Data("Australia", 5.2));

      pieChart.setData(data);
      pieChart.setTitle("The Continents: Land Area");
      return pieChart;
   }
}


