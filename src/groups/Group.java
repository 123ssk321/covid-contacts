/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package groups;


/*
 * Interface que representa um grupo de utilizadores(UserData) na aplicacao, apenas 
 * contendo os seus metodos modificadores.
 */


import messages.MessageData;
import users.UserData;

import contactNet.exceptions.SubscriptionExists;
import contactNet.exceptions.SubscriptionNotExists;

public interface Group {

	/**
	 * Adiciona uma mensagem(objeto do tipo MessageData) a uma colecao de mensagens deste grupo.
	 * Pre: message != null
	 * @param message - mensagem a ser adicionada a colecao de mensagens.
	 */
	void addMessage(MessageData message);
	
	/**
	 * Adiciona um utilizador(objeto do tipo UserData) a uma colecao de participantes deste grupo.
	 * Pre: participant != null
	 * @param participant - utilizador a ser adicionado a colecao de participantes.
	 * @throws SubscriptionExists - se participant ja for um participante neste grupo.
	 */
	void addParticipant(UserData participant) throws SubscriptionExists;
	
	/**
	 * Remove um participante da colecao de participantes deste grupo.
	 * @param participant - participante a ser removido.
	 * @throws SubscriptionNotExists - se participant nao for um participante neste grupo, ou seja, nao possuir
	 * uma subscricao neste grupo.
	 */
	void removeParticipant(UserData participant) throws SubscriptionNotExists;
	
	/**
	 * Remove todos os participante da colecao de participantes deste grupo.
	 */
	void removeAllParticipants();

}
