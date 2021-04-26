/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.interfaces;

import app.entity.Message;
import javafx.collections.ObservableList;

/**
 *
 * @author Grim
 */
public interface MessageCrudInterface {

    public ObservableList<Message> getMessagesByConversation(int idConversation, int limit);

    public void ajouterMessage(Message m);

    public void modifierMessage(Message m);

    public void supprimerMessage(Message m);

    public Message getDernierMessage(int idConversation);
}
