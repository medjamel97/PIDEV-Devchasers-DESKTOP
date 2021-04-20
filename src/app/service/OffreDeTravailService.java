package app.service;

import app.entity.OffreDeTravail;
import app.entity.Societe;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.interfaces.OffreDeTravailServiceInterface;

/**
 *
 * @author Grim
 */
public class OffreDeTravailService implements OffreDeTravailServiceInterface {

    ObservableList<OffreDeTravail> listOffreDeTravail = FXCollections.observableArrayList();

    private final Connection connexion;
    String mat;

    public OffreDeTravailService() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public ObservableList<OffreDeTravail> getOffreDeTravailBySociete(int idSociete) {
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
}
