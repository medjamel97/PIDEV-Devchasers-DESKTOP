/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.sql.Timestamp;

/**
 *
 * @author Maher
 */
public class Publication {

    private int id;
    private int candidatId;
    private String titre;
    private String description;
    private Timestamp date;
    private float pourcentageLike;

    public Publication(int id, int candidatId, String titre, String description, Timestamp date, float pourcentageLike) {
        this.id = id;
        this.candidatId = candidatId;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.pourcentageLike = pourcentageLike;
    }

    public Publication(int candidatId, String titre, String description, Timestamp date, float pourcentageLike) {
        this.candidatId = candidatId;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.pourcentageLike = pourcentageLike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(int candidatId) {
        this.candidatId = candidatId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public float getPourcentageLike() {
        return pourcentageLike;
    }

    public void setPourcentageLike(float pourcentageLike) {
        this.pourcentageLike = pourcentageLike;
    }

}
