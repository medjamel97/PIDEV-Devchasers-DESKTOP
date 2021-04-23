/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Commentaire;
import java.util.List;

/**
 *
 * @author Maher
 */
public interface CommentaireCrudInterface {

    public List<Commentaire> getAllCommentairesByPub(int idPublication);

    public void AjouterCommentaire(Commentaire c);

    public void SupprimerCommentaire(Commentaire c);

    public void ModiferCommentaire(Commentaire u);

}
