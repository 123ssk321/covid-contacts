/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a interface Iterator<E> permitindo iterar sobre uma lista simplesmente ligada.
 */


import contactNet.exceptions.NoSuchElementException;
import dataStructures.SinglyLinkedList.SListNode;

public class SinglyLLIterator<E> implements Iterator<E> {
	
	/**
	 * Serial Version UID of the Class
	 */
	static final long serialVersionUID = 0L;

	/** 
	 * Node with the first element in the iteration.
	 */
	private SListNode<E> firstNode;

	/**
	 * Node with the next element in the iteration.
	 */
	private SListNode<E> nextToReturn;


	/**
	 * Construtor da classe.
	 * Inicialisa as variaveis e coloca o nextToReturn a apontar para o proximo elemento
	 * a devolver no next.
	 * @param first - head da lista simplesmente ligada que vai ser iterada.
	 */
	public SinglyLLIterator(SListNode<E> first) {
		firstNode = first;
		nextToReturn = first;
		this.rewind();
	}  

	@Override
	public boolean hasNext() {
		return nextToReturn != null;
	}

	@Override
	public E next() throws NoSuchElementException {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}

		E elm = nextToReturn.getElement();
		nextToReturn = nextToReturn.getNext();
		return elm;
	}

	@Override
	public void rewind() {
		nextToReturn = firstNode;
	}

}
