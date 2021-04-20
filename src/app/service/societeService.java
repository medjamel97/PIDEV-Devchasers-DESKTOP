package app.service;

import app.entity.Societe;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.interfaces.SocieteServiceInterface;

/**
 *
 * @author Grim
 */
public class SocieteService implements SocieteServiceInterface {

    ObservableList<Societe> listSociete = FXCollections.observableArrayList();

    private final Connection connexion;
    String mat;

    public SocieteService() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public ObservableList<Societe> getSociete() {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM societe");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listSociete.add(new Societe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_societe"),
                        resultSet.getDate("date_creation_societe"),
                        resultSet.getString("num_tel_societe"),
                        resultSet.getString("id_photo_societe")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) societe : " + e.getMessage());
        }
        return listSociete;
    }

    @Override
    public ObservableList<Societe> getSocieteParNom(String nom) {
        listSociete = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM societe WHERE nom_societe LIKE ?");
            preparedStatement.setString(1, nom + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listSociete.add(new Societe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_societe"),
                        resultSet.getDate("date_creation_societe"),
                        resultSet.getString("num_tel_societe"),
                        resultSet.getString("id_photo_societe")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur recherche societe : " + e.getMessage());
        }
        return listSociete;
    }

}
