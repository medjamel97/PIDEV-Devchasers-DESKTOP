/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.service;

import app.entity.Candidat;
import javafx.collections.ObservableList;

/**
 *
 * @author Faten
 */
public interface candidatInterface {

    public void ajouterCandidat(Candidat candidat);

    public void modifierCandidat(Candidat candidat);

    public void supprimerCandidat(Candidat candidat);

    public ObservableList<Candidat> getCandiadat();

    public String getCandidatById();

}
