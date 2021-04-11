package app.entity;

class CandidatureOffre {

    private int id;

    // relations
    private int candidat;
    private int offreDeTravail;
    private int[] interview;
    private int[] revue;

    public CandidatureOffre(int id, int candidat, int offreDeTravail, int[] interview, int[] revue) {
        this.id = id;
        this.candidat = candidat;
        this.offreDeTravail = offreDeTravail;
        this.interview = interview;
        this.revue = revue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidat() {
        return candidat;
    }

    public void setCandidat(int candidat) {
        this.candidat = candidat;
    }

    public int getOffreDeTravail() {
        return offreDeTravail;
    }

    public void setOffreDeTravail(int offreDeTravail) {
        this.offreDeTravail = offreDeTravail;
    }

    public int[] getInterview() {
        return interview;
    }

    public void setInterview(int[] interview) {
        this.interview = interview;
    }

    public int[] getRevue() {
        return revue;
    }

    public void setRevue(int[] revue) {
        this.revue = revue;
    }

}
