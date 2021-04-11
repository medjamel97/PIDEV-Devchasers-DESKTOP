package app.entity;

import java.sql.Date;

class Message
{
    private int $id;
    private String contenu;
    private Date dateCreation;
    private boolean estProprietaire;
    private boolean estVu;

    // relations
    private int conversation;

    public Message(int $id, String contenu, Date dateCreation, boolean estProprietaire, boolean estVu, int conversation) {
        this.$id = $id;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.estProprietaire = estProprietaire;
        this.estVu = estVu;
        this.conversation = conversation;
    }

    public int get$id() {
        return $id;
    }

    public void set$id(int $id) {
        this.$id = $id;
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

    public boolean isEstProprietaire() {
        return estProprietaire;
    }

    public void setEstProprietaire(boolean estProprietaire) {
        this.estProprietaire = estProprietaire;
    }

    public boolean isEstVu() {
        return estVu;
    }

    public void setEstVu(boolean estVu) {
        this.estVu = estVu;
    }

    public int getConversation() {
        return conversation;
    }

    public void setConversation(int conversation) {
        this.conversation = conversation;
    }
    
    
}