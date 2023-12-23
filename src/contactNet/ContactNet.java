/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet;

/**
 * Interface de topo que representa a aplicacao gerindo todas as 
 * acoes associadas a esta.
 */

import dataStructures.Iterator;
import groups.GroupData;
import messages.MessageData;
import users.UserData;
import contactNet.exceptions.*;

public interface ContactNet {

	/**
	 * Regista um novo utilizador no sistema.
	 * @param login - login do utilizador.
	 * @param name - nome do utilizador.
	 * @param age - idade do utilizador.
	 * @param address - morada do utilizador.
	 * @param profession - profissao do utilizador.
	 * @throws UserExists - se ja existir um utilizador com este login.
	 */
	void insertUser(String login, String name, int age, String address, String profession) throws UserExists;
	
	/**
	 * Consulta os dados de um dado utilizador dado um login.
	 * @param login - login do utilizador.
	 * @return - o utilizador cujo os dados vao ser mostrados.
	 * @throws UserNotExists - se nao existir um utilizador com este login.
	 */
	UserData showUser(String login) throws UserNotExists;
	
	/**
	 * Regista contacto entre dois utilizadores dado o login de cada um deles.
	 * @param login1 - login do primeiro utilizador.
	 * @param login2 - login do segundo utilizador.
	 * @throws UserNotExists - se nao existir um utilizador com este login.
	 * @throws ContactExists - se estes utilizadores ja forem contactos.
	 */
	void insertContact(String login1, String login2) throws UserNotExists, ContactExists;
	
	/**
	 * Remove contacto entre dois utilizadores dado o login de cada um deles.
	 * @param login1 - login do primeiro utilizador.
	 * @param login2 - login do segundo utilizador.
	 * @throws UserNotExists - se nao existir um utilizador com este login.
	 * @throws ContactNotExists - se estes utilizadores nao forem contactos.
	 * @throws ContactNotRemoved - se os logins inseridos forem iguais.
	 */
	void removeContact(String login1, String login2) throws UserNotExists, ContactNotExists, ContactNotRemoved;
	
	/**
	 * Lista os contactos de um utilizador dado o seu login.
	 * @param login - login do utilizador.
	 * @return - um iterador(objeto do tipo Iterator) sobre todos os contactos do utilizador.
	 * @throws UserNotExists - se nao existir um utilizador com este login.
	 * @throws NoContacts - se o utilizador nao tiver contactos.
	 */
	Iterator<UserData> listContacts(String login) throws UserNotExists, NoContacts;
	
	/**
	 * Regista um novo grupo no sistema.
	 * @param group - nome do grupo.
	 * @param description - descricao do grupo.
	 * @throws GroupExists - se ja existir um grupo com este nome.
	 */
	void insertGroup(String group, String description) throws GroupExists;
	
	/**
	 * Consulta os dados de um grupo dado o seu nome. 
	 * @param group - nome do grupo.
	 * @return - o grupo.
	 * @throws GroupNotExists - se nao existir um grupo com este nome.
	 */
	GroupData showGroup(String group) throws GroupNotExists;
	
	/**
	 * Remove um grupo do sistema dado o seu nome.
	 * @param group - nome do grupo.
	 * @throws GroupNotExists - se nao existir um grupo com este nome.
	 */
	void removeGroup(String group) throws GroupNotExists;
	
	/**
	 * Insere um participante num grupo dado o login do utilizador que quer participar no grupo
	 * e o nome do grupo onde quer participar.
	 * @param login - login do utilizador.
	 * @param group - nome do grupo.
	 * @throws UserNotExists - se nao existir um utilizador com este login.
	 * @throws GroupNotExists - se nao existir um grupo com este nome.
	 * @throws SubscriptionExists - se o utilizador ja for participante do grupo.
	 */
	void subscribeGroup(String login, String group) throws UserNotExists, GroupNotExists, SubscriptionExists;
	
	/**
	 * Remove um participante de um grupo dado o login do participante e o nome do grupo.
	 * @param login - login do utilizador.
	 * @param group - nome do grupo.
	 * @throws UserNotExists - se nao existir um utilizador com este login.
	 * @throws GroupNotExists - se nao existir um grupo com este nome.
	 * @throws SubscriptionNotExists - se o utilizador nao for participante do grupo.
	 */
	void removeSubscription(String login, String group) throws UserNotExists, GroupNotExists, SubscriptionNotExists;
	
	/**
	 * Lista os participantes de um grupo dado o nome do grupo. 
	 * @param group - nome do grupo.
	 * @return - um iterador(objeto do tipo Iterator) sobre todos participantes do grupo.
	 * @throws GroupNotExists - se nao existir um grupo com este nome.
	 * @throws NoParticipants - se o grupo nao tiver participantes.
	 */
	Iterator<UserData> listParticipants(String group) throws GroupNotExists, NoParticipants; 	

	/**
	 * Regista mensagem associada a um dado utilizador.
	 * @param login - login do utilizador.
	 * @param title - titulo da mensagem.
	 * @param text - texto da mensagem.
	 * @param url - url da mensagem.
	 * @throws UserNotExists - se nao existir um utilizador com este login.
	 */
	void insertMessage(String login, String title, String text, String url) throws UserNotExists;
	
	/**
	 * Lista as mensagem de um contacto dado o login do utilizador cujo as mensagens vao ser listadas
	 * e o login do utilizador que pede a listagem das mensagens.
	 * @param login1 - login do utilizador cujas mensagems vao ser listadas.
	 * @param login2 - login do contacto do primeiro utilizador que pede a listagem das mensagens.
	 * @return - um iterador(objeto do tipo Iterator) sobre todas as mensagens do primeiro utilizador.
	 * @throws UserNotExists - se nao existir utilizadores com estes logins.
	 * @throws ContactNotExists - se os utilizadores nao forem contactos.
.	 * @throws NoContactMessages - se o primeiro utilizador nao tiver inserido mensagens
	 */
	Iterator<MessageData> listContactMessages(String login1, String login2) throws UserNotExists, ContactNotExists,
		NoContactMessages; 
	
	/**
	 * Lista as mensagem de um grupo dado o nome do grupo e o login do participante do grupo e
	 * pede a listagem de todas as mensagens do grupo onde participa. 
	 * @param group - nome do grupo.
	 * @param login - login do utilizador.
	 * @return - um iterador(objeto do tipo Iterator) sobre todas as mensagens do grupo.
	 * @throws GroupNotExists - se nao existir um grupo com este nome.
	 * @throws UserNotExists - se nao existir um utilizador com este login.
	 * @throws SubscriptionNotExists - se o utilizador nao for participante do grupo.
	 * @throws NoGroupMessages - se o grupo nao tiver mensagens.
	 */
	Iterator<MessageData> listGroupMessages(String group, String login) throws GroupNotExists, UserNotExists, 
		SubscriptionNotExists, NoGroupMessages; 
	
}
