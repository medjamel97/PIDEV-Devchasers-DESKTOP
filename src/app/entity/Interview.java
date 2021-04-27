/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.sql.Timestamp;

/**
 *
 * @author Grim
 */
public class Interview {

    private int id;
    private int candidatureOffreId;
    private int difficulte;
    private String objet;
    private String description;
    private Timestamp dateCreation;

    public Interview(int id, int candidatureOffreId, int difficulte, String objet, String description, Timestamp dateCreation) {
        this.id = id;
        this.candidatureOffreId = candidatureOffreId;
        this.difficulte = difficulte;
        this.objet = objet;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    public Interview(int candidatureOffreId, int difficulte, String objet, String description, Timestamp dateCreation) {
        this.candidatureOffreId = candidatureOffreId;
        this.difficulte = difficulte;
        this.objet = objet;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidatureOffreId() {
        return candidatureOffreId;
    }

    public void setCandidatureOffreId(int candidatureOffreId) {
        this.candidatureOffreId = candidatureOffreId;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

}
