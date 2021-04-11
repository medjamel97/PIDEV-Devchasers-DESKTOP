package app.entity;

class Interview {

    private int id;
    private String objet;
    private String description;
    private String difficulte;

    // relations
    private int candidatureOffre;

    public Interview(int id, String objet, String description, String difficulte, int candidatureOffre) {
        this.id = id;
        this.objet = objet;
        this.description = description;
        this.difficulte = difficulte;
        this.candidatureOffre = candidatureOffre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public int getCandidatureOffre() {
        return candidatureOffre;
    }

    public void setCandidatureOffre(int candidatureOffre) {
        this.candidatureOffre = candidatureOffre;
    }

}
