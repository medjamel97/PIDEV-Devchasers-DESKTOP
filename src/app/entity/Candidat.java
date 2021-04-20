/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.io.Serializable;
import java.util.Collection;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Faten
 */
@Entity
@Table(name = "candidat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Candidat.findAll", query = "SELECT c FROM Candidat c"),
    @NamedQuery(name = "Candidat.findById", query = "SELECT c FROM Candidat c WHERE c.id = :id"),
    @NamedQuery(name = "Candidat.findByNom", query = "SELECT c FROM Candidat c WHERE c.nom = :nom"),
    @NamedQuery(name = "Candidat.findByPrenom", query = "SELECT c FROM Candidat c WHERE c.prenom = :prenom"),
    @NamedQuery(name = "Candidat.findByDateNaissance", query = "SELECT c FROM Candidat c WHERE c.dateNaissance = :dateNaissance"),
    @NamedQuery(name = "Candidat.findBySexe", query = "SELECT c FROM Candidat c WHERE c.sexe = :sexe"),
    @NamedQuery(name = "Candidat.findByTel", query = "SELECT c FROM Candidat c WHERE c.tel = :tel"),
    @NamedQuery(name = "Candidat.findByIdPhoto", query = "SELECT c FROM Candidat c WHERE c.idPhoto = :idPhoto")})
public class Candidat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @Column(name = "prenom")
    private String prenom;
    @Basic(optional = false)
    @Column(name = "date_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Basic(optional = false)
    @Column(name = "sexe")
    private String sexe;
    @Basic(optional = false)
    @Column(name = "tel")
    private String tel;
    @Basic(optional = false)
    @Column(name = "id_photo")
    private String idPhoto;
    @OneToMany(mappedBy = "candidatId")
    private Collection<Competence> competenceCollection;
    @OneToMany(mappedBy = "candidatId")
    private Collection<Education> educationCollection;
    @OneToMany(mappedBy = "candidatId")
    private Collection<ExperienceDeTravail> experienceDeTravailCollection;
    @OneToOne(mappedBy = "candidatId")
    private Utilisateur utilisateur;

    public Candidat() {
    }

    public Candidat(Integer id) {
        this.id = id;
    }

    public Candidat(String nom, String prenom, Date dateNaissance, String sexe, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.tel = tel;
    }

    public Candidat(int id, String nom, String prenom, Date dateNaissance, String sexe, String tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.tel = tel;
    }

    public Candidat(Integer id, String nom, String prenom, Date dateNaissance, String sexe, String tel, String idPhoto) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.tel = tel;
        this.idPhoto = idPhoto;
    }

    public Candidat(int aInt, int aInt0, String string, String string0, int aInt1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    @XmlTransient
    public Collection<Competence> getCompetenceCollection() {
        return competenceCollection;
    }

    public void setCompetenceCollection(Collection<Competence> competenceCollection) {
        this.competenceCollection = competenceCollection;
    }

    @XmlTransient
    public Collection<Education> getEducationCollection() {
        return educationCollection;
    }

    public void setEducationCollection(Collection<Education> educationCollection) {
        this.educationCollection = educationCollection;
    }

    @XmlTransient
    public Collection<ExperienceDeTravail> getExperienceDeTravailCollection() {
        return experienceDeTravailCollection;
    }

    public void setExperienceDeTravailCollection(Collection<ExperienceDeTravail> experienceDeTravailCollection) {
        this.experienceDeTravailCollection = experienceDeTravailCollection;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Candidat)) {
            return false;
        }
        Candidat other = (Candidat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.entity.Candidat[ id=" + id + " ]";
    }

}
