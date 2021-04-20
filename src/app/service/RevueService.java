package app.service;

import app.entity.Revue;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.interfaces.RevueServiceInterface;
import app.utils.BadWords;

/**
 *
 * @author Grim
 */
public class RevueService implements RevueServiceInterface {

    ObservableList<Revue> listRevue = FXCollections.observableArrayList();

    private final Connection connexion;
    String mat;

    public RevueService() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public boolean controleEtoiles(int nbEtoiles) {
        return ((nbEtoiles >= 1) && (nbEtoiles <= 5));
    }

    @Override
    public boolean controleObjet(String objet) {
        return (objet.length() > 0);
    }

    @Override
    public boolean controleDescription(String description) {
        return (description.length() > 5);
    }

    @Override
    public boolean controleBadWords(String texte) {
        return !(BadWords.filterText(texte) || BadWords.filterText(texte));
    }

    @Override
    public void ajouterRevue(Revue revue) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO revue (candidature_offre_id, nb_etoiles, objet, description) VALUES ( ? , ? , ? , ? )");
            preparedStatement.setInt(1, revue.getCandidatureOffre());
            preparedStatement.setInt(2, revue.getNbEtoiles());
            preparedStatement.setString(3, revue.getObjet());
            preparedStatement.setString(4, revue.getDescription());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success ajout revue");
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
            System.out.println("Success modification revue");
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
            System.out.println("Succés suppresion revue");
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion revue : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Revue> getRevuesParOffre(int idOffreDeTravail) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM revue r join candidature_offre c on r.candidature_offre_id = c.id "
                    + "WHERE c.offre_de_travail_id = ?");
            preparedStatement.setInt(1, idOffreDeTravail);

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
    public ObservableList<Revue> getRevuesParOffreParObjet(int idOffreDeTravail, String objet) {
        listRevue = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM revue r join candidature_offre c on r.candidature_offre_id = c.id "
                    + "WHERE c.offre_de_travail_id = ? AND r.objet LIKE ?");
            preparedStatement.setInt(1, idOffreDeTravail);
            preparedStatement.setString(2, objet + "%");

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
