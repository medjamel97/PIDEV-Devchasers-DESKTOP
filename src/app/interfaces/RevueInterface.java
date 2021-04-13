/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Revue;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public interface RevueInterface {

    public void ajouterRevue(Revue v);

    public void modifierRevue(Revue v);

    public void supprimerRevue(Revue v);

    public ObservableList<Revue> getRevues();

    public ObservableList<Revue> getRevuesParObjet(String objet);

    public String getRevueById();

}
