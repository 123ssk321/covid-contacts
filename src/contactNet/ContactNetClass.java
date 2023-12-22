/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet;


/**
 * Classe de topo que implementa a interface ContactNet, representando a aplicacao, 
 * contendo, por isso, os metodos e variaveis associados as acoes que sao possiveis fazer nesta.
 */


import contactNet.exceptions.ContactExists;
import contactNet.exceptions.ContactNotExists;
import contactNet.exceptions.ContactNotRemoved;
import contactNet.exceptions.GroupExists;
import contactNet.exceptions.GroupNotExists;
import contactNet.exceptions.NoContactMessages;
import contactNet.exceptions.NoContacts;
import contactNet.exceptions.NoGroupMessages;
import contactNet.exceptions.NoParticipants;
import contactNet.exceptions.SubscriptionExists;
import contactNet.exceptions.SubscriptionNotExists;
import contactNet.exceptions.UserExists;
import contactNet.exceptions.UserNotExists;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;

import groups.Group;
import groups.GroupClass;
import groups.GroupData;
import messages.MessageClass;
import messages.MessageData;
import users.UserClass;
import users.UserData;
import users.User;

public class ContactNetClass implements ContactNet {

	/*
	 * Colecao de utilizadores desta aplicacao.
	 * A estrutura de dados escolhida foi a lista duplamente ligada, pois, o numero de utilizadores que 
	 * a aplicacao pode ter e ilimitado, logo escolha de uma estrutura dinamica e nao de uma 
	 * estrutura estatica como o vetor, onde o "custo" temporal de uma operacao de resize iria cobrir
	 * todas as desvantagens que a lista poderia ter em relacao ao vetor.
	 */
	private List<UserData> users;

	/*
	 * Colecao de grupos desta aplicacao.
	 * A estrutura de dados escolhida foi a lista duplamente ligada, pois, o numero de grupos que 
	 * a aplicacao pode ter e ilimitado, logo escolha de uma estrutura dinamica e nao de uma 
	 * estrutura estatica como o vetor, onde o "custo" temporal de uma operacao de resize iria cobrir
	 * todas as desvantagens que a lista poderia ter em relacao ao vetor.
	 */
	private List<GroupData> groups;

	/*
	 * Construtor da classe.
	 * Controi uma coleacao de utilizadores e de grupos.
	 */
	public ContactNetClass() {
		users = new DoublyLinkedList<UserData>();
		groups = new DoublyLinkedList<GroupData>();
	}

	@Override
	public void insertUser(String login, String name, int age, String address, String profession) throws UserExists {
		UserData usr = new UserClass(login, name, age, address, profession);

		if(getUser(login) != null) {
			throw new UserExists();
		}

		users.addFirst(usr);
	}

	@Override
	public UserData showUser(String login) throws UserNotExists {
		UserData usr = getUser(login);

		if(usr == null) {
			throw new UserNotExists();
		}

		return usr;
	}

	@Override
	public void insertContact(String login1, String login2) throws UserNotExists, ContactExists {
		UserData usr1 = getUser(login1);
		UserData usr2 = getUser(login2);

		if(usr1 == null || usr2 == null) {
			throw new UserNotExists();
		}
		if(usr1.login().equals(usr2.login())) {
			throw new ContactExists();
		}

		((User)usr1).addContact(usr2);
		((User)usr2).addContact(usr1);
	}

	@Override
	public void removeContact(String login1, String login2) throws UserNotExists, ContactNotExists, ContactNotRemoved {
		UserData usr1 = getUser(login1);
		UserData usr2 = getUser(login2);

		if(usr1 == null || usr2 == null) {
			throw new UserNotExists();
		}
		if(login1.equals(login2)) {
			throw new ContactNotRemoved(); 
		}

		((User)usr1).removeContact(usr2);
		((User)usr2).removeContact(usr1);
	}

	@Override
	public Iterator<UserData> listContacts(String login) throws UserNotExists, NoContacts {
		UserData usr = getUser(login);

		if(usr == null) {
			throw new UserNotExists();
		}

		return usr.contacts();
	}

	@Override
	public void insertGroup(String name, String description) throws GroupExists {
		GroupData group = new GroupClass(description , name);

		if(getGroup(name) != null) {
			throw new GroupExists();
		}

		groups.addFirst(group);
	}

	@Override
	public GroupData showGroup(String name) throws GroupNotExists {
		GroupData group = getGroup(name);

		if(group == null) {
			throw new GroupNotExists();
		}

		return group;
	}

	@Override
	public void removeGroup(String name) throws GroupNotExists {
		GroupData group = getGroup(name);

		if(group == null) {
			throw new GroupNotExists();
		}

		if(group.hasParticipants()) {
			Iterator<UserData> it = group.participants();
			while(it.hasNext()) {
				((User) it.next()).removeGroup(group);;
			}
		}
		groups.remove(group);
	}

	@Override
	public void subscribeGroup(String login, String name) throws UserNotExists, GroupNotExists, SubscriptionExists {
		UserData usr = getUser(login);
		GroupData group = getGroup(name);

		if(usr == null) {
			throw new UserNotExists();
		}
		if(group == null) {
			throw new GroupNotExists();
		}

		((Group) group).addParticipant(usr);
		((User) usr).addGroup(group);

	}

	@Override
	public void removeSubscription(String login, String name)
			throws UserNotExists, GroupNotExists, SubscriptionNotExists {
		UserData usr = getUser(login);
		GroupData group = getGroup(name);

		if(usr == null) {
			throw new UserNotExists();
		}
		if(group == null) {
			throw new GroupNotExists();
		}

		((Group)group).removeParticipant(usr);
		((User) usr).removeGroup(group);
	}

	@Override
	public Iterator<UserData> listParticipants(String name) throws GroupNotExists, NoParticipants {
		GroupData group = getGroup(name);

		if(group == null) {
			throw new GroupNotExists();
		}

		return group.participants();
	}

	@Override
	public void insertMessage(String login, String title, String text, String url) throws UserNotExists {
		UserData user = getUser(login);

		if(user == null) {
			throw new UserNotExists();
		}

		MessageData msg = new MessageClass(title, text, url);

		((User)user).addMessage(msg);
		((User)user).addMessageToGroups(msg);

		if(user.hasContacts()) {
			Iterator<UserData> iterator = user.contacts();
			while(iterator.hasNext()) {
				UserData usr = iterator.next();
				((User)usr).addMessage(msg);
			}
		}
	}

	@Override
	public Iterator<MessageData> listContactMessages(String login1, String login2)
			throws UserNotExists, ContactNotExists, NoContactMessages {
		UserData usr1 = getUser(login1);
		UserData usr2 = getUser(login2);

		if(usr1 == null || usr2 == null) {
			throw new UserNotExists();
		}

		return usr1.messages(usr2);
	}

	@Override
	public Iterator<MessageData> listGroupMessages(String name, String login)
			throws GroupNotExists, UserNotExists, SubscriptionNotExists, NoGroupMessages {
		UserData usr = getUser(login);
		GroupData group = getGroup(name);

		if(group == null) {
			throw new GroupNotExists();
		}
		if(usr == null) {
			throw new UserNotExists();
		}

		return group.messages(usr);
	}

	/*metodos privados*/

	/**
	 * Procura um utilizador na colecao dado o seu login.
	 * @param login - login do utilizador.
	 * @return - objeto do tipo UserData se o utilizador existir
	 * e null senao existir.
	 */
	private UserData getUser(String login) {
		Iterator<UserData> iterator = users.iterator();

		if(users.isEmpty()) {
			return null;
		}
		while (iterator.hasNext()) {
			UserData usr = iterator.next();
			if(usr.login().equals(login))
				return usr;
		}
		return null;
	}

	/**
	 * Procura um grupo na colecao dado o seu login.
	 * @param name - nome do grupo.
	 * @return - objeto do tipo GroupData se o grupo existir
	 * e null senao existir.
	 */
	private GroupData getGroup(String name) {
		Iterator<GroupData> iterator = groups.iterator();

		if(groups.isEmpty()) {
			return null;
		}
		while (iterator.hasNext()) {
			GroupData grp = iterator.next();
			if(grp.name().equals(name))
				return grp;
		}
		return null;
	}

}
