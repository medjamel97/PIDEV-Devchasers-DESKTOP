/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.sql.Date;

/**
 *
 * @author Akram
 */
public class Mission {

    private int id;
    private String nom;
    private String description;
    private Date date;
    private int nombreHeures;
    private float prixHeure;
    private int societe;
    private int[] candidatureMission;
    private int[] question;

    public Mission() {
    }

    public Mission(int id,String nom, String description, Date date, int nombreHeures, float prixHeure, int societe) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.nombreHeures = nombreHeures;
        this.prixHeure = prixHeure;
        this.societe = societe;
    }

    public Mission(String nom, String description, Date date, int nombreHeures, float prixHeure, int societe) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.nombreHeures = nombreHeures;
        this.prixHeure = prixHeure;
        this.societe = societe;
    }

    public Mission(int id, String nom, String description, Date date, int nombreHeures, float prixHeure, int societe, int[] candidatureMission, int[] question) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.nombreHeures = nombreHeures;
        this.prixHeure = prixHeure;
        this.societe = societe;
        this.candidatureMission = candidatureMission;
        this.question = question;
    }

    public Mission(String nom, String description, Date date, int nombreHeures, float prixHeure) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.nombreHeures = nombreHeures;
        this.prixHeure = prixHeure;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNombreHeures() {
        return nombreHeures;
    }

    public void setNombreHeures(int nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    public float getPrixHeure() {
        return prixHeure;
    }

    public void setPrixHeure(float prixHeure) {
        this.prixHeure = prixHeure;
    }

    public int getSociete() {
        return societe;
    }

    public void setSociete(int societe) {
        this.societe = societe;
    }

    public int[] getCandidatureMission() {
        return candidatureMission;
    }

    public void setCandidatureMission(int[] candidatureMission) {
        this.candidatureMission = candidatureMission;
    }

    public int[] getQuestion() {
        return question;
    }

    public void setQuestion(int[] question) {
        this.question = question;
    }

}
