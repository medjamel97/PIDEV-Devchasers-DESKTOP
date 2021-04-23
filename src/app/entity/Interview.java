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
public class Interview {

    private int id;
    private int candidatureOffreId;
    private String objet;
    private String description;
    private String difficulte;

    public Interview(int id, int candidatureOffreId, String objet, String description, String difficulte) {
        this.id = id;
        this.candidatureOffreId = candidatureOffreId;
        this.objet = objet;
        this.description = description;
        this.difficulte = difficulte;
    }

    public Interview(int candidatureOffreId, String objet, String description, String difficulte) {
        this.candidatureOffreId = candidatureOffreId;
        this.objet = objet;
        this.description = description;
        this.difficulte = difficulte;
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

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

}
