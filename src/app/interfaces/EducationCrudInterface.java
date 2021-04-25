/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Education;
import javafx.collections.ObservableList;

/**
 *
 * @author Grim
 */
public interface EducationCrudInterface {
    
    public void ajouterEducation(Education education);

    public void modifierEducation(Education education);

    public void supprimerEducation(Education education);

    public ObservableList<Education> getEducations();

    public Education getEducationById(int idEducation);

}
