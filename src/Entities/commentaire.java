/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Maher
 */
public class commentaire {
    private int id; 
    private int publication_id ;
    String description ; 
    
     public commentaire(int publication_id, String description) {
        this.publication_id = publication_id;
        this.description = description;
    }

    public commentaire(int id, int publication_id, String description) {
        this.id = id;
        this.publication_id = publication_id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(int publication_id) {
        this.publication_id = publication_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
