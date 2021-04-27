package app.service;

import app.entity.Societe;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.interfaces.SocieteCrudInterface;
import java.sql.Date;

/**
 *
 * @author Grim
 */
public class SocieteCrud implements SocieteCrudInterface {

    private static SocieteCrud instance;
    private final Connection connexion;

    public static SocieteCrud getInstance() {
        if (instance == null) {
            instance = new SocieteCrud();
        }
        return instance;
    }

    public SocieteCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public void ajouterSociete(Societe societe) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO societe(id, nom, date_creation, tel, id_photo) VALUES ( ? , ? , ? , ? , ? )");
            preparedStatement.setInt(1, societe.getId());
            preparedStatement.setString(2, societe.getNom());
            preparedStatement.setDate(3, (Date) societe.getDateCreation());
            preparedStatement.setString(4, societe.getTel());
            preparedStatement.setString(5, "Empty");

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout societe : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Societe> getSociete() {
        ObservableList<Societe> listSociete = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM societe");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listSociete.add(new Societe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getDate("date_creation"),
                        resultSet.getString("tel"),
                        resultSet.getString("id_photo")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) societe : " + e.getMessage());
        }
        return listSociete;
    }

    @Override
    public ObservableList<Societe> getSocieteParNom(String nom) {
        ObservableList<Societe> listSociete = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM societe WHERE nom LIKE ?");
            preparedStatement.setString(1, nom + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listSociete.add(new Societe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getDate("date_creation"),
                        resultSet.getString("tel"),
                        resultSet.getString("id_photo")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur recherche societe : " + e.getMessage());
        }
        return listSociete;
    }

    @Override
    public int getLastId() {
        int lastId = 0;
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT id FROM societe");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lastId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de recuperation last societe id : " + e.getMessage());
        }
        return lastId;
    }

    @Override
    public Societe getSocieteById(int idSociete) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM societe WHERE id = ?");
            preparedStatement.setInt(1, idSociete);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Societe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getDate("date_creation"),
                        resultSet.getString("tel"),
                        resultSet.getString("id_photo")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur recherche societe : " + e.getMessage());
        }
        return null;
    }
}
