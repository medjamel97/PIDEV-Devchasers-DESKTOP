package app.entity;

import java.sql.Date;

class Conversation {

    private int id;
    private Date $dateDernierMessage;

    // relations
    private int[] message;
    private int candidatExpediteur;
    private int candidatDestinataire;

    public Conversation(int id, Date $dateDernierMessage, int[] message, int candidatExpediteur, int candidatDestinataire) {
        this.id = id;
        this.$dateDernierMessage = $dateDernierMessage;
        this.message = message;
        this.candidatExpediteur = candidatExpediteur;
        this.candidatDestinataire = candidatDestinataire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date get$dateDernierMessage() {
        return $dateDernierMessage;
    }

    public void set$dateDernierMessage(Date $dateDernierMessage) {
        this.$dateDernierMessage = $dateDernierMessage;
    }

    public int[] getMessage() {
        return message;
    }

    public void setMessage(int[] message) {
        this.message = message;
    }

    public int getCandidatExpediteur() {
        return candidatExpediteur;
    }

    public void setCandidatExpediteur(int candidatExpediteur) {
        this.candidatExpediteur = candidatExpediteur;
    }

    public int getCandidatDestinataire() {
        return candidatDestinataire;
    }

    public void setCandidatDestinataire(int candidatDestinataire) {
        this.candidatDestinataire = candidatDestinataire;
    }

}
