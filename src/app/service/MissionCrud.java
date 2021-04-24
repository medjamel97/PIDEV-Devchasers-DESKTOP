/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.MainApp;
import app.entity.Mission;
import app.interfaces.MissionCrudInterface;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Akram
 */
public class MissionCrud implements MissionCrudInterface {

    private static MissionCrud instance;
    private final Connection connexion;

    public static MissionCrud getInstance() {
        if (instance == null) {
            instance = new MissionCrud();
        }
        return instance;

    }

    public MissionCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public ObservableList<Mission> getMission() {
        ObservableList<Mission> listMission = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM mission");

            ResultSet resultSet = preparedStatement.executeQuery();

            int[] haha = {10, 20, 30};
            while (resultSet.next()) {
                listMission.add(new Mission(
                        resultSet.getInt("id"),
                        resultSet.getInt("societe_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getDate("date"),
                        resultSet.getInt("nombre_heures"),
                        resultSet.getFloat("prix_heure"),
                        resultSet.getString("ville"),
                        resultSet.getString("longitude"),
                        resultSet.getString("latitude")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) mission : " + e.getMessage());
        }
        return listMission;
    }

    @Override
    public void ajouterMission(Mission u) {
        try {
            String req = "INSERT INTO mission (societe_id,date,nom,nombre_heures,prix_heure,description,ville,longitude,latitude) VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement st = connexion.prepareStatement(req);
            st.setInt(1, MainApp.getSession().getSocieteId());
            st.setDate(2, (Date) u.getDate());
            st.setString(3, u.getNom());
            st.setInt(4, u.getNombreHeures());
            st.setDouble(5, u.getPrixHeure());
            st.setString(6, u.getDescription());
            st.setString(7, u.getVille());
            st.setString(8, u.getLongitude());
            st.setString(9, u.getLatitude());
            st.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifierMission(Mission mission) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `mission` "
                    + "SET `date` = ?, `nom` = ?, `nombre_heures` = ?, `prix_heure` = ?, `description` = ? ,`ville` = ? ,`longitude` = ? ,`latitude` = ?  "
                    + "WHERE `id` = ? ");
            preparedStatement.setDate(1, (Date) mission.getDate());
            preparedStatement.setString(2, mission.getNom());
            preparedStatement.setInt(3, mission.getNombreHeures());
            preparedStatement.setDouble(4, mission.getPrixHeure());
            preparedStatement.setString(5, mission.getDescription());
            preparedStatement.setString(6, mission.getVille());
            preparedStatement.setString(7, mission.getLongitude());
            preparedStatement.setString(8, mission.getLatitude());
            preparedStatement.setInt(9, mission.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de modification mission : " + e.getMessage());
        }
    }

    @Override
    public void supprimerMission(Mission mission) {
        PreparedStatement preparedStatement;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText(null);
        alert.setContentText("vous voulez vraiment supprimer cette mission ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                preparedStatement = connexion.prepareStatement("DELETE FROM `mission` WHERE `id`=?");
                preparedStatement.setInt(1, mission.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("Erreur de suppresion mission : " + e.getMessage());
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
}
