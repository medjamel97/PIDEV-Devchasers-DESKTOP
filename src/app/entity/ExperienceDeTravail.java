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
@Table(name = "experience_de_travail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExperienceDeTravail.findAll", query = "SELECT e FROM ExperienceDeTravail e"),
    @NamedQuery(name = "ExperienceDeTravail.findById", query = "SELECT e FROM ExperienceDeTravail e WHERE e.id = :id"),
    @NamedQuery(name = "ExperienceDeTravail.findByDescription", query = "SELECT e FROM ExperienceDeTravail e WHERE e.description = :description"),
    @NamedQuery(name = "ExperienceDeTravail.findByTitreEmploi", query = "SELECT e FROM ExperienceDeTravail e WHERE e.titreEmploi = :titreEmploi"),
    @NamedQuery(name = "ExperienceDeTravail.findByNomEntreprise", query = "SELECT e FROM ExperienceDeTravail e WHERE e.nomEntreprise = :nomEntreprise"),
    @NamedQuery(name = "ExperienceDeTravail.findByVille", query = "SELECT e FROM ExperienceDeTravail e WHERE e.ville = :ville"),
    @NamedQuery(name = "ExperienceDeTravail.findByDuree", query = "SELECT e FROM ExperienceDeTravail e WHERE e.duree = :duree")})
public class ExperienceDeTravail implements Serializable {

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
    @Column(name = "titre_emploi")
    private String titreEmploi;
    @Basic(optional = false)
    @Column(name = "nom_entreprise")
    private String nomEntreprise;
    @Basic(optional = false)
    @Column(name = "ville")
    private String ville;
    @Basic(optional = false)
    @Column(name = "duree")
    private String duree;
    @JoinColumn(name = "candidat_id", referencedColumnName = "id")
    @ManyToOne
    private Candidat candidatId;

    public ExperienceDeTravail() {
    }

    public ExperienceDeTravail(Integer id) {
        this.id = id;
    }

    public ExperienceDeTravail(Integer id, String description, String titreEmploi, String nomEntreprise, String ville, String duree) {
        this.id = id;
        this.description = description;
        this.titreEmploi = titreEmploi;
        this.nomEntreprise = nomEntreprise;
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

    public String getTitreEmploi() {
        return titreEmploi;
    }

    public void setTitreEmploi(String titreEmploi) {
        this.titreEmploi = titreEmploi;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
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
        if (!(object instanceof ExperienceDeTravail)) {
            return false;
        }
        ExperienceDeTravail other = (ExperienceDeTravail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.entity.ExperienceDeTravail[ id=" + id + " ]";
    }
    
}
