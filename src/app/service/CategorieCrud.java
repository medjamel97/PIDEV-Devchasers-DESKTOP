/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;


import app.entity.Categorie;
import app.interfaces.Icategorie;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 *
 * @author Anis
 */
public class CategorieCrud implements Icategorie  {

    private final Connection connexion;

    public CategorieCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    public CategorieCrud(TextField cat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param o
     */
    
    public void ajouterCat(Categorie c) {

        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "INSERT INTO categorie (nom_categorie) VALUES (?)");

            
            preparedStatement.setString(1,c.getNomcategorie());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Categorie ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    
    public List<Categorie> DisplayCat() {

        List<Categorie> myList = new ArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT id,nom_categorie FROM categorie");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                myList.add(new Categorie(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_categorie")
                       ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) categories : " + e.getMessage());
        }
        return myList;

    }

   

  
    
   

    public void SupprimerCat(int id) {
       PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM `categorie` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
             System.out.println("Categorie supprimé");
        } catch (SQLException e) {
            System.out.println("Erreur de suppresion categorie : " + e.getMessage());
        }
    }


    public void ModifierCat(Categorie c) {
        
    
        {
        PreparedStatement preparedStatement;
        
        try {
            preparedStatement = connexion.prepareStatement(
                    "UPDATE `categorie` "
                    + "SET `nom_categorie` = ?"
                    + "WHERE `id` = ?");
            preparedStatement.setString(1, c.getNomcategorie());
            preparedStatement.setInt(2, c.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (SQLException e) {
            System.out.println("Erreur de modification categorie : " + e.getMessage());
        }
    
        }  }

  
}
