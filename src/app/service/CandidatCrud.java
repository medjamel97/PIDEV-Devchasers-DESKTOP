/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Candidat;
import app.entity.Revue;
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
            preparedStatement.setString(6, candidat.getIdPhoto());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout revue : " + e.getMessage());
        }
    }

    @Override
    public void modifierCandidat(Candidat candidat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimerCandidat(Candidat candidat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Candidat> getCandiadat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCandidatById() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
