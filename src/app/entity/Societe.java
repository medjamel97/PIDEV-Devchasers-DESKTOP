/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.sql.Date;

/**
 *
 * @author Faten
 */
public class Societe {

    private int id;
    private String nom;
    private Date dateCreation;
    private String tel;
    private String idPhoto;

    public Societe(int id, String nom, Date dateCreation, String tel, String idPhoto) {
        this.id = id;
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.tel = tel;
        this.idPhoto = idPhoto;
    }

    public Societe(String nom, Date dateCreation, String tel, String idPhoto) {
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.tel = tel;
        this.idPhoto = idPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    @Override
    public String toString() {
        return "Societe{" + "id=" + id + ", nom=" + nom + ", dateCreation=" + dateCreation + ", tel=" + tel + ", idPhoto=" + idPhoto + '}';
    }
}
