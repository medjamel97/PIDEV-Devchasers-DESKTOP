/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import javafx.scene.control.TextField;



/**
 *
 * @author Anis
 */

public class OffreDeTravail  {

   
    private int id;

    private String job;
    private String description;
    private int idCat;

    public OffreDeTravail(int id, String job, String description, int idCat) {
        this.id = id;
        this.job = job;
        this.description = description;
        this.idCat = idCat;
    }
   

    public OffreDeTravail(String job, String description) {

        this.job = job;
        this.description = description;
    }

    public OffreDeTravail(int id) {
        this.id = id;
    }

    public OffreDeTravail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public OffreDeTravail(int id, String job, String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public OffreDeTravail(TextField cat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }



    
}
