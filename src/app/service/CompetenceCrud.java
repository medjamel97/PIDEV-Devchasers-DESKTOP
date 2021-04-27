/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Competence;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.interfaces.CompetenceCrudInterface;
import app.interfaces.CompetenceCrudInterface;

/**
 *
 * @author Faten
 */
public class CompetenceCrud implements CompetenceCrudInterface {
    private static CompetenceCrud instance;
    private final Connection connexion;

    public static CompetenceCrud getInstance() {
        if (instance == null) {
            instance = new CompetenceCrud();
        }
        return instance;

    }

    public CompetenceCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public void ajouterCompetence(Competence competence) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO competence(candidat_id,name,level)VALUES ( ? ,? , ? )");
            preparedStatement.setInt(1, competence.getCandidatId());
            preparedStatement.setString(2, competence.getName());
            preparedStatement.setInt(3, competence.getLevel());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout competence : " + e.getMessage());
        }
    }
    
    
    @Override
    public ObservableList<Competence> getCompetenceByCandidat(int idCandidat) {
        ObservableList<Competence> listCompetence = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM competence WHERE candidat_id = ?");
            preparedStatement.setInt(1, idCandidat);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listCompetence.add(new Competence(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("level")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage pour un candidat (educations) : " + e.getMessage());
        }
        return listCompetence;
    }


    @Override
    public void modifierCompetence(Competence competence) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `competence` "
                    + "SET `name` = ?, `level` = ? "
                    + "WHERE `id` = ? ");
            preparedStatement.setString(1, competence.getName());
            preparedStatement.setInt(2, competence.getLevel());
            preparedStatement.setInt(3, competence.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de modification competence : " + e.getMessage());
        }
    }

    @Override
    public void supprimerCompetence(Competence competence) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `competence` WHERE `id`=?");
            preparedStatement.setInt(1, competence.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion Competence : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Competence> getCompetence() {
        ObservableList<Competence> listCompetence= FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM competence");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listCompetence.add(new Competence(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("level")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) Competence : " + e.getMessage());
        }
        return listCompetence;
    }

    @Override
    public Competence getCompetenceById(int idCompetence) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM competence WHERE id = ?");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Competence(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("level")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) Competence : " + e.getMessage());
        }
        return null;
    }

    @Override
    public int getLastId() {
        int lastId = 0;
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT id FROM competence");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lastId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de recuperation last Competence id : " + e.getMessage());
        }
        return lastId;
    }
    
}
