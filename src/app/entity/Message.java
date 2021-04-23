/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.sql.Date;

/**
 *
 * @author Grim
 */
public class Message {

    private int id;
    private int conversationId;
    private String contenu;
    private Date dateCreation;
    private byte estProprietaire;
    private byte estVu;

    public Message(int id, int conversationId, String contenu, Date dateCreation, byte estProprietaire, byte estVu) {
        this.id = id;
        this.conversationId = conversationId;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.estProprietaire = estProprietaire;
        this.estVu = estVu;
    }

    public Message(int conversationId, String contenu, Date dateCreation, byte estProprietaire, byte estVu) {
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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public byte getEstProprietaire() {
        return estProprietaire;
    }

    public void setEstProprietaire(byte estProprietaire) {
        this.estProprietaire = estProprietaire;
    }

    public byte getEstVu() {
        return estVu;
    }

    public void setEstVu(byte estVu) {
        this.estVu = estVu;
    }

}
