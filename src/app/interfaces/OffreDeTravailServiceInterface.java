/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.OffreDeTravail;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public interface OffreDeTravailServiceInterface {

    public ObservableList<OffreDeTravail> getOffreDeTravailBySociete(int idSociete);

}
