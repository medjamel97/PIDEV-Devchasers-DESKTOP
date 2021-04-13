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
import java.sql.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Faten
 */
public class CandidatCrud implements candidatInterface {

    ObservableList<Candidat> listCandiat = FXCollections.observableArrayList();
    private final Connection connexion;

    public CandidatCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    public void ajouterCandidat(Candidat candidat) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO candidat(nom,prenom,date_naissance,sexe,tel,id_photo)VALUES ( ? , ? , ? , ? , ? , ? )");
            preparedStatement.setString(1, candidat.getNom());
            preparedStatement.setString(2, candidat.getPrenom());
            preparedStatement.setDate(3, (Date) candidat.getDateNaissance());
            preparedStatement.setString(4, candidat.getSexe());
            preparedStatement.setString(5, candidat.getTel());
            preparedStatement.setString(6, "empty");

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
            preparedStatement.setDate(3, candidat.getDateNaissance());
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
    public ObservableList<Candidat> getCandiadat() {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM candidat");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listCandiat.add(new Candidat(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("date_naissance"),
                        resultSet.getString("sexe"),
                        resultSet.getString("tel")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) candidat : " + e.getMessage());
        }
        return listCandiat;
    }

    @Override
    public String getCandidatById() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
