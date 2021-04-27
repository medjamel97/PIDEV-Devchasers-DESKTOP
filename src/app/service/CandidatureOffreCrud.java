package app.service;

import app.entity.CandidatureOffre;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.interfaces.CandidatureOffreCrudInterface;

/**
 *
 * @author Grim
 */
public class CandidatureOffreCrud implements CandidatureOffreCrudInterface {

    private static CandidatureOffreCrud instance;
    private final Connection connexion;

    public static CandidatureOffreCrud getInstance() {
        if (instance == null) {
            instance = new CandidatureOffreCrud();
        }
        return instance;

    }

    public CandidatureOffreCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public ObservableList<CandidatureOffre> getCandidaturesOffre() {
        ObservableList<CandidatureOffre> listCandidatureOffre = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM candidature_offre");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listCandidatureOffre.add(new CandidatureOffre(
                        resultSet.getInt("id"),
                        resultSet.getInt("offre_de_travail_id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("etat")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage candidatureOffre : " + e.getMessage());
        }
        return listCandidatureOffre;
    }

    @Override
    public CandidatureOffre getCandidatureOffreByCandidatOffre(int idOffre, int idCandidat) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM candidature_offre "
                    + "WHERE  offre_de_travail_id = ? AND candidat_id = ?");
            preparedStatement.setInt(1, idOffre);
            preparedStatement.setInt(2, idCandidat);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new CandidatureOffre(
                        resultSet.getInt("id"),
                        resultSet.getInt("offre_de_travail_id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("etat")
                );
            } else {
                System.out.println("aucune candidature pour id_offre = " + idOffre + " avec le candidat = " + idCandidat);
            }

        } catch (SQLException e) {
            System.out.println("Erreur d'affichage candidatureOffre : " + e.getMessage());
        }

        return null;
    }

    @Override
    public void ajouterCandidature(CandidatureOffre candidatureOffre) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO candidature_offre ( offre_de_travail_id, candidat_id, etat ) VALUES (? , ? , ? )");
            preparedStatement.setInt(1, candidatureOffre.getOffreDeTravailId());
            preparedStatement.setInt(2, candidatureOffre.getCandidatId());
            preparedStatement.setString(3, candidatureOffre.getEtat());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success ajout candidature");
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout candidature : " + e.getMessage());
        }
    }

    @Override
    public void modifierEtat(CandidatureOffre candidatureOffre) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE candidature_offre "
                    + "SET etat = ? "
                    + "WHERE id = ?");
            preparedStatement.setString(1, candidatureOffre.getEtat());
            preparedStatement.setInt(2, candidatureOffre.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println("Erreur de modification etat : " + e.getMessage());
        }
    }

    @Override
    public CandidatureOffre getCandidaturesOffreById(int idCandidature) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT *"
                    + "FROM candidature_offre "
                    + "WHERE id = ?");
            preparedStatement.setInt(1, idCandidature);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new CandidatureOffre(
                        resultSet.getInt("id"),
                        resultSet.getInt("offre_de_travail_id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("etat")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) Candidature offre : " + e.getMessage());
        }
        System.out.println("aucun resultat candidature offre by id");
        return null;
    }

}
