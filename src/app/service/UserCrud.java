/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.User;
import app.interfaces.UserCrudInterface;
import app.utils.ConnecteurBD;
import de.mkammerer.argon2.Argon2Factory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Faten
 */
public class UserCrud implements UserCrudInterface {

    private static UserCrud instance;
    private final Connection connexion;

    public static UserCrud getInstance() {
        if (instance == null) {
            instance = new UserCrud();
        }
        return instance;

    }

    public UserCrud() {
        connexion = ConnecteurBD.driverBD();
    }

    @Override
    public void ajouterUtilisateur(User user) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO user (candidat_id, societe_id, email, roles, password, is_verified, is_set_up) VALUES ( ? , ? , ? , ? , ? , ? , ? )");

            if (user.getCandidatId() == Types.NULL) {
                preparedStatement.setNull(1, Types.NULL);
            } else {
                preparedStatement.setInt(1, user.getCandidatId());
            }
            if (user.getSocieteId() == Types.NULL) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setInt(2, user.getSocieteId());
            }
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setObject(4, user.getRoles());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setBoolean(6, user.getIsVerified());
            preparedStatement.setBoolean(7, user.getIsSetUp());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Success ajout user");
        } catch (SQLException e) {
            System.out.println("Erreur d'ajout user : " + e.getMessage());
        }
    }

    @Override
    public ObservableList<User> getAll() {
        ObservableList<User> listSociete = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM user"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listSociete.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getInt("societe_id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("is_verified"),
                        resultSet.getBoolean("is_set_up")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur de recuperation user by email : " + e.getMessage());
        }
        return listSociete;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM user WHERE email LIKE ?"
            );
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getInt("societe_id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("is_verified"),
                        resultSet.getBoolean("is_set_up")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur de recuperation user by email : " + e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserById(int userId) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT * FROM user WHERE id = ?"
            );
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getInt("candidat_id"),
                        resultSet.getInt("societe_id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("is_verified"),
                        resultSet.getBoolean("is_set_up")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur de recuperation user by email : " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean verifierEmail(String emailConnexion) {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT email FROM user WHERE email LIKE ?"
            );
            preparedStatement.setString(1, emailConnexion);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur de recuperation email : " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean verifierMotDePasse(String email, String passwordConnexion) {
        String password = "";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "SELECT password FROM user WHERE email LIKE ?"
            );
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de recuperation email : " + e.getMessage());
        }
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id).verify(password, passwordConnexion.toCharArray());
    }

    @Override
    public String encodePassword(String password) {
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id).hash(4, 65536, 1, password);
    }
}
