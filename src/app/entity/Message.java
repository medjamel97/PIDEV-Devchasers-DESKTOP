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
public class Message {

    private int id;
    private int conversationId;
    private String contenu;
    private Timestamp dateCreation;
    private boolean estProprietaire;
    private boolean estVu;

    public Message(int id, int conversationId, String contenu, Timestamp dateCreation, boolean estProprietaire, boolean estVu) {
        this.id = id;
        this.conversationId = conversationId;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.estProprietaire = estProprietaire;
        this.estVu = estVu;
    }

    public Message(int conversationId, String contenu, Timestamp dateCreation, boolean estProprietaire, boolean estVu) {
        this.conversationId = conversationId;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.estProprietaire = estProprietaire;
        this.estVu = estVu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean getEstProprietaire() {
        return estProprietaire;
    }

    public void setEstProprietaire(boolean estProprietaire) {
        this.estProprietaire = estProprietaire;
    }

    public boolean getEstVu() {
        return estVu;
    }

    public void setEstVu(boolean estVu) {
        this.estVu = estVu;
    }

}
