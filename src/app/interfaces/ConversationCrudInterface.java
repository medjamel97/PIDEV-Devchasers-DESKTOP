/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Conversation;
import javafx.collections.ObservableList;

/**
 *
 * @author Grim
 */
public interface ConversationCrudInterface {

    public ObservableList<Conversation> getConversationsByCandidat(int idCandidat);

    public ObservableList<Conversation> rechercheConversationByCandidatNomPrenom(int idCandidat, String nomPrenom);

    public Conversation getConversationByCandidats(int idCandidatExpediteur, int idCandidatDestinataire);
    
    public int recupererMessagesNonLus(int idConversation);
    
    public void markAllAsRead(int idConversation);

    public void ajouterConversation(Conversation c);

    public void modifierDateConversation(Conversation c);

    public void supprimerConversation(Conversation c);

}
