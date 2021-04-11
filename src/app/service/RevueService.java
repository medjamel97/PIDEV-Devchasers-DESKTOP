package app.service;

import app.entity.Revue;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Grim
 */
public class RevueService implements RevueInterface {

    ObservableList<Revue> listRevue = FXCollections.observableArrayList();

    private final Connection connexion;
    String mat;

    public RevueService() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public void ajouterRevue(Revue revue) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO revue VALUES ( ? , ? , ? , ? , ? )");
            preparedStatement.setInt(1, revue.getId());
            preparedStatement.setNull(2, Types.NULL);
            preparedStatement.setInt(3, revue.getNbEtoiles());
            preparedStatement.setString(4, revue.getObjet());
            preparedStatement.setString(5, revue.getDescription());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout revue : " + e.getMessage());
        }
    }

    @Override
    public void modifierRevue(Revue revue) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `revue` "
                    + "SET `nb_etoiles` = ?, `objet` = ?, `description` = ? "
                    + "WHERE `id` = ?");
            preparedStatement.setInt(1, revue.getNbEtoiles());
            preparedStatement.setString(2, revue.getObjet());
            preparedStatement.setString(3, revue.getDescription());
            preparedStatement.setInt(4, revue.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de modification revue : " + e.getMessage());
        }
    }

    @Override
    public void supprimerRevue(Revue revue) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `revue` WHERE `id`=?");
            preparedStatement.setInt(1, revue.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion revue : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Revue> getRevues() {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM revue");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listRevue.add(new Revue(
                        resultSet.getInt("id"),
                        resultSet.getInt("nb_etoiles"),
                        resultSet.getString("objet"),
                        resultSet.getString("description"),
                        resultSet.getInt("candidature_offre_id")));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) revue : " + e.getMessage());
        }
        return listRevue;
    }

    @Override
    public String getRevueById() {
        return null;
    }
}
