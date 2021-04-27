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
    private int candidatExpediteurId;
    private int candidatDestinataireId;
    private Timestamp dateDernierMessage;

    public Conversation(int id, int candidatExpediteurId, int candidatDestinataireId, Timestamp dateDernierMessage) {
        this.id = id;
        this.candidatExpediteurId = candidatExpediteurId;
        this.candidatDestinataireId = candidatDestinataireId;
        this.dateDernierMessage = dateDernierMessage;
    }

    public Conversation(int candidatExpediteurId, int candidatDestinataireId, Timestamp dateDernierMessage) {
        this.candidatExpediteurId = candidatExpediteurId;
        this.candidatDestinataireId = candidatDestinataireId;
        this.dateDernierMessage = dateDernierMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidatExpediteurId() {
        return candidatExpediteurId;
    }

    public void setCandidatExpediteurId(int candidatExpediteurId) {
        this.candidatExpediteurId = candidatExpediteurId;
    }

    public int getCandidatDestinataireId() {
        return candidatDestinataireId;
    }

    public void setCandidatDestinataireId(int candidatDestinataireId) {
        this.candidatDestinataireId = candidatDestinataireId;
    }

    public Timestamp getDateDernierMessage() {
        return dateDernierMessage;
    }

    public void setDateDernierMessage(Timestamp dateDernierMessage) {
        this.dateDernierMessage = dateDernierMessage;
    }

}
