/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.MainApp;
import app.entity.Candidat;
import app.entity.User;
import app.entity.Conversation;
import app.entity.Message;
import app.entity.Societe;
import app.service.CandidatCrud;
import app.service.UserCrud;
import app.service.ConversationCrud;
import app.service.MessageCrud;
import app.service.SocieteCrud;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class MessagerieController implements Initializable {

    private Conversation conversationActuelle;
    private final int MESSAGE_LIMIT = 20;
    private boolean countDownStarted;
    private Timeline messageRefreshTimeline, conversationRefreshTimeline;

    @FXML
    private VBox modalAjoutConv;
    @FXML
    private VBox conversationContainer;
    @FXML
    private VBox messagesContainer;
    @FXML
    private AnchorPane mainParent;
    @FXML
    private AnchorPane forEffectAnchorPane;
    @FXML
    private AnchorPane modalBackground;
    @FXML
    private VBox usersContainer;
    @FXML
    private TextField rechercheUsers;
    @FXML
    private Text chatNomUser;
    @FXML
    private Text chatDernierMessage;
    @FXML
    private TextField inputMessage;
    @FXML
    private VBox chatContainer;
    @FXML
    private ImageView imageUser;
    @FXML
    private TextField inputConversation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countDownStarted = false;
        conversationActuelle = null;
        chatContainer.setVisible(false);

        toggleAjoutModal();

        List<User> listUser = UserCrud.getInstance().getAll();
        listUser.forEach((user) -> {
            if (user.getId() != MainApp.getSession().getId()) {
                usersContainer.getChildren().add(creerUserForChoice(user));
            }
        });

        refreshConversations();
        refreshConversationsEveryInterval();
    }

    @FXML
    private void toggleAjoutModal(ActionEvent event) {
        toggleAjoutModal();
    }

    @FXML
    private void modalBackgroundClick(MouseEvent event) {
        toggleAjoutModal();
    }

    private void toggleAjoutModal() {
        if (mainParent.getChildren().contains(modalAjoutConv)) {
            mainParent.getChildren().removeAll(modalBackground, modalAjoutConv);
            forEffectAnchorPane.setEffect(null);
        } else {
            BoxBlur boxBlur = new BoxBlur(10.0, 10.0, 2);
            forEffectAnchorPane.setEffect(boxBlur);
            mainParent.getChildren().addAll(modalBackground, modalAjoutConv);
        }
    }

    @FXML
    private void rechercheUsers(KeyEvent event) {
        /*usersContainer.getChildren().clear();
        List<User> listUser = UserCrud.getInstance().getUsersByNomPrenom(rechercheUsers.getText());
        listUser.forEach((user) -> {
            if (user.getId() != MainApp.getSession().getId()) {
                usersContainer.getChildren().add(creerUser(user));
            }
        });*/
    }

    @FXML
    private void rechercheConversations(KeyEvent event) {
        conversationContainer.getChildren().clear();
        List<Conversation> listConversations = ConversationCrud.getInstance()
                .rechercheConversationByUserNomPrenom(
                        MainApp.getSession().getId(),
                        inputConversation.getText()
                );
        listConversations.forEach((conversation) -> {
            conversationContainer.getChildren().add(creerConversation(conversation));
        });
    }

    @FXML
    private void envoyerMessage(ActionEvent event) {

        String content = inputMessage.getText();
        inputMessage.setText("");
        creerMessage(content, true, -1, true);

        Conversation conversationDestinataire = ConversationCrud.getInstance().getConversationByUsers(
                conversationActuelle.getUserDestinataireId(),
                conversationActuelle.getUserExpediteurId()
        );

        if (conversationDestinataire == null) {
            String nom = "NaN";

            User user = UserCrud.getInstance().getUserById(conversationActuelle.getUserDestinataireId());

            if (user.getRoles().contains("ROLE_ADMIN")) {
                nom = "Admin";
            } else if (user.getRoles().contains("ROLE_SOCIETE")) {
                Societe societeDestinataire = SocieteCrud.getInstance().getSocieteById(user.getSocieteId());
                nom = societeDestinataire.getNom();
            } else if (user.getRoles().contains("ROLE_CANDIDAT")) {
                Candidat candidatDestinataire = CandidatCrud.getInstance().getCandidatById(user.getCandidatId());
                nom = candidatDestinataire.getPrenom() + " " + candidatDestinataire.getNom();
            }

            ConversationCrud.getInstance().ajouterConversation(new Conversation(
                    nom,
                    conversationActuelle.getUserDestinataireId(),
                    conversationActuelle.getUserExpediteurId(),
                    null
            ));

            conversationDestinataire = ConversationCrud.getInstance().getConversationByUsers(
                    conversationActuelle.getUserDestinataireId(),
                    conversationActuelle.getUserExpediteurId()
            );
        }

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        conversationActuelle.setDateDernierMessage(timestamp);
        ConversationCrud.getInstance().modifierDateConversation(conversationActuelle);

        MessageCrud.getInstance().ajouterMessage(
                new Message(conversationActuelle.getId(), content, timestamp, true, true)
        );
        MessageCrud.getInstance().ajouterMessage(
                new Message(conversationDestinataire.getId(), content, timestamp, false, false)
        );

        refreshMessages(conversationActuelle.getId());

        refreshConversations();
    }

    private Parent creerConversation(Conversation conversation) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/messagerie/ModeleConversation.fxml"));

            User user = UserCrud.getInstance().getUserById(conversation.getUserDestinataireId());

            String idPhoto = null;
            if (user.getRoles().contains("ROLE_ADMIN")) {
                idPhoto = "";
            } else if (user.getRoles().contains("ROLE_SOCIETE")) {
                Societe societeDestinataire = SocieteCrud.getInstance().getSocieteById(user.getSocieteId());
                idPhoto = societeDestinataire.getIdPhoto();
            } else if (user.getRoles().contains("ROLE_CANDIDAT")) {
                Candidat candidatDestinataire = CandidatCrud.getInstance().getCandidatById(user.getCandidatId());
                idPhoto = candidatDestinataire.getIdPhoto();
            }

            ((Label) parent.lookup("#nomConversation")).setText(conversation.getNom());
            if (ConversationCrud.getInstance().recupererMessagesNonLus(conversation.getId()) > 0) {
                ((AnchorPane) parent.lookup("#badgePane")).setVisible(true);
                ((Label) parent.lookup("#notificationBadge")).setText(String.valueOf(ConversationCrud.getInstance()
                        .recupererMessagesNonLus(conversation.getId()))
                );
            }

            ((Button) parent.lookup("#btnSupprimer")).setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmer la suppression");
                alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de vouloir supprimer cette conversation ?");
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    if (conversationActuelle.getId() == conversation.getId()) {
                        chatContainer.setVisible(false);
                        conversationActuelle = null;
                    }
                    ConversationCrud.getInstance().supprimerConversation(conversation);
                    refreshConversations();
                }
            });

            try {
                ((ImageView) parent.lookup("#imageConversation")).setImage(new Image(idPhoto));
            } catch (Exception e) {
                System.out.println("Erreur lien image : " + idPhoto);
            }

            String dernierMessage;
            if (MessageCrud.getInstance().getDernierMessage(conversation.getId()) != null) {
                dernierMessage = MessageCrud.getInstance().getDernierMessage(conversation.getId()).getContenu();
            } else {
                dernierMessage = "Aucun message";
            }
            ((Label) parent.lookup("#dernierMessage")).setText(dernierMessage);

            parent.setOnMouseClicked((event) -> {
                ConversationCrud.getInstance().markAllAsRead(conversation.getId());
                initializeChatWindow(user);
            });
            return parent;
        } catch (IOException ex) {
            Logger.getLogger(MessagerieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Parent creerMessage(String contenuMessage, boolean estProprietaire, int pos, boolean faded) {
        StackPane parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/messagerie/ModeleMessage.fxml"));
            ((Label) ((AnchorPane) parent.getChildren().get(0)).getChildren().get(0)).setText(contenuMessage);
            switch (pos) {
                case 1:
                    creerMessageAvecPositon(parent, estProprietaire, "top", faded);
                    break;
                case 0:
                    creerMessageAvecPositon(parent, estProprietaire, "middle", faded);
                    break;
                case -1:
                    creerMessageAvecPositon(parent, estProprietaire, "bottom", faded);
                    break;
                default:
                    creerMessageAvecPositon(parent, estProprietaire, "middle", faded);
                    break;
            }

            return parent;
        } catch (IOException ex) {
            Logger.getLogger(MessagerieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void creerMessageAvecPositon(StackPane parent, boolean estProprietaire, String position, boolean faded) {
        if (estProprietaire) {
            parent.setAlignment(Pos.CENTER_RIGHT);
            ((AnchorPane) parent.getChildren().get(0)).getStyleClass().add("message-expediteur-" + position);
            if (faded) {
                ((AnchorPane) parent.getChildren().get(0)).getStyleClass().add("background-gray");
            }
        } else {
            parent.setAlignment(Pos.CENTER_LEFT);
            ((AnchorPane) parent.getChildren().get(0)).getStyleClass().add("message-destinataire-" + position);
        }
    }

    private Parent creerUserForChoice(User user) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/messagerie/ModeleUser.fxml"));

            parent.setOnMouseClicked(event -> {
                toggleAjoutModal();
                initializeChatWindow(user);
            });

            HBox hboxContent = (HBox) ((AnchorPane) parent).getChildren().get(0);

            String nomUser = "NaN";
            String idPhoto = "NaN";

            if (user.getRoles().contains("ROLE_ADMIN")) {
                nomUser = "Admin";
                idPhoto = "";
            } else if (user.getRoles().contains("ROLE_SOCIETE")) {
                Societe societeDestinataire = SocieteCrud.getInstance().getSocieteById(user.getSocieteId());
                nomUser = societeDestinataire.getNom();
                idPhoto = societeDestinataire.getIdPhoto();
            } else if (user.getRoles().contains("ROLE_CANDIDAT")) {
                Candidat candidatDestinataire = CandidatCrud.getInstance().getCandidatById(user.getCandidatId());
                nomUser = candidatDestinataire.getPrenom() + " " + candidatDestinataire.getNom();
                idPhoto = candidatDestinataire.getIdPhoto();
            }

            ((Text) hboxContent.lookup("#contenuUser")).setText(nomUser);
            try {
                ((ImageView) hboxContent.lookup("#imageUser")).setImage(new Image(idPhoto));
            } catch (Exception e) {
                System.out.println("Could not load image : " + idPhoto);
            }

            return parent;
        } catch (IOException ex) {
            Logger.getLogger(MessagerieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void initializeChatWindow(User user) {

        chatContainer.setVisible(true);
        String nom = "NaN";
        String idPhoto = "NaN";

        if (user.getRoles().contains("ROLE_ADMIN")) {
            nom = "Admin";
            idPhoto = "";
        } else if (user.getRoles().contains("ROLE_SOCIETE")) {
            Societe societeDestinataire = SocieteCrud.getInstance().getSocieteById(user.getSocieteId());
            nom = societeDestinataire.getNom();
            idPhoto = societeDestinataire.getIdPhoto();
        } else if (user.getRoles().contains("ROLE_CANDIDAT")) {
            Candidat candidatDestinataire = CandidatCrud.getInstance().getCandidatById(user.getCandidatId());
            nom = candidatDestinataire.getPrenom() + " " + candidatDestinataire.getNom();
            idPhoto = candidatDestinataire.getIdPhoto();
        }

        chatNomUser.setText(nom);
        try {
            imageUser.setImage(new Image(idPhoto));
        } catch (Exception e) {
            System.out.println("Erreur lien image : " + idPhoto);
        }

        // get existing conversation
        Conversation conversation = ConversationCrud.getInstance().getConversationByUsers(
                MainApp.getSession().getId(), user.getId()
        );
        // create conversation if not exist
        if (conversation == null) {
            System.out.println("conversation is null, creating new one ..");
            conversation = new Conversation(nom, MainApp.getSession().getId(), user.getId(), null);
            ConversationCrud.getInstance().ajouterConversation(
                    conversation
            );
            conversation = ConversationCrud.getInstance().getConversationByUsers(
                    MainApp.getSession().getId(), user.getId()
            );
        }

        if (conversation.getDateDernierMessage() != null) {
            chatDernierMessage.setText(
                    "Dernier message : " + conversation.getDateDernierMessage()
                            .toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
        } else {
            chatDernierMessage.setText("Chat pas encore commencé");
        }

        if (!countDownStarted) {
            countDownStarted = true;
        } else {
            messageRefreshTimeline.stop();
        }
        refreshMessages(conversation.getId());
        refreshMessagesEveryInterval(conversation.getId());

        conversationActuelle = conversation;
    }

    private void refreshConversations() {
        System.out.println(LocalTime.now() + " : Refreshing conversations");
        conversationContainer.getChildren().clear();
        List<Conversation> listConversations = ConversationCrud.getInstance().getConversationsByUser(MainApp.getSession().getId());
        listConversations.forEach((conversation) -> {
            if (conversation.getDateDernierMessage() != null) {
                conversationContainer.getChildren().add(creerConversation(conversation));
            }
        });
    }

    private void refreshMessages(int idConversation) {
        System.out.println(LocalTime.now() + " : Refreshing messages for conversation : " + idConversation);
        messagesContainer.getChildren().clear();
        if (idConversation != 0) {
            List<Message> listMessages = MessageCrud.getInstance().getMessagesByConversation(idConversation, MESSAGE_LIMIT);
            Collections.reverse(listMessages);

            for (int i = 0; i < listMessages.size(); i++) {
                boolean previous;
                boolean next;
                try {
                    listMessages.get(i - 1);
                    previous = true;
                } catch (Exception e) {
                    previous = false;
                }
                try {
                    listMessages.get(i + 1);
                    next = true;
                } catch (Exception e) {
                    next = false;
                }

                if (listMessages.get(i).getEstProprietaire()) {
                    messagesContainer.getChildren().add(creerMessage(listMessages.get(i).getContenu(), true, getPosition(
                            previous && listMessages.get(i - 1).getEstProprietaire(),
                            next && listMessages.get(i + 1).getEstProprietaire()
                    ), false));
                } else {
                    messagesContainer.getChildren().add(creerMessage(listMessages.get(i).getContenu(), false, getPosition(
                            previous && !listMessages.get(i - 1).getEstProprietaire(),
                            next && !listMessages.get(i + 1).getEstProprietaire()
                    ), false));
                }
            }
        }
    }

    private int getPosition(boolean previous, boolean next) {
        if (previous && next) {
            return 0;
        }
        if (previous && !next) {
            return -1;
        }
        return 1;
    }

    private void refreshMessagesEveryInterval(int idConversation) {
        messageRefreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), (ActionEvent event) -> {
                    refreshMessages(idConversation);
                }));
        messageRefreshTimeline.setCycleCount(Timeline.INDEFINITE);
        messageRefreshTimeline.play();
    }

    private void refreshConversationsEveryInterval() {
        conversationRefreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), (ActionEvent event) -> {
                    refreshConversations();
                }));
        conversationRefreshTimeline.setCycleCount(Timeline.INDEFINITE);
        conversationRefreshTimeline.play();
    }
}
