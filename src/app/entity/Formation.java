/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.sql.Date;

/**
 *
 * @author Louay
 */
public class Formation {

    private int id;
    private int societeId;
    private String nom;
    private String filiere;
    private Date debut;
    private Date fin;

    public Formation(int id, int societeId, String nom, String filiere, Date debut, Date fin) {
        this.id = id;
        this.societeId = societeId;
        this.nom = nom;
        this.filiere = filiere;
        this.debut = debut;
        this.fin = fin;
    }

    public Formation(int societeId, String nom, String filiere, Date debut, Date fin) {
        this.societeId = societeId;
        this.nom = nom;
        this.filiere = filiere;
        this.debut = debut;
        this.fin = fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSocieteId() {
        return societeId;
    }

    public void setSocieteId(int societeId) {
        this.societeId = societeId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

}
