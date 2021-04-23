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
public class Evenement {

    private int id;
    private int societeId;
    private String titre;
    private Date debut;
    private Date fin;
    private String description;
    private byte allDay;

    public Evenement(int id, int societeId, String titre, Date debut, Date fin, String description, byte allDay) {
        this.id = id;
        this.societeId = societeId;
        this.titre = titre;
        this.debut = debut;
        this.fin = fin;
        this.description = description;
        this.allDay = allDay;
    }

    public Evenement(int societeId, String titre, Date debut, Date fin, String description, byte allDay) {
        this.societeId = societeId;
        this.titre = titre;
        this.debut = debut;
        this.fin = fin;
        this.description = description;
        this.allDay = allDay;
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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getAllDay() {
        return allDay;
    }

    public void setAllDay(byte allDay) {
        this.allDay = allDay;
    }

}
