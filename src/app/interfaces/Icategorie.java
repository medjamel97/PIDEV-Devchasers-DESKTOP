/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Categorie;
import java.util.List;

/**
 *
 * @author Anis
 */
public interface Icategorie {
    
      public boolean controleNomcat (String nomcategorie);
       public void ajouterCat(Categorie c);
       public List<Categorie> DisplayCat();
        public void SupprimerCat(int id) ;
          public void ModifierCat(Categorie c);
    
}
