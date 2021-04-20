package app.entity;

public class Revue {

    private int id;
    private int nbEtoiles;
    private String objet;
    private String description;

    // relations
    private int candidatureOffre;

    public Revue(int id, int nbEtoiles, String objet, String description, int candidatureOffre) {
        this.id = id;
        this.nbEtoiles = nbEtoiles;
        this.objet = objet;
        this.description = description;
        this.candidatureOffre = candidatureOffre;
    }

    public Revue(int nbEtoiles, String objet, String description, int candidatureOffre) {
        this.nbEtoiles = nbEtoiles;
        this.objet = objet;
        this.description = description;
        this.candidatureOffre = candidatureOffre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCandidatureOffre() {
        return candidatureOffre;
    }

    public void setCandidatureOffre(int candidatureOffre) {
        this.candidatureOffre = candidatureOffre;
    }

}
