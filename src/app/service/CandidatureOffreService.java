package app.service;

import app.entity.CandidatureOffre;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.interfaces.CandidatureOffreServiceInterface;

/**
 *
 * @author Grim
 */
public class CandidatureOffreService implements CandidatureOffreServiceInterface {

    ObservableList<CandidatureOffre> listCandidatureOffre = FXCollections.observableArrayList();

    private final Connection connexion;
    String mat;

    public CandidatureOffreService() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public ObservableList<CandidatureOffre> getCandidaturesOffre() {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM candidature_offre");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listCandidatureOffre.add(new CandidatureOffre(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getInt("offre_de_travail_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage candidatureOffre : " + e.getMessage());
        }
        return listCandidatureOffre;
    }

    @Override
    public CandidatureOffre getCandidatureOffreByCandidatOffre(int idCandidat, int idOffre) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM candidature_offre "
                    + "WHERE candidat_id = ? AND offre_de_travail_id = ?");
            preparedStatement.setInt(1, idCandidat);
            preparedStatement.setInt(2, idOffre);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new CandidatureOffre(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getInt("offre_de_travail_id")
                );
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
                    "INSERT INTO candidature_offre (candidat_id, offre_de_travail_id) VALUES (? , ? )");
            preparedStatement.setInt(1, candidatureOffre.getCandidat());
            preparedStatement.setInt(2, candidatureOffre.getOffreDeTravail());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success ajout candidature");
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout candidature : " + e.getMessage());
        }
    }
}
