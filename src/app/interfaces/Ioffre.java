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
 * @author Anis
 */
public interface Ioffre {
    
    public boolean controleJob(String job);
    public boolean controleDescription(String description);
     public void ajouterOffre(OffreDeTravail o);
     public void SupprimerOffre(int id);
     public void ModifierOffre(OffreDeTravail o);
         
    public List<OffreDeTravail> displayOffre();
    
}
