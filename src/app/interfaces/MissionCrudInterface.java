/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Mission;
import javafx.collections.ObservableList;

/**
 *
 * @author Faten
 */
public interface MissionCrudInterface {

    public ObservableList<Mission> getMission();

    public void ajouterMission(Mission u);

    public void modifierMission(Mission mission);

    public void supprimerMission(Mission mission, boolean override);

}
