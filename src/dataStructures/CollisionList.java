/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a iterface Dictionary<K,V> atraves de uma lista simplesmente
 * ligada e que representa uma lista de colisoes.
 */


import dataStructures.SinglyLinkedList.SListNode;

public class CollisionList<K, V> implements Dictionary<K,V>  {

	/*
	 * Lista de entries desta ColisionList.
	 */
	private List<Entry<K ,V>> entries; 

	/**
	 * Construtor da classe.
	 * Constroi uma nova lista ligada que contem as entries da CollisionList. A lista simplesmente
	 * ligada foi escolhida em vez da lista duplamente ligada pois a adicao de uma entry com um novo
	 * value e sempre no fim da lista nao se utilizando as vantegens da lista duplamente ligada, 
	 * gastando assim, menos memoria
	 */
	public CollisionList() {
		entries = new SinglyLinkedList<Entry<K ,V>>();
	}


	@Override
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	@Override
	public int size() {
		return entries.size();
	}

	@Override
	public V find(K key) {
		SListNode<Entry<K, V>> prevEntryNode = this.getPrevEntryNode(key);
		if(prevEntryNode == null) {
			return null;
		}
		return prevEntryNode.getNext().getElement().getValue();
	}

	@Override
	public V insert(K key, V value) {
		SListNode<Entry<K, V>> prevEntryNode = this.getPrevEntryNode(key);
		Entry<K,V> entry = new EntryClass<K, V> (key, value);

		if(prevEntryNode == null) {
			entries.addLast(entry);
			return null;
		} else {
			SListNode<Entry<K, V>> entryNode = prevEntryNode.getNext();
			V oldValue = entryNode.getElement().getValue();
			entryNode.setElement(entry);
			return oldValue;
		}
	}

	@Override
	public V remove(K key) {
		SListNode<Entry<K, V>> prevEntryNode = this.getPrevEntryNode(key);

		if(prevEntryNode == null) {
			return null;
		} else {
			SListNode<Entry<K, V>> entryNode = prevEntryNode.getNext();
			V oldValue = entryNode.getElement().getValue();
			((SinglyLinkedList<Entry<K, V>>) entries).removeMiddleNode(prevEntryNode,entryNode);
			return oldValue;
		}
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return entries.iterator();
	}

	/**
	 * Metodo que devolve o no que contem a entry com uma dada chave.
	 * @param key - chave da entry.
	 * @return - no cuja entry tenha a chave key.
	 */
	private SListNode<Entry<K, V>> getPrevEntryNode(K key){
		SListNode<Entry<K,V>> prevNode = new SListNode<Entry<K,V>>(null, ((SinglyLinkedList<Entry<K, V>>) entries).head);
		for(int i = -1; i < entries.size()-1; i++) {
			if(prevNode.getNext().getElement().getKey().equals(key)) {
				return prevNode;
			}
			prevNode = prevNode.getNext();
		}
		return null;
	}

}
