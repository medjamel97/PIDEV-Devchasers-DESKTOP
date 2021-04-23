/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Societe;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public interface SocieteCrudInterface {

    public void ajouterSociete(Societe societe);

    public ObservableList<Societe> getSociete();

    public ObservableList<Societe> getSocieteParNom(String nom);

    public int getLastId();
}
