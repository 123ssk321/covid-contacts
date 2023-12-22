/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package groups;


/*
 * Interface que representa um grupo de utilizadores(UserData) na aplicacao, apenas 
 * contendo os seus metodos acessores.
 */


import dataStructures.Iterator;

import messages.MessageData;
import users.UserData;

import contactNet.exceptions.NoGroupMessages;
import contactNet.exceptions.NoParticipants;
import contactNet.exceptions.SubscriptionNotExists;

public interface GroupData {

	/**
	 * Devolve a descricao deste grupo.
	 * @return - descricacao deste grupo.
	 */
	String description();
	
	/**
	 * Verifica se este grupo contem participantes(objetos do tipo UserData).
	 * @return - true se este grupo contiver pelo menos 1 participante.
	 */
	boolean hasParticipants();
	
	/**
	 * Devolve um objeto do tipo Iterator que itera sobre todas as 
	 * mensagens(objetos do tipo MessageData) deste grupo.
	 * Pre: participant != null
	 * @param participant - participante deste grupo que pede a listagem de todas as mensagens 
	 * do grupo.
	 * @return - um Iterator sobre todas as mensagens deste grupo.
	 * @throws SubscriptionNotExists - se participant nao for um participante deste grupo, ou seja,
	 * se nao contiver uma subscricao neste grupo.
	 * @throws NoGroupMessages - se este grupo nao possuir mensagens registadas/guardadas.
	 */
	Iterator<MessageData> messages(UserData participant) throws SubscriptionNotExists, NoGroupMessages;
	
	/**
	 * Devolve a nome deste grupo.
	 * @return - nome deste grupo
	 */
	String name();
	
	/**
	 * Devolve um objeto do tipo Iterator que itera sobre todos os 
	 * participantes(objetos do tipo UserData) deste grupo.
	 * @return - um Iterator sobre todos os participantes deste grupo.
	 * @throws NoParticipants - se nao existirem participantes neste grupo.
	 */
	Iterator<UserData> participants() throws NoParticipants;

}
