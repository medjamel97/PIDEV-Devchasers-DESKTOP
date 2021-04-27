/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.ExperienceDeTravail;
import javafx.collections.ObservableList;

/**
 *
 * @author Faten
 */
public interface ExperienceDeTravailCrudInterface {
    public void ajouterExperienceDeTravail(ExperienceDeTravail experience_de_travail);

    public void modifierExperienceDeTravail(ExperienceDeTravail experience_de_travail);

    public void supprimerExperienceDeTravail(ExperienceDeTravail experience_de_travail);

    public ObservableList<ExperienceDeTravail> getExperienceDeTravail();

    public ExperienceDeTravail getExperienceDeTravailById(int idExperienceDeTravail);
    
}
