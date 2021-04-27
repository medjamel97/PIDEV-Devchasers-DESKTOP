/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller.front_end;

import app.MainApp;
import app.entity.Candidat;
import app.entity.Conversation;
import app.entity.Message;
import app.service.CandidatCrud;
import app.service.ConversationCrud;
import app.service.MessageCrud;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grim
 */
public class MessagerieController implements Initializable {

    private static Conversation conversationActuelle;
    private final int MESSAGE_LIMIT = 20;

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
    private VBox candidatsContainer;
    @FXML
    private TextField rechercheCandidats;
    @FXML
    private Text chatNomCandidat;
    @FXML
    private Text chatDernierMessage;
    @FXML
    private TextField inputMessage;
    @FXML
    private VBox chatContainer;
    @FXML
    private ImageView imageCandidat;
    @FXML
    private TextField inputConversation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        chatContainer.setVisible(false);
        conversationActuelle = null;

        toggleAjoutModal();

        List<Candidat> listCandidat = CandidatCrud.getInstance().getCandiadats();
        listCandidat.forEach((candidat) -> {
            if (candidat.getId() != MainApp.getSession().getCandidatId()) {
                candidatsContainer.getChildren().add(creerCandidat(candidat));
            }
        });

        refreshConversations();
    }

    private Parent creerConversation(Conversation conversation) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/candidat/messagerie/ModeleConversation.fxml"));
            Candidat candidat = CandidatCrud.getInstance().getCandidatById(conversation.getCandidatDestinataireId());
            ((Label) parent.lookup("#nomConversation")).setText(candidat.getPrenom() + " " + candidat.getNom());
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
                    ConversationCrud.getInstance().supprimerConversation(conversation);
                    chatContainer.setVisible(false);
                    conversationActuelle = null;
                    refreshConversations();
                }

            });

            try {
                ((ImageView) parent.lookup("#imageConversation")).setImage(new Image(candidat.getIdPhoto()));
            } catch (Exception e) {
                System.out.println("Erreur lien image : " + candidat.getIdPhoto());
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

                chatNomCandidat.setText(candidat.getPrenom() + " " + candidat.getNom());
                try {
                    imageCandidat.setImage(new Image(candidat.getIdPhoto()));
                } catch (Exception e) {
                    System.out.println("Erreur lien image : " + candidat.getIdPhoto());
                }
                if (conversation.getDateDernierMessage() != null) {
                    chatDernierMessage.setText("Dernier message : " + conversation.getDateDernierMessage().toLocalDateTime()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            ));

                } else {
                    chatDernierMessage.setText("Chat pas encore commencé");
                }

                chatContainer.setVisible(true);
                conversationActuelle = conversation;

                refreshConversations();
                refreshMessages(conversation.getId(), MESSAGE_LIMIT);
            });
            return parent;
        } catch (IOException ex) {
            Logger.getLogger(MessagerieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Parent creerMessage(Message message, boolean estProprietaire, int pos) {
        StackPane parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/candidat/messagerie/ModeleMessage.fxml"));
            ((Label) ((AnchorPane) parent.getChildren().get(0)).getChildren().get(0)).setText(message.getContenu());

            switch (pos) {
                case 1:
                    creerMessageAvecPositon(parent, estProprietaire, "top");
                    break;
                case 0:
                    creerMessageAvecPositon(parent, estProprietaire, "middle");
                    break;
                case -1:
                    creerMessageAvecPositon(parent, estProprietaire, "bottom");
                    break;
                default:
                    creerMessageAvecPositon(parent, estProprietaire, "middle");
                    break;
            }

            return parent;
        } catch (IOException ex) {
            Logger.getLogger(MessagerieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void creerMessageAvecPositon(StackPane parent, boolean estProprietaire, String position) {
        if (estProprietaire) {
            parent.setAlignment(Pos.CENTER_RIGHT);
            ((AnchorPane) parent.getChildren().get(0)).getStyleClass().add("message-expediteur-" + position);
        } else {
            parent.setAlignment(Pos.CENTER_LEFT);
            ((AnchorPane) parent.getChildren().get(0)).getStyleClass().add("message-destinataire-" + position);
        }
    }

    private Parent creerCandidat(Candidat candidat) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/app/gui/front_end/candidat/messagerie/ModeleCandidat.fxml"));

            parent.setOnMouseClicked(event -> {
                Conversation conversation = new Conversation(MainApp.getSession().getCandidatId(), candidat.getId(), null);
                conversationActuelle = conversation;
                ConversationCrud.getInstance().ajouterConversation(
                        conversation
                );

                conversationActuelle = ConversationCrud.getInstance().getConversationByCandidats(
                        MainApp.getSession().getCandidatId(), candidat.getId()
                );

                toggleAjoutModal();

                chatContainer.setVisible(true);

                chatNomCandidat.setText(candidat.getPrenom() + " " + candidat.getNom());
                try {
                    imageCandidat.setImage(new Image(candidat.getIdPhoto()));
                } catch (Exception e) {
                    System.out.println("Erreur lien image : " + candidat.getIdPhoto());
                }
                if (conversationActuelle.getDateDernierMessage() != null) {
                    chatDernierMessage.setText("Dernier message : " + conversationActuelle.getDateDernierMessage().toLocalDateTime()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            ));

                } else {
                    chatDernierMessage.setText("Chat pas encore commencé");
                }

                chatContainer.setVisible(true);

                refreshConversations();
                refreshMessages(
                        conversationActuelle.getId(),
                        MESSAGE_LIMIT
                );
            });
            ((Text) parent.lookup("#contenuCandidat")).setText(candidat.getPrenom() + " " + candidat.getNom());
            return parent;
        } catch (IOException ex) {
            Logger.getLogger(MessagerieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
    private void rechercheCandidats(KeyEvent event) {
        candidatsContainer.getChildren().clear();
        List<Candidat> listCandidat = CandidatCrud.getInstance().getCandiadatsByNomPrenom(rechercheCandidats.getText());
        listCandidat.forEach((candidat) -> {
            if (candidat.getId() != MainApp.getSession().getCandidatId()) {
                candidatsContainer.getChildren().add(creerCandidat(candidat));
            }
        });
    }

    @FXML
    private void rechercheConversations(KeyEvent event) {
        conversationContainer.getChildren().clear();
        List<Conversation> listConversations = ConversationCrud.getInstance()
                .rechercheConversationByCandidatNomPrenom(
                        MainApp.getSession().getCandidatId(),
                        inputConversation.getText()
                );
        listConversations.forEach((conversation) -> {
            conversationContainer.getChildren().add(creerConversation(conversation));
        });
    }

    @FXML
    private void envoyerMessage(ActionEvent event) {

        Conversation conversationDestinataire = ConversationCrud.getInstance().getConversationByCandidats(
                conversationActuelle.getCandidatDestinataireId(),
                conversationActuelle.getCandidatExpediteurId()
        );

        if (conversationDestinataire == null) {
            ConversationCrud.getInstance().ajouterConversation(new Conversation(
                    conversationActuelle.getCandidatDestinataireId(),
                    conversationActuelle.getCandidatExpediteurId(),
                    null
            ));

            conversationDestinataire = ConversationCrud.getInstance().getConversationByCandidats(
                    conversationActuelle.getCandidatDestinataireId(),
                    conversationActuelle.getCandidatExpediteurId()
            );
        }

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        conversationActuelle.setDateDernierMessage(timestamp);
        ConversationCrud.getInstance().modifierDateConversation(conversationActuelle);

        MessageCrud.getInstance().ajouterMessage(
                new Message(conversationActuelle.getId(), inputMessage.getText(), timestamp, true, true)
        );
        MessageCrud.getInstance().ajouterMessage(
                new Message(conversationDestinataire.getId(), inputMessage.getText(), timestamp, false, false)
        );
        refreshMessages(conversationActuelle.getId(), MESSAGE_LIMIT);

        inputMessage.setText("");
        refreshConversations();
    }

    private void refreshConversations() {
        conversationContainer.getChildren().clear();
        List<Conversation> listConversations = ConversationCrud.getInstance().getConversationsByCandidat(MainApp.getSession().getCandidatId());
        listConversations.forEach((conversation) -> {
            conversationContainer.getChildren().add(creerConversation(conversation));
        });
    }

    private void refreshMessages(int idConversation, int messageParPage) {
        messagesContainer.getChildren().clear();
        if (idConversation != 0) {
            List<Message> listMessages = MessageCrud.getInstance().getMessagesByConversation(idConversation, messageParPage);
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
                    messagesContainer.getChildren().add(creerMessage(listMessages.get(i), true, getPosition(
                            previous && listMessages.get(i - 1).getEstProprietaire(),
                            next && listMessages.get(i + 1).getEstProprietaire()
                    )));
                } else {
                    messagesContainer.getChildren().add(creerMessage(listMessages.get(i), false, getPosition(
                            previous && !listMessages.get(i - 1).getEstProprietaire(),
                            next && !listMessages.get(i + 1).getEstProprietaire()
                    )));
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

}
