/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Interview;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public interface InterviewCrudInterface {

    public boolean controleDifficulte(int nbDifficulte);

    public boolean controleObjet(String objet);

    public boolean controleDescription(String description);

    public boolean controleBadWords(String texte);

    public void ajouterInterview(Interview v);

    public void modifierInterview(Interview v);

    public void supprimerInterview(Interview v);

    public ObservableList<Interview> getAllInterviews();

    public ObservableList<Interview> getInterviewsParOffre(int idOffreDeTravail);

    public ObservableList<Interview> getInterviewsParOffreParDifficulte(int idOffreDeTravail, int difficulte);

    public ObservableList<Interview> getInterviewsParOffreParCandidat(int idOffreDeTravail, String nomPrenom);

}
