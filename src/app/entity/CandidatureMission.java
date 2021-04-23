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
public class CandidatureMission {

    private int id;
    private int missionId;
    private int candidatId;

    public CandidatureMission(int id, int missionId, int candidatId) {
        this.id = id;
        this.missionId = missionId;
        this.candidatId = candidatId;
    }

    public CandidatureMission(int missionId, int candidatId) {
        this.missionId = missionId;
        this.candidatId = candidatId;
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

    public int getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(int candidatId) {
        this.candidatId = candidatId;
    }

}
