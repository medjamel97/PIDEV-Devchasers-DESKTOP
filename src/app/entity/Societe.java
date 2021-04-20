/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Faten
 */
@Entity
@Table(name = "societe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Societe.findAll", query = "SELECT s FROM Societe s"),
    @NamedQuery(name = "Societe.findById", query = "SELECT s FROM Societe s WHERE s.id = :id"),
    @NamedQuery(name = "Societe.findByNomSociete", query = "SELECT s FROM Societe s WHERE s.nomSociete = :nomSociete"),
    @NamedQuery(name = "Societe.findByDateCreationSociete", query = "SELECT s FROM Societe s WHERE s.dateCreationSociete = :dateCreationSociete"),
    @NamedQuery(name = "Societe.findByNumTelSociete", query = "SELECT s FROM Societe s WHERE s.numTelSociete = :numTelSociete"),
    @NamedQuery(name = "Societe.findByIdPhotoSociete", query = "SELECT s FROM Societe s WHERE s.idPhotoSociete = :idPhotoSociete")})
public class Societe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nom_societe")
    private String nomSociete;
    @Basic(optional = false)
    @Column(name = "date_creation_societe")
    @Temporal(TemporalType.DATE)
    private Date dateCreationSociete;
    @Basic(optional = false)
    @Column(name = "num_tel_societe")
    private String numTelSociete;
    @Basic(optional = false)
    @Column(name = "id_photo_societe")
    private String idPhotoSociete;
    @OneToOne(mappedBy = "societeId")
    private Utilisateur utilisateur;

    public Societe() {
    }

    public Societe(Integer id) {
        this.id = id;
    }

    public Societe(Integer id, String nomSociete, Date dateCreationSociete, String numTelSociete, String idPhotoSociete) {
        this.id = id;
        this.nomSociete = nomSociete;
        this.dateCreationSociete = dateCreationSociete;
        this.numTelSociete = numTelSociete;
        this.idPhotoSociete = idPhotoSociete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public Date getDateCreationSociete() {
        return dateCreationSociete;
    }

    public void setDateCreationSociete(Date dateCreationSociete) {
        this.dateCreationSociete = dateCreationSociete;
    }

    public String getNumTelSociete() {
        return numTelSociete;
    }

    public void setNumTelSociete(String numTelSociete) {
        this.numTelSociete = numTelSociete;
    }

    public String getIdPhotoSociete() {
        return idPhotoSociete;
    }

    public void setIdPhotoSociete(String idPhotoSociete) {
        this.idPhotoSociete = idPhotoSociete;
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
        if (!(object instanceof Societe)) {
            return false;
        }
        Societe other = (Societe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.entity.Societe[ id=" + id + " ]";
    }
    
}
