/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.OffreDeTravail;
import app.interfaces.Ioffre;
import app.utils.ConnecteurBD;
import gestionentrepot.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;

/**
 *
 * @author Anis
 */
public class OffreDeTravailCrud implements Ioffre {

    private final Connection connexion;

    public OffreDeTravailCrud() {
        connexion = ConnecteurBD.driverBD();
    }

 
    @Override
   public boolean controleJob (String job) {
        return (job.length() > 0);
    }


    @Override
    public boolean controleDescription(String description) {
        return (description.length() > 5);
    }

 
    
    @Override
    public void ajouterOffre(OffreDeTravail o) {

        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "INSERT INTO offre_de_travail (job,description) VALUES ( ? , ? )");

            preparedStatement.setString(1, o.getJob());
            preparedStatement.setString(2, o.getDescription());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Offre ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    
    public List<OffreDeTravail> DisplayOffre() {

        List<OffreDeTravail> myList = new ArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM offre_de_travail");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                myList.add(new OffreDeTravail(
                        resultSet.getInt("id"),
                        resultSet.getString("job"),
                        resultSet.getString("description"),
                        resultSet.getInt("categorie_id")
                       ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) Offre : " + e.getMessage());
        }
        return myList;

    }

    public void start(Stage stage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<OffreDeTravail> displayOffre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    

    @Override
    public void SupprimerOffre(int id) {
       PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `offre_de_travail` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
             System.out.println("Offre supprimé");
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion offre : " + e.getMessage());
        }
    }

    @Override
    public void ModifierOffre(OffreDeTravail o) {
        
    
        {
        PreparedStatement preparedStatement;
        
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `offre_de_travail` "
                    + "SET `job` = ?, `description` = ?"
                    + "WHERE `id` = ?");
            preparedStatement.setString(1, o.getJob());
            preparedStatement.setString(2, o.getDescription());
            preparedStatement.setInt(3, o.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (SQLException e) {
            System.out.println("Erreur de modification candidat : " + e.getMessage());
        }
    
        }  }

  
    public List rechercheOffre(String x) {
        List<OffreDeTravail> offreList = new ArrayList<>();
        try {
            String requete = "Select * from offre_de_travail where job like '%" + x + "%' or description like '%" + x + "%'";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OffreDeTravail o = new OffreDeTravail();
                
                o.setId(rs.getInt("id"));
                o.setJob(rs.getString("job"));
                o.setDescription(rs.getString("description"));
                
                
                offreList.add(o);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return offreList;
    }

}
