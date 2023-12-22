/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package users;


/*
 * Interface que representa um utilizador na aplicacao, apenas contendo os seus metodos
 * acessores.
 */


import dataStructures.Iterator;

import messages.MessageData;

import contactNet.exceptions.ContactNotExists;
import contactNet.exceptions.NoContactMessages;
import contactNet.exceptions.NoContacts;

public interface UserData extends Comparable<UserData>{
	
	/**
	 * Devolve a localidade deste utilizador.
	 * @return - localidade deste utilizador.
	 */
	String address();
	
	/**
	 * Devolve a idade deste utilizador.
	 * @return - idade deste utilizador.
	 */
	int age();
	
	/**
	 * Devolve um objeto do tipo Iterator que itera sobre todos os 
	 * contactos(objetos do tipo UserData) deste utilizador.
	 * @return - um Iterator sobre todos os contactos deste utilizador.
	 * @throws NoContacts - se este utilizador nao possuir contactos.
	 */
	Iterator<UserData> contacts() throws NoContacts;
	
	/**
	 * Devolve o numero de grupos(objetos do tipo GroupData) onde que o utilizador
	 * participa.
	 * @return - numero de grupos subscritos por este utilizador.
	 */
	int getNumberOfGroups();
	
	/**
	 * Verifica se utilizador tem contactos(objeto do tipo UserData).
	 * @return - true se este utilizador possuir pelo menos 1 contacto.
	 */
	boolean hasContacts();
	
	/**
	 * Devolve o login deste utilizador.
	 * @return - login deste utilizador.
	 */
	String login();
	
	/**
	 * Devolve um objeto do tipo Iterator que itera sobre todas as 
	 * mensagens(objetos do tipo MessageData) deste utilizador.
	 * Pre: contact != null
	 * @param contact - utilizador que pede a listagem das mensagens deste utilizador.
	 * @return - um Iterator sobre todas as mensagens deste utilizador.
	 * @throws ContactNotExists - se este utilizador e contact nao forem contactos um do outro.
	 * @throws NoContactMessages - se este utilizador nao possuir mensagens registadas/guardadas.
	 */
	Iterator<MessageData> messages(UserData contact) throws ContactNotExists, NoContactMessages;
	
	/**
	 * Devolve o nome deste UserData.
	 * @return - nome deste UserData.
	 */
	String name();
	
	/**
	 * Devolve a profissao deste UserData.
	 * @return - profissao deste UserData.
	 */
	String profession();
	
}
