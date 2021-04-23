/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Categorie;
import app.utils.ConnecteurBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import app.interfaces.CategorieCrudInterface;

/**
 *
 * @author Anis
 */
public class CategorieCrud implements CategorieCrudInterface {

    private static CategorieCrud instance;
    private final Connection connexion;

    public static CategorieCrud getInstance() {
        if (instance == null) {
            instance = new CategorieCrud();
        }
        return instance;

    }

    public CategorieCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public void ajouterCat(Categorie c) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO categorie (nom) VALUES (?)");
            preparedStatement.setString(1, c.getNom());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Categorie ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Categorie> DisplayCat() {

        List<Categorie> myList = new ArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM categorie");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                myList.add(new Categorie(
                        resultSet.getInt("id"),
                        resultSet.getString("nom")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'affichage (tout) categories : " + e.getMessage());
        }
        return myList;

    }

    @Override
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

    @Override
    public void ModifierCat(Categorie c) {
        {
            PreparedStatement preparedStatement;

            try {
                preparedStatement = connexion.prepareStatement(
                        "UPDATE `categorie` "
                        + "SET `nom` = ?"
                        + "WHERE `id` = ?");
                preparedStatement.setString(1, c.getNom());
                preparedStatement.setInt(2, c.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();

            } catch (SQLException e) {
                System.out.println("Erreur de modification categorie : " + e.getMessage());
            }

        }
    }

}
