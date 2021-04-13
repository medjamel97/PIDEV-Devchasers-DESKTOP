/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Faten
 */
@Entity
@Table(name = "education")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Education.findAll", query = "SELECT e FROM Education e"),
    @NamedQuery(name = "Education.findById", query = "SELECT e FROM Education e WHERE e.id = :id"),
    @NamedQuery(name = "Education.findByDescription", query = "SELECT e FROM Education e WHERE e.description = :description"),
    @NamedQuery(name = "Education.findByNiveauEducation", query = "SELECT e FROM Education e WHERE e.niveauEducation = :niveauEducation"),
    @NamedQuery(name = "Education.findByFiliere", query = "SELECT e FROM Education e WHERE e.filiere = :filiere"),
    @NamedQuery(name = "Education.findByEtablissement", query = "SELECT e FROM Education e WHERE e.etablissement = :etablissement"),
    @NamedQuery(name = "Education.findByVille", query = "SELECT e FROM Education e WHERE e.ville = :ville"),
    @NamedQuery(name = "Education.findByDuree", query = "SELECT e FROM Education e WHERE e.duree = :duree")})
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "niveau_education")
    private String niveauEducation;
    @Basic(optional = false)
    @Column(name = "filiere")
    private String filiere;
    @Basic(optional = false)
    @Column(name = "etablissement")
    private String etablissement;
    @Basic(optional = false)
    @Column(name = "ville")
    private String ville;
    @Basic(optional = false)
    @Column(name = "duree")
    private String duree;
    @JoinColumn(name = "candidat_id", referencedColumnName = "id")
    @ManyToOne
    private Candidat candidatId;

    public Education() {
    }

    public Education(Integer id) {
        this.id = id;
    }

    public Education(Integer id, String description, String niveauEducation, String filiere, String etablissement, String ville, String duree) {
        this.id = id;
        this.description = description;
        this.niveauEducation = niveauEducation;
        this.filiere = filiere;
        this.etablissement = etablissement;
        this.ville = ville;
        this.duree = duree;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNiveauEducation() {
        return niveauEducation;
    }

    public void setNiveauEducation(String niveauEducation) {
        this.niveauEducation = niveauEducation;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Candidat getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Candidat candidatId) {
        this.candidatId = candidatId;
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
        if (!(object instanceof Education)) {
            return false;
        }
        Education other = (Education) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.entity.Education[ id=" + id + " ]";
    }
    
}
