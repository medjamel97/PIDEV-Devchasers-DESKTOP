/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Publication;
import app.interfaces.PublicationCrudInterface;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Maher
 */
public class PublicationCrud implements PublicationCrudInterface {

    private static PublicationCrud instance;
    private final Connection connexion;

    public static PublicationCrud getInstance() {
        if (instance == null) {
            instance = new PublicationCrud();
        }
        return instance;
    }

    public PublicationCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public List<Publication> getAll() {
        ObservableList<Publication> listPublication = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM publication order by date desc");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listPublication.add(new Publication(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("date"),
                        resultSet.getInt("pourcentage_like")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) publication : " + e.getMessage());
        }
        return listPublication;
    }

    @Override
    public void incrementerjaime(int id) {
        try {
            PreparedStatement ste = connexion.prepareStatement("update publication set nbr_like=nbr_like+1  WHERE id='" + id);
            ste.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PublicationCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean Add_nbr_like(int id, int nbr_like) {

        int test = 0;
        boolean check;
        try {
            PreparedStatement pst = connexion.prepareStatement("update publication set nbr_like = ?  where id=" + id);
            pst.setInt(1, nbr_like);

        } catch (SQLException ex) {
            Logger.getLogger(PublicationCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (test == 0) {
            check = false;
        } else {
            check = true;
        }
        return check;

    }

    @Override
    public ObservableList<Publication> findpubBytitre(String titre) {
        ObservableList<Publication> listPublication = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM publication WHERE titre LIKE ? order by date desc");
            preparedStatement.setString(1, titre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listPublication.add(new Publication(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("date"),
                        resultSet.getInt("pourcentage_like")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur recherche publication : " + e.getMessage());
        }
        return listPublication;
    }

    @Override
    public Publication findpublicationById(int id) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM publication WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return new Publication(
                    resultSet.getInt("id"),
                    resultSet.getInt("candidat_id"),
                    resultSet.getString("titre"),
                    resultSet.getString("description"),
                    resultSet.getTimestamp("date"),
                    resultSet.getInt("pourcentage_like")
            );
        } catch (SQLException e) {
            System.out.println("Erreur recherche publication : " + e.getMessage());
        }
        return null;
    }

    @Override
    public void AjouterPublication(Publication publication) {
        try {
            String req;

            req = "INSERT INTO `publication`(`description`,`titre`,`date`)  VALUES (?,?,?)";
            PreparedStatement res = connexion.prepareStatement(req);

            res.setString(1, publication.getDescription());
            res.setString(2, publication.getTitre());
            res.setTimestamp(3, publication.getDate());
            res.executeUpdate();
            System.out.println("Publication ajouté");

        } catch (SQLException e) {
            System.out.println("Erreur d'ajout : " + e.getMessage());

        }
    }

    @Override
    public void ModiferPublication(Publication publication) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `publication` "
                    + "SET `titre` = ?, `description` = ?, `date` = ? "
                    + "WHERE `id` = ?"
            );
            preparedStatement.setString(1, publication.getTitre());
            preparedStatement.setString(2, publication.getDescription());
            preparedStatement.setTimestamp(3, publication.getDate());
            preparedStatement.setInt(4, publication.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success modification publication");
        } catch (SQLException e) {
            System.out.println("Erreur de modification publication : " + e.getMessage());
        }
    }

    @Override
    public void SupprimerPublication(Publication publication) {
        System.out.println("bdet 1 ");
        // ... user chose CANCEL or closed the dialog
        PreparedStatement preparedStatement;
        try {
            System.out.println("bdet 2 ");
            preparedStatement = connexion.prepareStatement("DELETE FROM `publication` WHERE `id`=?");
            System.out.println(publication.getId());
            preparedStatement.setInt(1, publication.getId());
            System.out.println("bdet 3 ");
            preparedStatement.executeUpdate();
            System.out.println("bdet 4 ");
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion publication : " + e.getMessage());
        }

    }
}
