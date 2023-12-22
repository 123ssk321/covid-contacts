/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package users;


/**
 * Classe que implementa a interface User e UserData, contendo, por isso, todos os metodos
 * e variaveis associados as acoes que um utilizador pode ter, ou seja, implementa todos os
 * metodos(modificadores a acessores) de um utilizador na aplicacao.
 */


import dataStructures.DoublyLinkedList;
import dataStructures.List;
import dataStructures.OrderedSequence;
import dataStructures.OrderedSequenceClass;
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
	 * Indice da proxima posicao livre da colecao de grupos deste utilizador
	 */
	private int groupIndex;
	
	/*
	 * Colecao dos grupos onde este utilizador participa.
	 * A estrutura de dados escolhida para esta colecao foi o vetor, dado que, o numero de grupos
	 * em que o utilizador participa e limitado (10 grupos), nao necessitando, assim de uma estrutura
	 * de dados dinamica(lista ligada) nem de redimensionar o vetor, o que permite, a adicao de um 
	 * novo grupo com complexidade temporal O(1). Assim o vetor tem 2 vantagens e a lista duplamente
	 * ligada apenas 1 que e a remocao de um grupo.
	 */
	private GroupData[] groups;
	
	/*
	 * Colecao de mensagens deste utilizador.
	 * A estrutura de dados escolhida foi a lista duplamente ligada, pois, o numero de mensagens que 
	 * um utilizador pode ter e ilimitado, logo escolha de uma estrutura dinamica e nao de uma 
	 * estrutura estatica como o vetor, onde o "custo" temporal de uma operacao de resize iria cobrir
	 * todas as desvantagens que a lista poderia ter em relacao ao vetor.
	 */
	private List<MessageData> messages;
	
	/*
	 * Colecao de contactos deste utilizador.
	 * A estrutura de dados escolhida foi a sequencia ordenada, dado que, permite nao estar a fazer uma operacao de
	 * sort cada vez que e feito um pedido de listagem dos contactos deste utilizador, uma vez que, a complexidade 
	 * temporal de uma operacao de sort e alta e se existirem muitos pedidos de listagem entao uma colecao nao ordenada
	 * torna se desvantajosa. 
	 */
	private OrderedSequence<UserData> contacts;	

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

		contacts = new OrderedSequenceClass<UserData>();
		messages = new DoublyLinkedList<MessageData>();
		groups = new GroupClass[MAX_GROUPS];
		groupIndex = 0;
	}


	@Override
	public void addContact(UserData contact) throws ContactExists {
		if(contacts.contains(contact)) {
			throw new ContactExists();
		}
		
		contacts.insert(contact);
	}

	@Override
	public void addGroup(GroupData group) throws SubscriptionExists {
		if(findGroup(group) != -1) {
			throw new SubscriptionExists();
		}
		
		groups[groupIndex++] = group;
	}

	@Override
	public void addMessage(MessageData message) {
		messages.addFirst(message);
	}

	@Override
	public void addMessageToGroups(MessageData message) {
		for(int i = 0; i < groupIndex; i++) {
			((Group) groups[i]).addMessage(message);
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
		
		return contacts.iterator();
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
		return groupIndex;
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
	public Iterator<MessageData> messages(UserData contact) throws ContactNotExists, NoContactMessages{
		if(!contacts.contains(contact) && !(login.equals(contact.login()))) {
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
		boolean removed = contacts.remove(contact);
		
		if(!removed) {
			throw new ContactNotExists();
		}
	}

	@Override
	public void removeGroup(GroupData group) throws GroupNotExists {
		int index = findGroup(group);
		
		if(index == -1) {
			throw new GroupNotExists();
		}
		
		for(int i = index; i < groupIndex-1; i++) {
			groups[i] = groups[i+1];
		}
		groupIndex--;
	}

	/**
	 * Devolve a posicao da primeira ocurrencia de group na colecao de grupos(GroupData)
	 * deste utilizador, se a colecao contiver group, senao devolve -1. 
	 * @param group - group a ser procurado na colecao de grupos.
	 * @return - posicao da primeira ocurrencia de group na colecao ou -1.
	 */
	private int findGroup(GroupData group) {
		for(int i = 0 ; i < groupIndex; i++) {
			if(groups[i].name().equals(group.name()))
				return i;
		}
		return -1;
	}

}
