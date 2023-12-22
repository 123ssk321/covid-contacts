/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 * A implementacao desta lista foi feita usando a head e a tail como dummy nodes, ou seja,
 * nos que quando a lista esta vazia apontam um para outro e cujo elemento apontado e null
 * Os metodos privados facultados e que nao necessarios para esta implementacao foram colocados como comentario. 
 */

package dataStructures;

import contactNet.exceptions.InvalidPositionException;
import contactNet.exceptions.NoElementException;


/**
 * Doubly linked list Implementation 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */

public class DoublyLinkedList<E> implements List<E>  {

	/**
	 * Serial Version UID of the Class
	 */
	static final long serialVersionUID = 0L;

	static class DListNode<E>{
		// Element stored in the node.
		private E element;
		// (Pointer to) the next node.
		private DListNode<E> next;
		// (Pointer to) the previous node.
		private DListNode<E> previous;


		public DListNode(E elem, DListNode<E> thePrev, DListNode<E> theNext){
			element = elem;
			previous = thePrev;
			next = theNext;
		}


		public DListNode(E theElement) {
			this(theElement, null, null);
		}

		public E getElement() {
			return element;
		}

		public DListNode<E> getNext() {
			return next;
		}

		public DListNode<E> getPrevious() {
			return previous;
		}

		public void setElement(E newElement) {
			element = newElement;
		}

		public void setNext(DListNode<E> newNext) {
			next = newNext;
		}

		public void setPrevious(DListNode<E> newPrevious) {
			previous = newPrevious;
		}

	}


	// Node at the head of the list.
	private DListNode<E> head;

	// Node at the tail of the list.
	private DListNode<E> tail;

	// Number of elements in the list.
	private int currentSize;


	public DoublyLinkedList() {
		head = new DListNode<E>(null);
		tail = new DListNode<E>(null);
		
		head.setNext(tail);
		tail.setPrevious(head);
		
		currentSize = 0;
	}


	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public int find(E element) {		
		DListNode<E> node = head.getNext();

		for(int i = 0; i < currentSize; i++) {
			if(node.getElement().equals(element)) {
				return i;
			}
			node = node.getNext();
		}
		return -1;
	}

	@Override
	public E getFirst() throws NoElementException {
		if(isEmpty()) {
			throw new NoElementException();
		}

		return head.getNext().getElement();
	}

	@Override
	public E getLast() throws NoElementException {
		if(isEmpty()) {
			throw new NoElementException();
		}

		return tail.getPrevious().getElement();
	}

	@Override
	public E get(int position) throws InvalidPositionException {
		if (position < 0 || position >= currentSize) {
			throw new InvalidPositionException();
		}

		return getNode(position).getElement();
	}

	/**
	 * Retorna o no da posicao especificado por position nesta lista.
	 * @param position - posicao do no a ser retornado.
	 * @return - no em position
	 */
	private DListNode<E> getNode(int position){
		if(position == currentSize) {
			return tail;
		}
				
		DListNode<E> node = null;

		if(((currentSize - 1) - position) >= position) {
			node = head.getNext();
			for(int i = 0; i < position; i++) {
				node = node.getNext();
			}
		} else {
			node = tail.getPrevious();
			for(int i = currentSize - 1; i > position; i--) {
				node = node.getPrevious();
			}
		}
		return node;
	}

	@Override
	public void addFirst(E element) {
		addMiddle(0, element);
	}

	@Override
	public void addLast(E element) {
		addMiddle(currentSize, element);
	}

	protected void addMiddleNode(DListNode<E> auxNode, E element) {
		DListNode<E> newNode = new DListNode<E>(element, auxNode.getPrevious(), auxNode);

		auxNode.getPrevious().setNext(newNode);
		auxNode.setPrevious(newNode);
		currentSize++;
	}
	
	/**
	 * Adiciona element na posicao especificada por position nesta lista.
	 * @param position - posicao onde vai ser adicionado um element.
	 * @param element - elemento a ser adicionado nesta lista.
	 */
	private void addMiddle(int position, E element) {
		DListNode<E> auxNode = getNode(position);

		this.addMiddleNode(auxNode, element);
	}

	@Override
	public void add(int position, E element) throws InvalidPositionException {
		if (position < 0 || position > currentSize) { 
			throw new InvalidPositionException();
		}
		
		addMiddle(position, element);
	}

	/**
	 * Removes the first node in the list.
	 * Pre-condition: the list is not empty.
	 */
	/*private void removeFirstNode() {
		if(currentSize == 1) {
			head.setNext(tail);;
			tail.setPrevious(head);
		} else {
			DListNode<E> node = head.getNext().getNext();
			head.setNext(node);
			node.setPrevious(head);
		}
	}*/

	@Override
	public E removeFirst() throws NoElementException {
		return removeMiddle(0);
	}

	/**
	 * Removes the last node in the list.
	 * Pre-condition: the list is not empty.
	 */
	/*private void removeLastNode(){
		if(currentSize == 1) {
			head.setNext(tail);
			tail.setPrevious(head);
		} else {
			DListNode<E> node = tail.getPrevious().getPrevious();
			node.setNext(tail);
			tail.setPrevious(node);
		}
	}*/

	@Override 
	public E removeLast() throws NoElementException {
		return removeMiddle(currentSize-1);
	}

	/**
	 * Remove o no especificado por node desta lista.
	 * @param node - no a ser removido.
	 */
	private void removeMiddleNode(DListNode<E> node) {
		DListNode<E> nextNode = node.getNext();
		DListNode<E> previousNode = node.getPrevious();

		nextNode.setPrevious(previousNode);
		previousNode.setNext(nextNode);
		currentSize--;
	}

	/**
	 * Remove o objeto desta lista que esta na posicao especificada
	 * por position.
	 * @param position - posicao do elemento a ser removido.
	 * @return - elemento que foi removido desta lista.
	 */
	private E removeMiddle(int position) {
		DListNode<E> nodeToRemove = this.getNode(position);

		this.removeMiddleNode(nodeToRemove);
		return nodeToRemove.getElement();
	}

	@Override
	public E remove(int position) throws InvalidPositionException {
		if(position < 0 || position >= currentSize) {
			throw new InvalidPositionException();
		}

		return removeMiddle(position);
	}

	@Override
	public boolean remove(E element) {
		DListNode<E> node = head.getNext();

		for(int i = 0; i < currentSize; i++) {
			if(node.getElement().equals(element)) {
				removeMiddleNode(node);
				return true;
			}
			node = node.getNext();
		}
		return false;
	}

	/*private DListNode<E> findNode(E element) {
		DListNode<E> node = head.getNext();

		for(int i = 0; i < currentSize; i++) {
			if(node.getElement().equals(element)) {
				return node;
			}
			node = node.getNext();
		}
		return null;
	}*/

	@Override
	public Iterator<E> iterator() {
		return new DoublyLLIterator<E>(head.getNext(), tail.getPrevious());
	}

	/**
	 * Removes all of the elements from the specified list and
	 * inserts them at the end of the list (in proper sequence).
	 * @param list - list to be appended to the end of this
	 */
	public void append(DoublyLinkedList<E> list) {
		if(list.isEmpty()) {
			return;
		}
		
		DListNode<E> otherRealHead = list.head.getNext();
		DListNode<E> otherRealTail = list.tail.getPrevious();
		
		if(this.isEmpty()) {
			this.head.setNext(otherRealHead);
			otherRealHead.setPrevious(this.head);
			this.tail.setPrevious(otherRealTail);
			otherRealTail.setNext(this.tail);
						
			this.currentSize = list.currentSize;
		} else {
			DListNode<E> thisRealTail = tail.getPrevious();
			
			thisRealTail.setNext(otherRealHead);
			otherRealHead.setPrevious(thisRealTail);
			tail.setPrevious(otherRealTail);
			otherRealTail.setNext(tail);
			
			this.currentSize += list.currentSize;
		}		
		
		list.head.setNext(list.tail);
		list.tail.setPrevious(list.head);
		list.currentSize = 0;
	}

}
