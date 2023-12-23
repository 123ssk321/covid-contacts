/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package users;


/**
 * Classe que implementa a interface User e UserData, contendo, por isso, todos os metodos
 * e variaveis associados as acoes que um utilizador pode ter, ou seja, implementa todos os
 * metodos(modificadores a acessores) de um utilizador na aplicacao.
 */


import dataStructures.AVLTree;
import dataStructures.BinarySearchTree;
import dataStructures.ChainedHashTable;
import dataStructures.Dictionary;

/**
 * Classe que implementa a interface User e UserData, contendo, por isso, todos os metodos
 * e variaveis associados as acoes que um utilizador pode ter, ou seja, implementa todos os
 * metodos(modificadores a acessores) de um utilizador na aplicacao.
 */


import dataStructures.List;
import dataStructures.SinglyLinkedList;
import dataStructures.Iterator;

import groups.*;
import messages.MessageData;

import contactNet.exceptions.ContactExists;
import contactNet.exceptions.ContactNotExists;
import contactNet.exceptions.GroupNotExists;
import contactNet.exceptions.NoContactMessages;
import contactNet.exceptions.NoContacts;
import contactNet.exceptions.SubscriptionExists;

public class UserClass implements User, UserData {

	/*
	 * Tamanho maximo  da colecao de grupos deste utilizador.
	 */
	private static final int MAX_GROUPS = 10;


	/*
	 * Idade deste utilizador.
	 */
	private int age;

	/*
	 * Colecao dos grupos onde este utilizador participa.
	 * A estrutura de dados escolhida foi a tabela de dispersao aberta pois permite a insercao, a remocao
	 * e a pesquisa e o acesso a elementos com uma complexidade temporal perto de O(1) quando o fator de 
	 * ocupacao da tabela e menor que 1. 
	 */
	private Dictionary<String,GroupData> groups;

	/*
	 * Colecao de mensagens deste utilizador.
	 * A estrutura de dados escolhida foi a lista simplesmente ligada, pois, o numero de mensagens que 
	 * um utilizador pode ter e ilimitado, logo escolha de uma estrutura dinamica e nao de uma 
	 * estrutura estatica como o vetor, onde o "custo" temporal de uma operacao de resize iria cobrir
	 * todas as desvantagens que a lista poderia ter em relacao ao vetor. Como no caso desta aplicacao
	 * as mensagens de um Group apenas sao iteradas por ordem de insercao, ou seja, nao existem pesquisas, foi 
	 * escolhida lista simplesmente ligada que preserva a ordem de insercao dos elementos. A lista simplesmente
	 * ligada foi escolhida em vez da lista duplamente ligada pois a adicao de uma mensagem e sempre no inicio da
	 * lista nao se utilizando as vantegens da lista duplamente ligada, gastando assim, menos memoria 
	 */
	private List<MessageData> messages;

	/*
	 * Colecao de contactos deste utilizador.
	 * A estrutura de dados escolhida foi a arvore AVL pois permite a insercao, a remocao
	 * e a pesquisa e o acesso a elementos com uma complexidade temporal O(log n) (onde n e o numero de
	 * elementos da arvore) e preserva a relacao de ordem das chaves permitindo itera-las ordenadamente.
	 */
	private Dictionary<String, UserData> contacts;	

	/*
	 * Localidade deste utilizador.
	 */
	private String address;

	/*
	 * Login deste utilizador.
	 */
	private String login;

	/*
	 * Nome deste utilizador.
	 */
	private String name;

	/*
	 * Profissao deste utilizador.
	 */
	private String profession;

	/**
	 * Construtor da classe.
	 * Constroi um utilizador inicialisando as variaveis de instancia com os parametros
	 * de mesma designacao, assim como as colecoes deste utilizador.
	 * @param login - login do utilizador.
	 * @param name - nome do utilizador.
	 * @param age - idade do utilizador.
	 * @param address - localidade do utilizador.
	 * @param profession - profissao do utilizador.
	 */
	public UserClass(String login, String name, int age, String address, String profession) {
		this.login = login;
		this.name = name;
		this.age = age;
		this.address = address;
		this.profession = profession;

		messages = new SinglyLinkedList<MessageData>();
		contacts = new AVLTree<String, UserData>();
		groups = new ChainedHashTable<String, GroupData>(MAX_GROUPS);
	}


	@Override
	public void addContact(UserData contact) throws ContactExists {
		UserData usr = contacts.find(contact.login());

		if(usr != null) {
			throw new ContactExists();
		}

		contacts.insert(contact.login(), contact);
	}

	@Override
	public void addGroup(GroupData group) throws SubscriptionExists {
		String name = group.name();
		if(groups.find(name)!=null) {
			throw new SubscriptionExists();
		}

		groups.insert(name, group);
	}

	@Override
	public void addMessage(MessageData message) {
		messages.addFirst(message);
	}

	@Override
	public void addMessageToGroups(MessageData message) {	
		Iterator<GroupData> it = ((ChainedHashTable<String, GroupData>)groups).values();
		while(it.hasNext()) {
			GroupData group = it.next();
			((Group)group).addMessage(message);
		}

	}

	@Override
	public void sendMessageToAllContacts(MessageData message) {
		if(hasContacts()) {
			Iterator<UserData> it = ((BinarySearchTree<String, UserData>) contacts).values();
			while(it.hasNext()) {
				UserData usr = it.next();
				((User)usr).addMessage(message);
			}
		}
	}

	@Override
	public String address() {
		return address;
	}

	@Override
	public int age() {
		return age;
	}

	@Override
	public int compareTo(UserData usr) {
		return login.compareTo(usr.login());
	}

	@Override
	public Iterator<UserData> contacts() throws NoContacts {
		if(contacts.isEmpty()) {
			throw new NoContacts();
		}

		return ((BinarySearchTree<String, UserData>) contacts).values();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserClass)) {
			return false;
		}
		UserClass other = (UserClass) obj;
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		return true;
	}

	@Override
	public int getNumberOfGroups() {
		return groups.size();
	}

	@Override
	public boolean hasContacts() {
		return !contacts.isEmpty();
	}

	@Override
	public String login() {
		return login;
	}

	@Override
	public Iterator<MessageData> messages(UserData contact) throws ContactNotExists, NoContactMessages {
		UserData usr = contacts.find(contact.login());

		if((usr == null) && !(login.equals(contact.login()))) {
			throw new ContactNotExists();
		}
		if(messages.isEmpty()) {
			throw new NoContactMessages();
		}

		return messages.iterator();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String profession() {
		return profession;
	}

	@Override
	public void removeContact(UserData contact) throws ContactNotExists {
		UserData removed = contacts.remove(contact.login());

		if(removed == null) {
			throw new ContactNotExists();
		}
	}

	@Override
	public void removeGroup(GroupData group) throws GroupNotExists {
		GroupData removed = groups.remove(group.name());

		if(removed == null) 
			throw new GroupNotExists();
	}

}
