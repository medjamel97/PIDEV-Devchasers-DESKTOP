/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Candidat;
import javafx.collections.ObservableList;

/**
 *
 * @author Faten
 */
public interface CandidatCrudInterface {

    public void ajouterCandidat(Candidat candidat);

    public void modifierCandidat(Candidat candidat);

    public void supprimerCandidat(Candidat candidat);

    public ObservableList<Candidat> getCandidats();

    public ObservableList<Candidat> getCandidatsByNomPrenom(String recherche);

    public Candidat getCandidatById(int idCandidat);

    public int getLastId();
}
