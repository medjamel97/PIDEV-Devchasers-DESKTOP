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
    public boolean controleDifficulte(int difficulte) {
        return ((difficulte >= 0) && (difficulte <= 4));
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
                    "INSERT INTO interview (candidature_offre_id, difficulte, description, objet, date_creation ) VALUES ( ? , ? , ? , ? , ?)");
            preparedStatement.setInt(1, interview.getCandidatureOffreId());
            preparedStatement.setInt(2, interview.getDifficulte());
            preparedStatement.setString(3, interview.getObjet());
            preparedStatement.setString(4, interview.getDescription());
            preparedStatement.setTimestamp(5, interview.getDateCreation());

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
                    "UPDATE interview "
                    + "SET difficulte = ?, objet = ?, description = ?, date_creation = ? "
                    + "WHERE id = ?");
            preparedStatement.setInt(1, interview.getDifficulte());
            preparedStatement.setString(2, interview.getObjet());
            preparedStatement.setString(3, interview.getDescription());
            preparedStatement.setTimestamp(4, interview.getDateCreation());
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
            preparedStatement = connexion.prepareStatement("DELETE FROM interview WHERE id=?");
            preparedStatement.setInt(1, interview.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Succés suppresion interview");
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion interview : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Interview> getAllInterviews() {
        ObservableList<Interview> listInterview = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM interview");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listInterview.add(new Interview(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidature_offre_id"),
                        resultSet.getInt("difficulte"),
                        resultSet.getString("objet"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("date_creation")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) interview : " + e.getMessage());
        }
        return listInterview;
    }

    @Override
    public ObservableList<Interview> getInterviewsParOffre(int idOffreDeTravail) {
        ObservableList<Interview> listInterview = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM interview i join candidature_offre c on i.candidature_offre_id = c.id "
                    + "WHERE c.offre_de_travail_id = ? "
                    + "ORDER BY i.date_creation ");
            preparedStatement.setInt(1, idOffreDeTravail);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listInterview.add(new Interview(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidature_offre_id"),
                        resultSet.getInt("difficulte"),
                        resultSet.getString("objet"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("date_creation")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (par offre) interview : " + e.getMessage());
        }
        return listInterview;
    }

    @Override
    public ObservableList<Interview> getInterviewsParOffreParDifficulte(int idOffreDeTravail, int difficulte) {
        ObservableList<Interview> listInterview = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM interview i join candidature_offre c on i.candidature_offre_id = c.id "
                    + "WHERE c.offre_de_travail_id = ? AND i.difficulte = ? "
                    + "ORDER BY i.date_creation ");
            preparedStatement.setInt(1, idOffreDeTravail);
            preparedStatement.setInt(2, difficulte);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listInterview.add(new Interview(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidature_offre_id"),
                        resultSet.getInt("difficulte"),
                        resultSet.getString("objet"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("date_creation")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage par difficulte interview : " + e.getMessage());
        }
        return listInterview;
    }

    @Override
    public ObservableList<Interview> getInterviewsParOffreParCandidat(int idOffreDeTravail, String nomPrenom) {
        ObservableList<Interview> listInterview = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM interview i "
                    + "join candidature_offre c on i.candidature_offre_id = c.id "
                    + "join candidat can on c.candidat_id = can.id "
                    + "WHERE c.offre_de_travail_id = ? AND (can.nom LIKE ? OR can.prenom LIKE ?) "
                    + "ORDER BY i.date_creation ");
            preparedStatement.setInt(1, idOffreDeTravail);
            preparedStatement.setString(2, nomPrenom + "%");
            preparedStatement.setString(3, nomPrenom + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listInterview.add(new Interview(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidature_offre_id"),
                        resultSet.getInt("difficulte"),
                        resultSet.getString("objet"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("date_creation")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur de recherche interviews : " + e.getMessage());
        }
        return listInterview;
    }
}
