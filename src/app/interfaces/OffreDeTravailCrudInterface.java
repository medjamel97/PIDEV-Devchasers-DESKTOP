/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.OffreDeTravail;
import java.util.List;

/**
 *
 * @author PC
 */
public interface OffreDeTravailCrudInterface {

    public boolean controleJob(String job);

    public boolean controleDescription(String description);

    public List<OffreDeTravail> getOffreDeTravailBySociete(int idSociete);

    public List<OffreDeTravail> displayOffre();

    public List<OffreDeTravail> getOffreDeTravailByCategorieId(int idCat);

    public List<OffreDeTravail> rechercheOffre(String recherche);

    public OffreDeTravail getOffreDeTravailById(int idOffre);

    public void ajouterOffre(OffreDeTravail o);

    public void ajouterOffreSansCategorie(OffreDeTravail o);

    public void ModifierOffre(OffreDeTravail o);

    public void ModifierOffreSansCategorie(OffreDeTravail o);

    public void SupprimerOffre(int id);

}
