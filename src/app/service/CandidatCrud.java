/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Candidat;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.interfaces.CandidatCrudInterface;

/**
 *
 * @author Faten
 */
public class CandidatCrud implements CandidatCrudInterface {

    private static CandidatCrud instance;
    private final Connection connexion;

    public static CandidatCrud getInstance() {
        if (instance == null) {
            instance = new CandidatCrud();
        }
        return instance;

    }

    public CandidatCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public void ajouterCandidat(Candidat candidat) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO candidat(id,nom,prenom,date_naissance,sexe,tel,id_photo)VALUES ( ? , ? , ? , ? , ? , ? , ? )");
            preparedStatement.setInt(1, candidat.getId());
            preparedStatement.setString(2, candidat.getNom());
            preparedStatement.setString(3, candidat.getPrenom());
            preparedStatement.setDate(4, (Date) candidat.getDateNaissance());
            preparedStatement.setString(5, candidat.getSexe());
            preparedStatement.setString(6, candidat.getTel());
            preparedStatement.setString(7, "empty");

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout candidat : " + e.getMessage());
        }
    }

    @Override
    public void modifierCandidat(Candidat candidat) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `candidat` "
                    + "SET `nom` = ?, `prenom` = ?, `date_naissance` = ?, `sexe` = ?, `tel` = ?"
                    + "WHERE `id` = ?");
            preparedStatement.setString(1, candidat.getNom());
            preparedStatement.setString(2, candidat.getPrenom());
            preparedStatement.setDate(3, (Date) candidat.getDateNaissance());
            preparedStatement.setString(4, candidat.getSexe());
            preparedStatement.setString(5, candidat.getTel());
            preparedStatement.setInt(6, candidat.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de modification candidat : " + e.getMessage());
        }
    }

    @Override
    public void supprimerCandidat(Candidat candidat) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `candidat` WHERE `id`=?");
            preparedStatement.setInt(1, candidat.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion candidat : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Candidat> getCandiadats() {
        ObservableList<Candidat> listCandidat = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM candidat");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listCandidat.add(new Candidat(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("date_naissance"),
                        resultSet.getString("sexe"),
                        resultSet.getString("tel"),
                        null
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) candidat : " + e.getMessage());
        }
        return listCandidat;
    }

    @Override
    public Candidat getCandidatById(int idCandidat) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM candidat WHERE id = ?");
            preparedStatement.setInt(1, idCandidat);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Candidat(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("date_naissance"),
                        resultSet.getString("sexe"),
                        resultSet.getString("tel"),
                        null
                );
            }
        } catch (SQLException e) {
            System.out.println(" d'affichage (tout) candidat : " + e.getMessage());
        }
        System.out.println("Aucun resultat candidat pour id = " + idCandidat);
        return null;
    }

    @Override
    public int getLastId() {
        int lastId = 0;
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT id FROM candidat");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lastId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de recuperation last candidat id : " + e.getMessage());
        }
        return lastId;
    }
}
