package app.service;

import app.entity.Interview;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.utils.BadWords;
import app.interfaces.InterviewCrudInterface;

/**
 *
 * @author Grim
 */
public class InterviewCrud implements InterviewCrudInterface {

    private static InterviewCrud instance;
    private final Connection connexion;

    public static InterviewCrud getInstance() {
        if (instance == null) {
            instance = new InterviewCrud();
        }
        return instance;

    }

    public InterviewCrud() {
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
    public void ajouterInterview(Interview interview) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO interview (candidature_offre_id, nb_etoiles, objet, description) VALUES ( ? , ? , ? , ? )");
            preparedStatement.setString(1, interview.getObjet());
            preparedStatement.setString(2, interview.getDescription());
            preparedStatement.setString(3, interview.getDifficulte());
            preparedStatement.setInt(4, interview.getCandidatureOffreId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success ajout interview");
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout interview : " + e.getMessage());
        }
    }

    @Override
    public void modifierInterview(Interview interview) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `interview` "
                    + "SET `objet` = ?, `description` = ?, `difficulte` = ?,`candidature_offre` = ? "
                    + "WHERE `id` = ?");
            preparedStatement.setString(1, interview.getObjet());
            preparedStatement.setString(2, interview.getDescription());
            preparedStatement.setString(3, interview.getDifficulte());
            preparedStatement.setInt(4, interview.getCandidatureOffreId());
            preparedStatement.setInt(5, interview.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success modification interview");
        } catch (SQLException e) {
            System.out.println("Erreur de modification interview : " + e.getMessage());
        }
    }

    @Override
    public void supprimerInterview(Interview interview) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `interview` WHERE `id`=?");
            preparedStatement.setInt(1, interview.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Succés suppresion interview");
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion interview : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Interview> getInterviewsParOffre(int idOffreDeTravail) {
        ObservableList<Interview> listInterview = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM interview i join candidature_offre c on i.candidature_offre_id = c.id "
                    + "WHERE c.offre_de_travail_id = ?");
            preparedStatement.setInt(1, idOffreDeTravail);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listInterview.add(new Interview(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidatureOffre"),
                        resultSet.getString("objet"),
                        resultSet.getString("description"),
                        resultSet.getString("difficulte")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) interview : " + e.getMessage());
        }
        return listInterview;
    }

    @Override
    public ObservableList<Interview> getInterviewsParOffreParObjet(int idOffreDeTravail, String objet) {
        ObservableList<Interview> listInterview = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM interview i join candidature_offre c on i.candidature_offre_id = c.id "
                    + "WHERE c.offre_de_travail_id = ? AND i.objet LIKE ?");
            preparedStatement.setInt(1, idOffreDeTravail);
            preparedStatement.setString(2, objet + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listInterview.add(new Interview(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidatureOffre"),
                        resultSet.getString("objet"),
                        resultSet.getString("description"),
                        resultSet.getString("difficulte")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) interview : " + e.getMessage());
        }
        return listInterview;
    }

    @Override
    public String getInterviewById() {
        return null;
    }
}
