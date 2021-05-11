/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Message;
import app.interfaces.MessageCrudInterface;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Grim
 */
public class MessageCrud implements MessageCrudInterface {

    private static MessageCrud instance;
    private final Connection connexion;

    public static MessageCrud getInstance() {
        if (instance == null) {
            instance = new MessageCrud();
        }
        return instance;
    }

    public MessageCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public ObservableList<Message> getMessagesByConversation(int idConversation, int limit) {
        ObservableList<Message> listMessage = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM message "
                    + "WHERE conversation_id = ? "
                    + "ORDER BY date_creation DESC "
                    + "LIMIT ?");
            preparedStatement.setInt(1, idConversation);
            preparedStatement.setInt(2, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listMessage.add(new Message(
                        resultSet.getInt("id"),
                        resultSet.getInt("conversation_id"),
                        resultSet.getString("contenu"),
                        resultSet.getTimestamp("date_creation"),
                        resultSet.getBoolean("est_proprietaire"),
                        resultSet.getBoolean("est_vu")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) messages : " + e.getMessage());
        }
        return listMessage;
    }

    @Override
    public void ajouterMessage(Message message) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO message (conversation_id, contenu, date_creation, est_proprietaire, est_vu) VALUES ( ? , ? , ? , ? , ? )");
            preparedStatement.setInt(1, message.getConversationId());
            preparedStatement.setString(2, message.getContenu());
            preparedStatement.setTimestamp(3, message.getDateCreation());
            preparedStatement.setBoolean(4, message.getEstProprietaire());
            preparedStatement.setBoolean(5, message.getEstVu());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success ajout message");
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout message : " + e.getMessage());
        }
    }

    @Override
    public void modifierMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimerMessage(Message message) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `message` WHERE `id`=?");
            preparedStatement.setInt(1, message.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Succés suppresion message");
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion message : " + e.getMessage());
        }
    }

    @Override
    public Message getDernierMessage(int idConversation) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM message "
                    + "WHERE conversation_id = ? "
                    + "ORDER BY date_creation DESC "
                    + "LIMIT 1");
            preparedStatement.setInt(1, idConversation);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Message(
                        resultSet.getInt("id"),
                        resultSet.getInt("conversation_id"),
                        resultSet.getString("contenu"),
                        resultSet.getTimestamp("date_creation"),
                        resultSet.getBoolean("est_proprietaire"),
                        resultSet.getBoolean("est_vu")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage detnier messages : " + e.getMessage());
        }
        return null;
    }
}
