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
public class Conversation {

    private int id;
    private String nom;
    private int userExpediteurId;
    private int userDestinataireId;
    private Timestamp dateDernierMessage;

    public Conversation(int id, String nom, int userExpediteurId, int userDestinataireId, Timestamp dateDernierMessage) {
        this.id = id;
        this.nom = nom;
        this.userExpediteurId = userExpediteurId;
        this.userDestinataireId = userDestinataireId;
        this.dateDernierMessage = dateDernierMessage;
    }

    public Conversation(String nom, int userExpediteurId, int userDestinataireId, Timestamp dateDernierMessage) {
        this.nom = nom;
        this.userExpediteurId = userExpediteurId;
        this.userDestinataireId = userDestinataireId;
        this.dateDernierMessage = dateDernierMessage;
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

    public int getUserExpediteurId() {
        return userExpediteurId;
    }

    public void setUserExpediteurId(int userExpediteurId) {
        this.userExpediteurId = userExpediteurId;
    }

    public int getUserDestinataireId() {
        return userDestinataireId;
    }

    public void setUserDestinataireId(int userDestinataireId) {
        this.userDestinataireId = userDestinataireId;
    }

    public Timestamp getDateDernierMessage() {
        return dateDernierMessage;
    }

    public void setDateDernierMessage(Timestamp dateDernierMessage) {
        this.dateDernierMessage = dateDernierMessage;
    }

}
