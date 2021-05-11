/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.User;
import javafx.collections.ObservableList;

/**
 *
 * @author Faten
 */
public interface UserCrudInterface {

    public void ajouterUtilisateur(User u);

    public ObservableList<User> getAll();

    public User getUserByEmail(String email);

    public User getUserById(int userId);

    public String encodePassword(String password);

    public boolean verifierEmail(String emailConnexion);

    public boolean verifierMotDePasse(String email, String passwordConnexion);

}
