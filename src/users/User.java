/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package users;


/*
 * Interface que representa um utilizador na aplicacao, apenas contendo os seus metodos
 * modificadores.
 */


import groups.GroupData;
import messages.MessageData;

import contactNet.exceptions.ContactExists;
import contactNet.exceptions.ContactNotExists;
import contactNet.exceptions.GroupNotExists;
import contactNet.exceptions.SubscriptionExists;

public interface User {

	/**
	 * Adiciona um outro utilizador(objeto do tipo UserData) a uma colecao de contactos
	 * deste utilizador.
	 * Pre: contact != null
	 * @param contact - utilizador a ser adicionado a colecao de contactos.
	 * @throws ContactExists - se este UserData e contact ja forem contacto um do outro.
	 */
	void addContact(UserData contact) throws ContactExists;
	
	/**
	 * Adiciona um grupo(objeto do tipo GroupData) a uma colecao de grupos deste utilizador, ou
	 * seja, aos grupos onde este utilizador subscreveu.
	 * Pre: group != null
	 * @param group - grupo a ser adicionado a colecao de grupos.
	 * @throws SubscriptionExists - se este utilizador ja e participante de group.
	 */
	void addGroup(GroupData group) throws SubscriptionExists;
	
	/**
	 * Adiciona uma mensagem(objeto do tipo MessageData) a uma colecao de mensagens deste utilizador.
	 * Pre: message != null
	 * @param message - mensagem a ser adicionada a colecao de mensagens.
	 */
	void addMessage(MessageData message);
	
	
	/**
	 * Adiciona uma mensagem(objeto do tipo MessageData) a todos os grupos onde este utilizador
	 * e participante.
	 * Pre: message != null
	 * @param message - mensagem a ser adicionada a todos os grupos subscritos.
	 */
	void addMessageToGroups(MessageData message);
	
	/**
	 * Envia uma mensagem(objeto do tipo MessageData) a todos os contactos deste utilizador.
	 * Pre: message != null
	 * @param message - mensagem a ser adicionada a todos os grupos subscritos.
	 */
	void sendMessageToAllContacts(MessageData message);
	
	/**
	 * Remove um contacto da colecao de contactos deste utilizador.
	 * Pre: contact != null.
	 * @param contact - contacto a ser removido da colecao de contactos.
	 * @throws ContactNotExists - se este utilizador e contact nao forem contactos um do outro.
	 */
	void removeContact(UserData contact) throws ContactNotExists;
	
	/**
	 * Remove um grupo da colecao de grupos deste utilizador, ou seja, este utilizador deixa de ser
	 * participante do grupo.
	 * Pre: group != null.
	 * @param group - grupo a ser cancelada a subscricao.
	 * @throws GroupNotExists - se group nao existir, ou seja, se este utilizador nao for participante
	 * de group.
	 */
	void removeGroup(GroupData group) throws GroupNotExists;
	
}
