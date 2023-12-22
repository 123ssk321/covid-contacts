/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package groups;


/**
 * Classe que implementa a interface Group e GroupData, contendo, por isso, todos os metodos
 * e variaveis associados as acoes que um grupo pode ter, ou seja, implementa todos os
 * metodos(modificadores a acessores) de um grupo na aplicacao.
 */


import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;
import dataStructures.OrderedSequence;
import dataStructures.OrderedSequenceClass;

import messages.MessageData;
import users.UserData;

import contactNet.exceptions.NoGroupMessages;
import contactNet.exceptions.NoParticipants;
import contactNet.exceptions.SubscriptionExists;
import contactNet.exceptions.SubscriptionNotExists;

public class GroupClass implements Group, GroupData {
	
	/*
	 * Colecao de mensagens deste grupo.
	 * A estrutura de dados escolhida foi a lista duplamente ligada, dado que, o numero de mensagens que 
	 * um grupo pode ter e ilimitado, logo escolha de uma estrutura dinamica, e nao de uma 
	 * estrutura estatica como o vetor, onde o "custo" temporal de uma operacao de resize iria cobrir
	 * todas as desvantagens que a lista poderia ter em relacao ao vetor.
	 */
	private List<MessageData> messages;
	
	/*
	 * Colecao de participantes deste grupo.
	 * A estrutura de dados escolhida foi a sequencia ordenada, dado que, permite nao estar a fazer uma operacao de
	 * sort cada vez que e feito um pedido de listagem dos participantes deste grupo, uma vez que, a complexidade 
	 * temporal de uma operacao de sort e alta e se existirem muitos pedidos de listagem entao uma colecao nao ordenada
	 * torna se desvantajosa. 
	 */
	private OrderedSequence<UserData> participants;
	
	/*
	 * Descricao deste grupo.
	 */
	private String description;
	
	/*
	 * Nome deste grupo.
	 */
	private String name;
	
	
	/**
	 * Construtor da classe.
	 * Constroi um grupo inicialisando as variaveis de instancia com os parametros
	 * de mesma designacao, assim como as colecoes deste grupo.
	 * @param description - descricao deste grupo.
	 * @param name - nome deste grupo.
	 */
	public GroupClass(String description, String name) {
		this.description = description;
		this.name = name;
		
		participants = new OrderedSequenceClass<UserData>();
		messages = new DoublyLinkedList<MessageData>();	
	}
	
	
	@Override
	public void addMessage(MessageData message) {
		messages.addFirst(message);
	}

	@Override
	public void addParticipant(UserData participant) throws SubscriptionExists{
		if(hasParticipant(participant)) {
			throw new SubscriptionExists();
		}
		
		participants.insert(participant);
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof GroupClass)) {
			return false;
		}
		GroupClass other = (GroupClass) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}


	@Override
	public boolean hasParticipants() {
		return !participants.isEmpty();
	}
	
	@Override
	public Iterator<MessageData> messages(UserData participant) throws SubscriptionNotExists, NoGroupMessages {
		if(!hasParticipant(participant)) {
			throw new SubscriptionNotExists();
		}
		if(messages.isEmpty()) {
			throw new NoGroupMessages();
		}
		
		return messages.iterator();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Iterator<UserData> participants() throws NoParticipants {
		if(participants.isEmpty()) {
			throw new NoParticipants();
		}
		
		return participants.iterator();
	}

	@Override
	public void removeParticipant(UserData participant) throws SubscriptionNotExists{
		if(!hasParticipant(participant)) {
			throw new SubscriptionNotExists();
		}
		
		participants.remove(participant);
	}

	/**
	 * Verifica se o participante existe na colecao de participantes deste grupo, ou seja, se 
	 * e um subscritor do grupo.
	 * @param participant - participante a ser verificada a subscricao neste grupo.
	 * @return - true se o participante for um subscritor do grupo.
	 */
	private boolean hasParticipant(UserData participant) {
		return participants.contains(participant);
	}
	
}
