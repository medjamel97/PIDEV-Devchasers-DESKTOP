/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import utils.DataSource;
import Entities.commentaire;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import pidevv.AffichageController;

/**
 *
 * @author Maher
 */
public class commentaireService {

    private final Connection cnx;
    private static commentaireService instance;
    ObservableList<commentaire> listPub = FXCollections.observableArrayList();

    public commentaireService() {
        cnx = DataSource.getInstance().getCnx();
    }

    public static commentaireService getInstance() {
        if (instance == null) {
            instance = new commentaireService();
        }
        return instance;

    }

    public void AjouterCommentaire(commentaire p) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx.prepareStatement(
                    "INSERT INTO commentaire (publication_id, description) VALUES ( ? , ? )");
            preparedStatement.setInt(1, p.getPublication_id());
            preparedStatement.setString(2, p.getDescription());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success ajout commentaire");
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout commentaire : " + e.getMessage());
        }
    }

    public boolean Add_nbr_like(int id, int nbr_like) {

        int test = 0;
        boolean check;
        try {
            PreparedStatement pst = cnx.prepareStatement("update commentaire set nbr_like = ?  where id=" + id);
            pst.setInt(1, nbr_like);

        } catch (SQLException ex) {
            Logger.getLogger(commentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (test == 0) {
            check = false;
        } else {
            check = true;
        }
        return check;

    }

    public List<commentaire> getAllByPubId(int id) {
        try {
            PreparedStatement preparedStatement
                    = cnx.prepareStatement("SELECT * FROM commentaire WHERE publication_id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listPub.add(new commentaire(
                        resultSet.getInt("id"),
                        resultSet.getInt("publication_id"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) commentaire : " + e.getMessage());
        }
        return listPub;
    }

    public void incrementerjaime(int id) {
        try {
            PreparedStatement ste = cnx.prepareStatement("update commentaire set nbr_like=nbr_like+1  WHERE id='" + id);
            ste.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(commentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<commentaire> findpubBytitre(String titre) {
        listPub = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(
                    "SELECT * FROM commentaire WHERE titre LIKE ?");
            preparedStatement.setString(1, titre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listPub.add(new commentaire(
                        resultSet.getInt("id"),
                        resultSet.getInt("publication_id"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur recherche commentaire : " + e.getMessage());
        }
        return listPub;
    }

    public void SupprimerCommentaire(commentaire commentaire) {

        // ... user chose CANCEL or closed the dialog
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx.prepareStatement("DELETE FROM `commentaire` WHERE `id`=?");
            preparedStatement.setInt(1, commentaire.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion commentaire : " + e.getMessage());
        }

    }

    public void ModiferCommentaire(commentaire u) {
        PreparedStatement preparedStatement;
        System.out.println("aaaaaaa");
        try {
            preparedStatement = cnx.prepareStatement(
                    "UPDATE commentaire "
                    + "SET publication_id = ?, description = ? "
                    + "WHERE id = ?");
            preparedStatement.setInt(1, u.getPublication_id());
            preparedStatement.setString(2, u.getDescription());
            preparedStatement.setInt(3, u.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success modification commentaire");
        } catch (SQLException e) {
            System.out.println("Erreur de modification commentaire : " + e.getMessage());
        }
    }

}
