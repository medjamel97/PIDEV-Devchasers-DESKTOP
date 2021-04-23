/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Publication;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Maher
 */
public interface PublicationCrudInterface {

    public List<Publication> getAll();

    public void incrementerjaime(int id);

    public boolean Add_nbr_like(int id, int nbr_like);

    public ObservableList<Publication> findpubBytitre(String titre);

    public Publication findpublicationById(int id);

    public void AjouterPublication(Publication publication);

    public void ModiferPublication(Publication p);

    public void SupprimerPublication(Publication publication);

}
