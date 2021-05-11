/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.MainApp;
import app.entity.Candidat;
import app.entity.CandidatureOffre;
import app.entity.OffreDeTravail;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import app.service.RevueCrud;
import app.entity.Revue;
import app.entity.Societe;
import app.service.CandidatCrud;
import app.service.CandidatureOffreCrud;
import com.jafregle.Jafregle;
import com.jafregle.Language;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class RevueAfficherToutController implements Initializable {
    
    public static Societe societeActuelle;
    public static OffreDeTravail offreDeTravailActuelle;
    public static Revue revueActuelle;
    
    @FXML
    private VBox containerAllRevue;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Button btnRetour;
    
    @FXML
    private Button btnAjout;
    
    @FXML
    private TextField inputRecherche;
    
    @FXML
    private Text topText;
    
    @FXML
    private Text noteTotale;
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;
    @FXML
    private ImageView star3;
    @FXML
    private ImageView star4;
    @FXML
    private ImageView star5;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (MainApp.getSession() == null) {
            btnAjout.setDisable(true);
        }
        
        topText.setText("Revues sur " + offreDeTravailActuelle.getNom() + " de " + societeActuelle.getNom());
        
        ImageView btnRetourIcon = new ImageView("app/images/mdi/arrow-left-dark.png");
        btnRetourIcon.setFitHeight(20);
        btnRetourIcon.setFitWidth(20);
        btnRetour.setGraphic(btnRetourIcon);
        
        List<Revue> listRevue = RevueCrud.getInstance().getRevuesParOffre(offreDeTravailActuelle.getId());
        
        float sommeNote = 0;
        int i;
        for (i = 0; i < listRevue.size(); i++) {
            sommeNote += listRevue.get(i).getNbEtoiles();
        }
        float sommeTotale = (sommeNote / i);
        if (sommeTotale == Math.round(sommeTotale)) {
            noteTotale.setText(String.format("%.0f", sommeTotale) + "/5");
        } else {
            noteTotale.setText(String.format("%.1f", sommeTotale) + "/5");
        }
        setStarsFloat(sommeTotale);
        
        afficherRevues(listRevue);
    }
    
    public void afficherRevues(List<Revue> listRevue) {
        
        if (!listRevue.isEmpty()) {
            for (int i = 0; i < listRevue.size(); i++) {
                CandidatureOffre candidatureOffre = CandidatureOffreCrud.getInstance().getCandidaturesOffreById(
                        listRevue.get(i).getCandidatureOffreId()
                );
                Candidat candidat = CandidatCrud.getInstance().getCandidatById(candidatureOffre.getCandidatId());
                containerAllRevue.getChildren().add(creerRevue(
                        candidat.getNom(),
                        candidat.getPrenom(),
                        candidat.getIdPhoto(),
                        offreDeTravailActuelle.getNom(),
                        societeActuelle.getNom(),
                        listRevue.get(i)
                ));
            }
            
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune revue"));
            containerAllRevue.getChildren().add(stackPane);
        }

        /* makes scrolling faster
            containerAllRevue.setOnScroll((event) -> {
            double deltaY = event.getDeltaY() * 1;
            double width = scrollPane.getContent().getBoundsInLocal().getWidth();
            double vvalue = scrollPane.getVvalue();
            scrollPane.setVvalue(vvalue + -deltaY / width);
            });*/
    }
    
    public Parent creerRevue(
            String prenomCandidatText,
            String nomCandidatText,
            String idPhotoCandidat,
            String nomOffreDeTravail,
            String nomSociete,
            Revue revue
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/ModeleRevue.fxml"));
            
            AnchorPane leftAnchorPane = ((AnchorPane) ((HBox) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) leftAnchorPane.lookup("#prenomCandidat")).setText(prenomCandidatText);
            ((Text) leftAnchorPane.lookup("#nomCandidat")).setText(nomCandidatText);
            try {
                ((ImageView) leftAnchorPane.lookup("#imageCandidat")).setImage(new Image(idPhotoCandidat));
            } catch (Exception e) {
                ((ImageView) leftAnchorPane.lookup("#imageCandidat")).setImage(new Image("app/images/default.jpg"));
            }
            ((Text) leftAnchorPane.lookup("#nomSociete")).setText(nomOffreDeTravail);
            ((Text) leftAnchorPane.lookup("#nomOffre")).setText(nomSociete);
            
            AnchorPane rightAnchorPane = ((AnchorPane) ((HBox) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(2));
            
            HBox hboxEtoiles = (HBox) rightAnchorPane.lookup("#hboxEtoiles");
            
            for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
                if (indexEtoile >= revue.getNbEtoiles()) {
                    ((ImageView) hboxEtoiles.lookup("#etoile" + indexEtoile)).setImage(new Image("/app/images/mdi/star-outline.png"));
                } else {
                    ((ImageView) hboxEtoiles.lookup("#etoile" + indexEtoile)).setImage(new Image("/app/images/mdi/star.png"));
                }
            }
            HBox hBoxContenu = (HBox) rightAnchorPane.lookup("#hboxContenu");
            VBox contenuContainer = (VBox) hBoxContenu.lookup("#contenuContainer");
            Pane separateur = (Pane) hBoxContenu.lookup("#separateur");
            VBox traductionContainer = (VBox) hBoxContenu.lookup("#traductionContainer");
            
            ((Text) contenuContainer.lookup("#objet")).setText(revue.getObjet());
            ((Text) contenuContainer.lookup("#description")).setText(revue.getDescription());
            Button btnTraduire = (Button) contenuContainer.lookup("#btnTraduire");
            
            Text objetTraduit = (Text) (traductionContainer.lookup("#textTraduitLabel"));
            Text descriptionTraduite = (Text) (traductionContainer.lookup("#translatedDescription"));
            Button btnMasquerTraduction = (Button) traductionContainer.lookup("#btnMasquerTraduction");
            
            hBoxContenu.getChildren().remove(separateur);
            traductionContainer.getChildren().remove(objetTraduit);
            traductionContainer.getChildren().remove(descriptionTraduite);
            traductionContainer.getChildren().remove(btnMasquerTraduction);
            ((Text) contenuContainer.lookup("#description")).setWrappingWidth(800);
            
            btnTraduire.setOnAction(action -> {
                try {
                    Jafregle jafregle = new Jafregle(Language.ENGLISH, Language.FRENCH);
                    
                    objetTraduit.setText(jafregle.translate(revue.getObjet()));
                    descriptionTraduite.setText(jafregle.translate(revue.getDescription()));
                } catch (IOException e) {
                    System.out.println("Erreur de traduction");
                    System.out.println(e.getMessage());
                }
                
                hBoxContenu.getChildren().add(1, separateur);
                traductionContainer.getChildren().add(objetTraduit);
                traductionContainer.getChildren().add(descriptionTraduite);
                traductionContainer.getChildren().add(btnMasquerTraduction);
                contenuContainer.getChildren().remove(btnTraduire);
                
                descriptionTraduite.setWrappingWidth(400);
                ((Text) contenuContainer.lookup("#description")).setWrappingWidth(400);
            });
            btnMasquerTraduction.setOnAction(action -> {
                hBoxContenu.getChildren().remove(separateur);
                traductionContainer.getChildren().remove(objetTraduit);
                traductionContainer.getChildren().remove(descriptionTraduite);
                traductionContainer.getChildren().remove(btnMasquerTraduction);
                contenuContainer.getChildren().add(btnTraduire);
                
                ((Text) contenuContainer.lookup("#description")).setWrappingWidth(800);
            });
            
            HBox hBoxActionsDate = (HBox) rightAnchorPane.lookup("#hboxActionsDate");
            
            try {
                ((Text) hBoxActionsDate.lookup("#texteDateCreation"))
                        .setText(revue.getDateCreation().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e) {
                System.out.println("Date nulle");
            }
            Button btnModifier = (Button) hBoxActionsDate.lookup("#btnModifier");
            Button btnSupprimer = (Button) hBoxActionsDate.lookup("#btnSupprimer");
            
            if (MainApp.getSession() != null) {
                CandidatureOffre candidatureAssocie = CandidatureOffreCrud.getInstance().getCandidatureOffreByCandidatOffre(
                        offreDeTravailActuelle.getId(),
                        MainApp.getSession().getCandidatId()
                );
                if (candidatureAssocie != null) {
                    if (revue.getCandidatureOffreId() == candidatureAssocie.getId()) {
                        btnModifier.setOnAction((event) -> {
                            modifierRevue(event, revue);
                        });
                        btnSupprimer.setOnAction((event) -> {
                            supprimerRevue(event, revue);
                        });
                    } else {
                        hBoxActionsDate.getChildren().remove(btnModifier);
                        hBoxActionsDate.getChildren().remove(btnSupprimer);
                    }
                } else {
                    hBoxActionsDate.getChildren().remove(btnModifier);
                    hBoxActionsDate.getChildren().remove(btnSupprimer);
                }
            } else {
                hBoxActionsDate.getChildren().remove(btnModifier);
                hBoxActionsDate.getChildren().remove(btnSupprimer);
            }
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }
    
    private void modifierRevue(ActionEvent event, Revue revue) {
        revueActuelle = revue;
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/Manipuler.fxml")
        );
    }
    
    private void supprimerRevue(ActionEvent event, Revue revue) {
        revueActuelle = null;
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer votre revue avec id : ?");
        Optional<ButtonType> action = alert.showAndWait();
        
        if (action.get() == ButtonType.OK) {
            RevueCrud.getInstance().supprimerRevue(revue);
            MainWindowController.chargerInterface(
                    getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/AfficherTout.fxml")
            );
        }
    }
    
    @FXML
    private void ajouterRevue(ActionEvent event) {
        CandidatureOffre candidatureOffre;
        if (CandidatureOffreCrud.getInstance().getCandidatureOffreByCandidatOffre(
                RevueAfficherToutController.offreDeTravailActuelle.getId(),
                MainApp.getSession().getCandidatId()
        ) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur d'ajout revue");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez d'abord avoir une candidature accepté pour cette offre pour publier une revue");
            alert.showAndWait();
        } else {
            candidatureOffre = CandidatureOffreCrud.getInstance()
                    .getCandidatureOffreByCandidatOffre(
                            RevueAfficherToutController.offreDeTravailActuelle.getId(),
                            MainApp.getSession().getCandidatId()
                    );
            
            System.out.println(
                    "Candidature : " + candidatureOffre.getId()
                    + " / Candidat : " + candidatureOffre.getCandidatId()
                    + " / Offre : " + candidatureOffre.getOffreDeTravailId()
                    + " / Etat : " + candidatureOffre.getEtat());
            
            if (candidatureOffre.getEtat().equals("accepté")) {
                MainWindowController.chargerInterface(
                        getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/Manipuler.fxml")
                );
            }
        }
    }
    
    @FXML
    private void selectionnerSociete(ActionEvent event) {
        MainWindowController.chargerInterface(
                getClass().getResource("/app/gui/front_end/societe/offre_de_travail/revue/SelectionnerSociete.fxml")
        );
    }
    
    @FXML
    private void rechercher(KeyEvent event) {
        
        containerAllRevue.getChildren().clear();
        
        List<Revue> listRevue = RevueCrud.getInstance().getRevuesParOffreParObjet(offreDeTravailActuelle.getId(), inputRecherche.getText());
        
        afficherRevues(listRevue);
    }
    
    public static void information(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("information");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private void setStarsFloat(float nbEtoiles) {
        ImageView[] stars = {star1, star2, star3, star4, star5};
        for (int i = 0; i < 5; i++) {
            if (nbEtoiles >= i + 1) {
                stars[i].setImage(new Image("app/images/mdi/star.png"));
            } else if ((nbEtoiles > i) && (nbEtoiles < i + 1)) {
                stars[i].setImage(new Image("app/images/mdi/star-half.png"));
            } else {
                stars[i].setImage(new Image("app/images/mdi/star-outline.png"));
            }
            ColorAdjust blackEffect = new ColorAdjust();
            blackEffect.setBrightness(-1.0);
            stars[i].setEffect(blackEffect);
        }
    }
}
