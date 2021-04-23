/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

/**
 *
 * @author Grim
 */
public class Revue {

    private int id;
    private int candidatureOffreId;
    private int nbEtoiles;
    private String objet;
    private String description;

    public Revue(int id, int candidatureOffreId, int nbEtoiles, String objet, String description) {
        this.id = id;
        this.candidatureOffreId = candidatureOffreId;
        this.nbEtoiles = nbEtoiles;
        this.objet = objet;
        this.description = description;
    }

    public Revue(int candidatureOffreId, int nbEtoiles, String objet, String description) {
        this.candidatureOffreId = candidatureOffreId;
        this.nbEtoiles = nbEtoiles;
        this.objet = objet;
        this.description = description;
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

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
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

}
