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
import dataStructures.ChainedHashTable;
import dataStructures.Dictionary;
import dataStructures.Iterator;

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
	 * A estrutura de dados escolhida foi a tabela de dispersao aberta pois permite a insercao, a remocao
	 * e a pesquisa e o acesso a elementos com uma complexidade temporal perto de O(1) quando o fator de 
	 * ocupacao da tabela e menor que 1, que neste caso, sera sempre devido ao metodo rehash mesmo que este
	 * seja uma operacao custosa em termos de tempo.
	 */
	private Dictionary<String, UserData> users;
	
	/*
	 * Colecao de grupos desta aplicacao.
	 * A estrutura de dados escolhida foi a tabela de dispersao aberta pois permite a insercao, a remocao
	 * e a pesquisa e o acesso a elementos com uma complexidade temporal perto de O(1) quando o fator de 
	 * ocupacao da tabela e menor que 1, que neste caso, sera sempre devido ao metodo rehash mesmo que este
	 * seja uma operacao custosa em termos de tempo. 
	 */
	private Dictionary<String,GroupData> groups;

	/*
	 * Construtor da classe.
	 * Controi uma colecao de utilizadores e de grupos.
	 */
	public ContactNetClass() {
		users = new ChainedHashTable<String, UserData>();
		groups = new ChainedHashTable<String, GroupData>();
	}

	@Override
	public void insertUser(String login, String name, int age, String address, String profession) throws UserExists {
		UserData usr = users.find(login);
		if(usr!= null) {
			throw new UserExists();
		}
		usr = new UserClass(login, name, age, address, profession);
		users.insert(login, usr);
	}

	@Override
	public UserData showUser(String login) throws UserNotExists {
		UserData usr = users.find(login);

		if(usr == null) {
			throw new UserNotExists();
		}

		return usr;
	}

	@Override
	public void insertContact(String login1, String login2) throws UserNotExists, ContactExists {
		UserData usr1 = users.find(login1);
		UserData usr2 = users.find(login2);
		
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
		UserData usr1 = users.find(login1);
		UserData usr2 = users.find(login2);
		
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
		UserData usr = users.find(login);

		if(usr == null) {
			throw new UserNotExists();
		}

		return usr.contacts();
	}

	@Override
	public void insertGroup(String name, String description) throws GroupExists {
		GroupData group = groups.find(name);
		if(group != null) {
			throw new GroupExists();
		}
		group = new GroupClass(description, name);
		groups.insert(name, group);
	}

	@Override
	public GroupData showGroup(String name) throws GroupNotExists {
		GroupData group = groups.find(name);
		
		if(group == null) {
			throw new GroupNotExists();
		}

		return group;
	}

	@Override
	public void removeGroup(String name) throws GroupNotExists {
		GroupData group = groups.find(name);
		
		if(group == null) {
			throw new GroupNotExists();
		}
		
		((Group) group).removeAllParticipants();
		groups.remove(name);
	}

	@Override
	public void subscribeGroup(String login, String name) throws UserNotExists, GroupNotExists, SubscriptionExists {
		UserData usr = users.find(login);
		GroupData group = groups.find(name);

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
		UserData usr = users.find(login);
		GroupData group = groups.find(name);

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
		GroupData group = groups.find(name);

		if(group == null) {
			throw new GroupNotExists();
		}

		return group.participants();
	}

	@Override
	public void insertMessage(String login, String title, String text, String url) throws UserNotExists {
		UserData user = users.find(login);

		if(user == null) {
			throw new UserNotExists();
		}
		
		MessageData msg = new MessageClass(title, text, url);

		((User)user).addMessage(msg);
		((User)user).addMessageToGroups(msg);
		((User)user).sendMessageToAllContacts(msg);
	}

	@Override
	public Iterator<MessageData> listContactMessages(String login1, String login2)
			throws UserNotExists, ContactNotExists, NoContactMessages {
		UserData usr1 = users.find(login1);
		UserData usr2 = users.find(login2);
		
		if(usr1 == null || usr2 == null) {
			throw new UserNotExists();
		}

		return usr1.messages(usr2);
	}

	@Override
	public Iterator<MessageData> listGroupMessages(String name, String login)
			throws GroupNotExists, UserNotExists, SubscriptionNotExists, NoGroupMessages {
		UserData usr = users.find(login);
		GroupData group = groups.find(name);

		if(group == null) {
			throw new GroupNotExists();
		}
		if(usr == null) {
			throw new UserNotExists();
		}

		return group.messages(usr);
	}

}
