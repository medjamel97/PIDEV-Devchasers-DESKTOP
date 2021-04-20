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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Faten
 */
@Entity
@Table(name = "utilisateur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u"),
    @NamedQuery(name = "Utilisateur.findById", query = "SELECT u FROM Utilisateur u WHERE u.id = :id"),
    @NamedQuery(name = "Utilisateur.findByEmail", query = "SELECT u FROM Utilisateur u WHERE u.email = :email"),
    @NamedQuery(name = "Utilisateur.findByMotDePasse", query = "SELECT u FROM Utilisateur u WHERE u.motDePasse = :motDePasse"),
    @NamedQuery(name = "Utilisateur.findByTypeUtilisateur", query = "SELECT u FROM Utilisateur u WHERE u.typeUtilisateur = :typeUtilisateur")})
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "mot_de_passe")
    private String motDePasse;
    @Basic(optional = false)
    @Column(name = "type_utilisateur")
    private int typeUtilisateur;
    @JoinColumn(name = "candidat_id", referencedColumnName = "id")
    @OneToOne
    private Candidat candidatId;
    @JoinColumn(name = "societe_id", referencedColumnName = "id")
    @OneToOne
    private Societe societeId;

    public Utilisateur() {
    }

    public Utilisateur(Integer id) {
        this.id = id;
    }

    public Utilisateur(Integer id, String email, String motDePasse, int typeUtilisateur) {
        this.id = id;
        this.email = email;
        this.motDePasse = motDePasse;
        this.typeUtilisateur = typeUtilisateur;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public void setTypeUtilisateur(int typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public Candidat getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Candidat candidatId) {
        this.candidatId = candidatId;
    }

    public Societe getSocieteId() {
        return societeId;
    }

    public void setSocieteId(Societe societeId) {
        this.societeId = societeId;
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
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.entity.Utilisateur[ id=" + id + " ]";
    }
    
}
