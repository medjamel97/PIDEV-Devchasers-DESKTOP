/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

/**
 *
 * @author Anis
 */
public class OffreDeTravail {

    private int id;

    private int idCat;
    private int idSociete;
    private String job;
    private String description;

    public OffreDeTravail(int id, int idCat, int idSociete, String job, String description) {
        this.id = id;
        this.idCat = idCat;
        this.idSociete = idSociete;
        this.job = job;
        this.description = description;
    }

    public OffreDeTravail(int id, String job, String description, int idCat) {
        this.id = id;
        this.job = job;
        this.description = description;
        this.idCat = idCat;
    }

    public OffreDeTravail(String job, String description) {

        this.job = job;
        this.description = description;
    }

    public OffreDeTravail(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    @Override
    public String toString() {
        return "OffreDeTravail{" + "id=" + id + ", job=" + job + ", description=" + description + ", idCat=" + idCat + '}';
    }

}