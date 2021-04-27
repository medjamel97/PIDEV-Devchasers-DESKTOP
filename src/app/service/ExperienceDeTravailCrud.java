/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.ExperienceDeTravail;
import app.interfaces.ExperienceDeTravailCrudInterface;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Faten
 */
public class ExperienceDeTravailCrud implements ExperienceDeTravailCrudInterface {

    private static ExperienceDeTravailCrud instance;
    private final Connection connexion;

    public static ExperienceDeTravailCrud getInstance() {
        if (instance == null) {
            instance = new ExperienceDeTravailCrud();
        }
        return instance;

    }

    public ExperienceDeTravailCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public void ajouterExperienceDeTravail(ExperienceDeTravail experience_de_travail) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO experience_de_travail (candidat_id,description,titre_emploi,nom_entreprise,ville,duree)VALUES ( ? ,? , ? , ? , ? ,?)");
            preparedStatement.setInt(1, experience_de_travail.getCandidatId());
            preparedStatement.setString(2, experience_de_travail.getDescription());
            preparedStatement.setString(3, experience_de_travail.getTitreEmploi());
            preparedStatement.setString(4, experience_de_travail.getNomEntreprise());
            preparedStatement.setString(5, experience_de_travail.getVille());
            preparedStatement.setString(6, experience_de_travail.getDuree());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout de ExperienceDeTravail : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<ExperienceDeTravail> getExperienceDeTravail() {
        ObservableList<ExperienceDeTravail> listExperienceDeTravail = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM experience_de_travail");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listExperienceDeTravail.add(new ExperienceDeTravail(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("description"),
                        resultSet.getString("titre_emploi"),
                        resultSet.getString("nom_entreprise"),
                        resultSet.getString("ville"),
                        resultSet.getString("duree")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (toutes) Experience De Travail : " + e.getMessage());
        }
        return listExperienceDeTravail;
    }

    @Override
    public ObservableList<ExperienceDeTravail> getExperienceDeTravailByCandidat(int idCandidat) {
        ObservableList<ExperienceDeTravail> listExpdetravail = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM experience_de_travail WHERE candidat_id = ?");
            preparedStatement.setInt(1, idCandidat);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listExpdetravail.add(new ExperienceDeTravail(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("description"),
                        resultSet.getString("titre_emploi"),
                        resultSet.getString("nom_entreprise"),
                        resultSet.getString("ville"),
                        resultSet.getString("duree")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage pour un candidat (educations) : " + e.getMessage());
        }
        return listExpdetravail;
    }

    @Override
    public ExperienceDeTravail getExperienceDeTravailById(int idExperienceDeTravail) {
        ObservableList<ExperienceDeTravail> listExperienceDeTravail = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM experience_de_travail WHERE id = ?");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listExperienceDeTravail.add(new ExperienceDeTravail(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("description"),
                        resultSet.getString("titre_emploi"),
                        resultSet.getString("nom_entreprise"),
                        resultSet.getString("ville"),
                        resultSet.getString("duree")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) candidat : " + e.getMessage());
        }
        return null;
    }

    @Override
    public void modifierExperienceDeTravail(ExperienceDeTravail experience_de_travail) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `experience_de_travail` "
                    + "SET  `description` = ?, `titre_emploi` = ?, `nom_entreprise` = ?, `ville` = ?, `duree` = ? "
                    + "WHERE `id` = ?");
            preparedStatement.setString(1, experience_de_travail.getDescription());
            preparedStatement.setString(2, experience_de_travail.getTitreEmploi());
            preparedStatement.setString(3, experience_de_travail.getNomEntreprise());
            preparedStatement.setString(4, experience_de_travail.getVille());
            preparedStatement.setString(5, experience_de_travail.getDuree());
            preparedStatement.setInt(6, experience_de_travail.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de modification de ExperienceDeTravail: " + e.getMessage());
        }

    }

    @Override
    public void supprimerExperienceDeTravail(ExperienceDeTravail experience_de_travail) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `experience_de_travail` WHERE `id`=?");
            preparedStatement.setInt(1, experience_de_travail.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion de ExperienceDeTravail : " + e.getMessage());
        }
    }

}
