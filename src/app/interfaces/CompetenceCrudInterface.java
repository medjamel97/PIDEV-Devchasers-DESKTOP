/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Competence;
import javafx.collections.ObservableList;

/**
 *
 * @author Faten
 */
public interface CompetenceCrudInterface {
    public void ajouterCompetence(Competence competence);

    public void modifierCompetence(Competence competence);

    public void supprimerCompetence(Competence competence);

    public ObservableList<Competence> getCompetence();

    public Competence getCompetenceById(int idCompetence);
    
        public ObservableList<Competence> getCompetenceByCandidat(int idCandidat);

    public int getLastId();
}
