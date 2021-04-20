/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author Maher
 */
public class publication {

    int id;
    int candidat_id;
    String description;
    String titre;
    Date date;
    int nbr_like;
    int all_like;

    public publication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidat_id() {
        return candidat_id;
    }

    public void setCandidat_id(int candidat_id) {
        this.candidat_id = candidat_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbr_like() {
        return nbr_like;
    }

    public void setNbr_like(int nbr_like) {
        this.nbr_like = nbr_like;
    }

    public int getAll_like() {
        return all_like;
    }

    public void setAll_like(int all_like) {
        this.all_like = all_like;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public publication(int id, int candidat_id, String description, int nbr_like, int all_like, Date date, String titre) {
        this.id = id;
        this.candidat_id = candidat_id;
        this.description = description;
        this.nbr_like = nbr_like;
        this.all_like = all_like;
        this.date = date;
        this.titre = titre;
    }

    public publication(int id, String titre,String description) {
         this.id = id;
           this.titre = titre;
        this.description = description;
      
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
