/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

/**
 *
 * @author Akram
 */
public class Question {

    private int id;
    private int missionId;
    private String description;
    private String reponse;

    public Question(int id, int missionId, String description, String reponse) {
        this.id = id;
        this.missionId = missionId;
        this.description = description;
        this.reponse = reponse;
    }

    public Question(int missionId, String description, String reponse) {
        this.missionId = missionId;
        this.description = description;
        this.reponse = reponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

}
