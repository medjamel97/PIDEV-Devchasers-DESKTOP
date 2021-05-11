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

    public ObservableList<Conversation> getConversationsByUser(int idUser);

    public ObservableList<Conversation> rechercheConversationByUserNomPrenom(int idUser, String nomPrenom);

    public Conversation getConversationByUsers(int idUserExpediteur, int idUserDestinataire);

    public int recupererMessagesNonLus(int idConversation);

    public void markAllAsRead(int idConversation);

    public void ajouterConversation(Conversation c);

    public void modifierDateConversation(Conversation c);

    public void supprimerConversation(Conversation c);

}
