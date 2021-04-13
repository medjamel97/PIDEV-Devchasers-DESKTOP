/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Mission;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Akram
 */
public class MissionCrud {

    ObservableList<Mission> listMission = FXCollections.observableArrayList();

    private final Connection connexion;
    String mat;

    public MissionCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    public ObservableList<Mission> getMission() {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM mission");

            ResultSet resultSet = preparedStatement.executeQuery();
            
            int[] haha = { 10, 20, 30 };
            while (resultSet.next()) {
                listMission.add(new Mission(
                        resultSet.getInt("id"),
                        resultSet.getString("mission_name"),
                        resultSet.getString("description"),
                        resultSet.getDate("date"),
                        resultSet.getInt("nbheure"),
                        resultSet.getFloat("prix_h"),
                        resultSet.getInt("societe_id"),
                        haha,
                        haha
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) mission : " + e.getMessage());
        }
        return listMission;
    }

    public void ajouterMission(Mission u) {
        try {
            String req = "INSERT INTO mission (societe_id,date,mission_name,nbheure,prix_h,description) VALUES (?,?,?,?,?,?)";

            PreparedStatement st = connexion.prepareStatement(req);
            st.setNull(1, Types.NULL);
            st.setDate(2, (Date) u.getDate());
            //st.setString(6, u.getSalt());
            st.setString(3, u.getNom());
            //st.setDate(8, u.getLast_login());
            //st.setDate(10, u.getPassword_requested_at());
            st.setInt(4, u.getNombreHeures());
            st.setFloat(5, u.getPrixHeure());
            st.setString(6, u.getDescription());
            st.executeUpdate();
            System.out.println("mission ajoutée !!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
