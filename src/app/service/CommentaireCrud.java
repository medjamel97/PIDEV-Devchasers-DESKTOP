/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.MainApp;
import app.entity.Commentaire;
import app.interfaces.CommentaireCrudInterface;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Maher
 */
public class CommentaireCrud implements CommentaireCrudInterface {

    private static CommentaireCrud instance;
    private final Connection connexion;

    public static CommentaireCrud getInstance() {
        if (instance == null) {
            instance = new CommentaireCrud();
        }
        return instance;

    }

    public CommentaireCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public void AjouterCommentaire(Commentaire commentaire) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO commentaire (publication_id, user_id, description) VALUES ( ? , ? , ?)");
            preparedStatement.setInt(1, commentaire.getPublicationId());
            preparedStatement.setInt(2, commentaire.getUserId());
            preparedStatement.setString(3, commentaire.getDescription());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success ajout commentaire");
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout commentaire : " + e.getMessage());
        }
    }

    @Override
    public List<Commentaire> getAllCommentairesByPub(int idPublication) {
        ObservableList<Commentaire> listCommentaire = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement
                    = connexion.prepareStatement("SELECT * FROM commentaire WHERE publication_id = ?");
            preparedStatement.setInt(1, idPublication);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listCommentaire.add(new Commentaire(
                        resultSet.getInt("id"),
                        resultSet.getInt("publication_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) commentaire : " + e.getMessage());
        }
        return listCommentaire;
    }

    @Override
    public void SupprimerCommentaire(Commentaire commentaire) {

        // ... user chose CANCEL or closed the dialog
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `commentaire` WHERE `id`=?");
            preparedStatement.setInt(1, commentaire.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion commentaire : " + e.getMessage());
        }

    }

    @Override
    public void ModiferCommentaire(Commentaire commentaire) {
        PreparedStatement preparedStatement;
        System.out.println("aaaaaaa");
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE commentaire "
                    + "SET  description = ? "
                    + "WHERE id = ?");
            preparedStatement.setString(1, commentaire.getDescription());
            preparedStatement.setInt(2, commentaire.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success modification commentaire");
        } catch (SQLException e) {
            System.out.println("Erreur de modification commentaire : " + e.getMessage());
        }
    }

}
