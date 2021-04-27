/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Education;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.interfaces.EducationCrudInterface;

/**
 *
 * @author Faten
 */
public class EducationCrud implements EducationCrudInterface {

    private static EducationCrud instance;
    private final Connection connexion;

    public static EducationCrud getInstance() {
        if (instance == null) {
            instance = new EducationCrud();
        }
        return instance;

    }

    public EducationCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public void ajouterEducation(Education education) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO education(candidat_id,description,niveau_education,filiere,etablissement,ville,duree)VALUES ( ? , ? , ? , ? , ? ,?,? )");
            preparedStatement.setInt(1, education.getCandidatId());
            preparedStatement.setString(2, education.getDescription());
            preparedStatement.setString(3, education.getNiveauEducation());
            preparedStatement.setString(4, education.getFiliere());
            preparedStatement.setString(5, education.getEtablissement());
            preparedStatement.setString(6, education.getVille());
            preparedStatement.setString(7, education.getDuree());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout education : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Education> getEducations() {
        ObservableList<Education> listEducation = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM education");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listEducation.add(new Education(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("description"),
                        resultSet.getString("niveau_education"),
                        resultSet.getString("filiere"),
                        resultSet.getString("etablissement"),
                        resultSet.getString("ville"),
                        resultSet.getString("duree")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (toutes) educations : " + e.getMessage());
        }
        return listEducation;
    }

    @Override
    public ObservableList<Education> getEducationsByCandidat(int idCandidat) {
        ObservableList<Education> listEducation = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM education WHERE candidat_id = ?");
            preparedStatement.setInt(1, idCandidat);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listEducation.add(new Education(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("description"),
                        resultSet.getString("niveau_education"),
                        resultSet.getString("filiere"),
                        resultSet.getString("etablissement"),
                        resultSet.getString("ville"),
                        resultSet.getString("duree")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage pour un candidat (educations) : " + e.getMessage());
        }
        return listEducation;
    }

    @Override
    public Education getEducationById(int idEducation) {
        ObservableList<Education> listEducation = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM education WHERE id = ?");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listEducation.add(new Education(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("description"),
                        resultSet.getString("niveau_education"),
                        resultSet.getString("filiere"),
                        resultSet.getString("etablissement"),
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
    public void modifierEducation(Education education) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `education` "
                    + "SET `description` = ?, `niveau_education` = ?, `filiere` = ?, `etablissement` = ?, `ville` = ?,`duree` = ?" 
                    + "WHERE `id` = ?");
            preparedStatement.setString(1, education.getDescription());
            preparedStatement.setString(2, education.getNiveauEducation());
            preparedStatement.setString(3, education.getFiliere());
            preparedStatement.setString(4, education.getEtablissement());
            preparedStatement.setString(5, education.getVille());
            preparedStatement.setString(6, education.getDuree());
            preparedStatement.setInt(7, education.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("modifier avec succes");
        } catch (SQLException e) {
            System.out.println("Erreur de modification education : " + e.getMessage());
        }

    }

    @Override
    public void supprimerEducation(Education education) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `education` WHERE `id`=?");
            preparedStatement.setInt(1, education.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion education : " + e.getMessage());
        }
    }

}
