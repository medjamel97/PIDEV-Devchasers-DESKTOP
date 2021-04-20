/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import utils.DataSource;
import Entities.publication;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import pidevv.AffichageController;

/**
 *
 * @author Maher
 */
public class publicationService {

    private final Connection cnx;
    private static publicationService instance;
    ObservableList<publication> listPub = FXCollections.observableArrayList();

    public publicationService() {
        cnx = DataSource.getInstance().getCnx();
    }

    public static publicationService getInstance() {
        if (instance == null) {
            instance = new publicationService();
        }
        return instance;

    }

    public boolean AjouterPublication(publication p) {

        int verf = 0;
        try {
            String req;

            req = "INSERT INTO `publication`(`description`,`titre`,`date`)  VALUES (?,?,?)";
            PreparedStatement res = cnx.prepareStatement(req);

            //  res.setInt(5,1);
            res.setString(1, p.getDescription());
            res.setString(2, p.getTitre());

            res.setDate(3, (Date) p.getDate());
            verf = res.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(publicationService.class.getName()).log(Level.SEVERE, null, e);

        }
        if (verf == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean Add_nbr_like(int id, int nbr_like) {

        int test = 0;
        boolean check;
        try {
            PreparedStatement pst = cnx.prepareStatement("update publication set nbr_like = ?  where id=" + id);
            pst.setInt(1, nbr_like);

        } catch (SQLException ex) {
            Logger.getLogger(publicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (test == 0) {
            check = false;
        } else {
            check = true;
        }
        return check;

    }

    public List<publication> getAll() {
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT * FROM publication order by date desc");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listPub.add(new publication(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("description"),
                        resultSet.getInt("nbr_like"),
                        resultSet.getInt("all_like"),
                        resultSet.getDate("date"),
                        resultSet.getString("titre")));

            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) publication : " + e.getMessage());
        }
        return listPub;
    }
    

    public void incrementerjaime(int id) {
        try {
            PreparedStatement ste = cnx.prepareStatement("update publication set nbr_like=nbr_like+1  WHERE id='" + id);
            ste.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(publicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<publication> findpubBytitre(String titre){
        listPub = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(
                    "SELECT * FROM publication WHERE titre LIKE ?");
            preparedStatement.setString(1, titre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
          while (resultSet.next()) {
                listPub.add(new publication(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getString("description"),
                        resultSet.getInt("nbr_like"),
                        resultSet.getInt("all_like"),
                        resultSet.getDate("date"),
                        resultSet.getString("titre")));

            }
        } catch (SQLException e) {
            System.out.println("Erreur recherche publication : " + e.getMessage());
        }
        return listPub;
    }
    
    
    public publication findpublicationById(int id) {
        publication u = new publication();
        int count = 0;

        String requete = "select * from publication where id=" + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                u.setId(rs.getInt(1));
                u.setCandidat_id(rs.getInt(2));
                u.setDescription(rs.getString(3));
                u.setTitre(rs.getString(7));

                count++;
            }
            if (count == 0) {
                return null;
            } else {
                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(publicationService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public void SupprimerPublication(publication publication) {
      
             // ... user chose CANCEL or closed the dialog
        
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx.prepareStatement("DELETE FROM `publication` WHERE `id`=?");
            preparedStatement.setInt(1, publication.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion publication : " + e.getMessage());
        }
        
    }

    public void ModiferPublication(publication u) {
      PreparedStatement preparedStatement;
        System.out.println("aaaaaaa");
        try {
            preparedStatement = cnx.prepareStatement(
                    "UPDATE publication "
                    + "SET titre = ?, description = ? "
                    + "WHERE id = ?");
            preparedStatement.setString(1, u.getTitre());
            preparedStatement.setString(2, u.getDescription());
           // preparedStatement.setDate(3, u.getDate());
            preparedStatement.setInt(3, u.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success modification publication");
        } catch (SQLException e) {
            System.out.println("Erreur de modification publication : " + e.getMessage());
        }
    }

}
