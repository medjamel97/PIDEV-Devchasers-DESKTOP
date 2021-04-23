/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.OffreDeTravail;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import app.interfaces.OffreDeTravailCrudInterface;
import javafx.collections.ObservableList;

/**
 *
 * @author Anis
 */
public class OffreDeTravailCrud implements OffreDeTravailCrudInterface {

    private static OffreDeTravailCrud instance;
    private final Connection connexion;

    public static OffreDeTravailCrud getInstance() {
        if (instance == null) {
            instance = new OffreDeTravailCrud();
        }
        return instance;

    }

    public OffreDeTravailCrud() {
        connexion = ConnecteurBD.driverBD();
    }

 
    @Override
   public boolean controleJob (String job) {
        return (job.length() > 0);
    }


    @Override
    public boolean controleDescription(String description) {
        return (description.length() > 5);
    }

    @Override
    public void ajouterOffre(OffreDeTravail o) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO offre_de_travail (nom,description) VALUES ( ? , ? )");

            preparedStatement.setString(1, o.getNom());
            preparedStatement.setString(2, o.getDescription());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Offre ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void ModifierOffre(OffreDeTravail o) {

        {
            PreparedStatement preparedStatement;

            try {
                preparedStatement = connexion.prepareStatement(
                        "UPDATE `offre_de_travail` "
                        + "SET `nom` = ?, `description` = ?"
                        + "WHERE `id` = ?");
                preparedStatement.setString(1, o.getNom());
                preparedStatement.setString(2, o.getDescription());
                preparedStatement.setInt(3, o.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();

            } catch (SQLException e) {
                System.out.println("Erreur de modification candidat : " + e.getMessage());
            }

        }
    }

    @Override
    public void SupprimerOffre(int id) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `offre_de_travail` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Offre supprimé");
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion offre : " + e.getMessage());
        }
    }

    @Override
    public List<OffreDeTravail> getOffreDeTravailBySociete(int idSociete) {
        ObservableList<OffreDeTravail> listOffreDeTravail = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM offre_de_travail WHERE societe_id = ?");
            preparedStatement.setInt(1, idSociete);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listOffreDeTravail.add(new OffreDeTravail(
                        resultSet.getInt("id"),
                        resultSet.getInt("categorie_id"),
                        resultSet.getInt("societe_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage offres de travails : " + e.getMessage());
        }
        return listOffreDeTravail;
    }

    @Override
    public List<OffreDeTravail> displayOffre() {
        ObservableList<OffreDeTravail> listOffreDeTravail = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM offre_de_travail");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listOffreDeTravail.add(new OffreDeTravail(
                        resultSet.getInt("id"),
                        resultSet.getInt("categorie_id"),
                        resultSet.getInt("societe_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) Offre : " + e.getMessage());
        }
        return listOffreDeTravail;
    }

    @Override
    public List rechercheOffre(String recherche) {
        ObservableList<OffreDeTravail> listOffreDeTravail = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM offre_de_travail"
                    + "WHERE nom LIKE ?");
            preparedStatement.setString(1, recherche + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listOffreDeTravail.add(new OffreDeTravail(
                        resultSet.getInt("id"),
                        resultSet.getInt("categorie_id"),
                        resultSet.getInt("societe_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) offre : " + e.getMessage());
        }
        return listOffreDeTravail;
    }

    @Override
    public OffreDeTravail getOffreDeTravailById(int idOffre) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM offre_de_travail WHERE id = ?");
            preparedStatement.setInt(1, idOffre);

            ResultSet resultSet = preparedStatement.executeQuery();
            return new OffreDeTravail(
                    resultSet.getInt("id"),
                    resultSet.getInt("categorie_id"),
                    resultSet.getInt("societe_id"),
                    resultSet.getString("nom"),
                    resultSet.getString("description")
            );
        } catch (SQLException e) {
            System.out.println("Erreur getOffreDeTravailById : " + e.getMessage());
        }
        return null;
    }

}
