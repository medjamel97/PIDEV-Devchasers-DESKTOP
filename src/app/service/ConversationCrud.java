/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.interfaces.ConversationCrudInterface;
import javafx.collections.ObservableList;

/**
 *
 * @author Grim
 */
import app.entity.Conversation;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;

public class ConversationCrud implements ConversationCrudInterface {

    private static ConversationCrud instance;
    private final Connection connexion;

    public static ConversationCrud getInstance() {
        if (instance == null) {
            instance = new ConversationCrud();
        }
        return instance;
    }

    public ConversationCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public ObservableList<Conversation> getConversationsByUser(int idUser) {
        ObservableList<Conversation> listConversation = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM conversation "
                    + "WHERE user_expediteur_id = ?");
            preparedStatement.setInt(1, idUser);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listConversation.add(new Conversation(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getInt("user_expediteur_id"),
                        resultSet.getInt("user_destinataire_id"),
                        resultSet.getTimestamp("date_dernier_message")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) conversation : " + e.getMessage());
        }
        return listConversation;
    }

    @Override
    public ObservableList<Conversation> rechercheConversationByUserNomPrenom(int userId, String nomPrenom) {
        ObservableList<Conversation> listConversation = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM conversation c join user ca on c.user_destinataire_id = ca.id "
                    + "WHERE (ca.nom LIKE ? OR ca.prenom LIKE ?) AND (user_expediteur_id = ?)");

            preparedStatement.setString(1, nomPrenom + "%");
            preparedStatement.setString(2, nomPrenom + "%");
            preparedStatement.setInt(3, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listConversation.add(new Conversation(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getInt("user_expediteur_id"),
                        resultSet.getInt("user_destinataire_id"),
                        resultSet.getTimestamp("date_dernier_message")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur recherche conversation : " + e.getMessage());
        }
        return listConversation;
    }

    @Override
    public Conversation getConversationByUsers(int idUserExpediteur, int idUserDestinataire) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "SELECT * "
                    + "FROM conversation "
                    + "WHERE user_expediteur_id = ? AND user_destinataire_id = ?");
            preparedStatement.setInt(1, idUserExpediteur);
            preparedStatement.setInt(2, idUserDestinataire);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Conversation(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getInt("user_expediteur_id"),
                        resultSet.getInt("user_destinataire_id"),
                        resultSet.getTimestamp("date_dernier_message")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erreur de recuperation conversation by users : " + e.getMessage());
        }
        return null;
    }

    @Override
    public int recupererMessagesNonLus(int idConversation) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "SELECT COUNT(*) AS total "
                    + "FROM conversation c JOIN message m on m.conversation_id = c.id "
                    + "WHERE m.conversation_id = ? AND m.est_vu = false "
            );
            preparedStatement.setInt(1, idConversation);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return (resultSet.getInt("total"));
            } else {
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("Erreur de recuperation messages non lus : " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void markAllAsRead(int idConversation) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE conversation c JOIN message m on m.conversation_id = c.id "
                    + "SET m.est_vu = true "
                    + "WHERE m.conversation_id = ?");
            preparedStatement.setInt(1, idConversation);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success modification conversation");
        } catch (SQLException e) {
            System.out.println("Erreur de modification conversation : " + e.getMessage());
        }
    }

    @Override
    public void ajouterConversation(Conversation conversation) {
        if (getConversationByUsers(conversation.getUserExpediteurId(), conversation.getUserDestinataireId()) == null) {
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connexion.prepareStatement(
                        "INSERT INTO conversation (nom, user_expediteur_id, user_destinataire_id, date_dernier_message) VALUES ( ? , ? , ? , ? )");
                preparedStatement.setString(1, conversation.getNom());
                preparedStatement.setInt(2, conversation.getUserExpediteurId());
                preparedStatement.setInt(3, conversation.getUserDestinataireId());
                preparedStatement.setTimestamp(4, conversation.getDateDernierMessage());

                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Success ajout conversation");
            } catch (SQLException e) {
                System.out.println("Erreur d'ajout conversation : " + e.getMessage());
            }
        }
    }

    @Override
    public void modifierDateConversation(Conversation conversation) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `conversation` "
                    + "SET `date_dernier_message` = ? "
                    + "WHERE `id` = ?");
            preparedStatement.setTimestamp(1, conversation.getDateDernierMessage());
            preparedStatement.setInt(2, conversation.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success modification conversation");
        } catch (SQLException e) {
            System.out.println("Erreur de modification conversation : " + e.getMessage());
        }
    }

    @Override
    public void supprimerConversation(Conversation conversation) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `conversation` WHERE `id`=?");
            preparedStatement.setInt(1, conversation.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Succés suppresion conversation");
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion conversation : " + e.getMessage());
        }
    }
}
