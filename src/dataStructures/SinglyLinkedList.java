/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;

import contactNet.exceptions.InvalidPositionException;
import contactNet.exceptions.NoElementException;

/**
 * Singly linked list Implementation 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */

public class SinglyLinkedList<E> implements List<E> {

	/**
	 * Serial Version UID of the Class
	 */
	static final long serialVersionUID = 0L;

	static class SListNode<E>{
		// Element stored in the node.
		private E element;
		// (Pointer to) the next node.
		private SListNode<E> next;

		public SListNode(E elem, SListNode<E> theNext){
			element = elem;
			next = theNext;
		}

		public SListNode(E theElement) {
			this(theElement, null);
		}

		public E getElement() {
			return element;
		}

		public SListNode<E> getNext() {
			return next;
		}

		public void setElement(E newElement) {
			element = newElement;
		}

		public void setNext(SListNode<E> newNext) {
			next = newNext;
		}

	}

	// Node at the head of the list.
	protected SListNode<E> head;

	// Node at the tail of the list.
	private SListNode<E> tail;

	// Number of elements in the list.
	private int currentSize;


	public SinglyLinkedList() {
		head = null;
		tail = null;
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
	public Iterator<E> iterator() throws NoElementException {
		return new SinglyLLIterator<E>(head);
	}

	@Override
	public int find(E element) {
		SListNode<E> node = head;

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
		if(this.isEmpty()) {
			throw new NoElementException();
		}

		return head.getElement();
	}

	@Override
	public E getLast() throws NoElementException {
		if(this.isEmpty()) {
			throw new NoElementException();
		}

		return tail.getElement();
	}

	@Override
	public E get(int position) throws InvalidPositionException {
		if(position < 0 || position >= currentSize) {
			throw new InvalidPositionException();
		}

		if (position == this.size() - 1) {
			return tail.getElement();
		}

		SListNode<E> node = head;

		for (int i = 0; i < position; i++) {
			node = node.getNext();
		}
		return node.getElement();
	}

	@Override
	public void addFirst(E element) {
		SListNode<E> node = new SListNode<E>(element);

		if(this.isEmpty()) {
			head = node;
			tail = node;
		} else {
			node.setNext(head);
			head = node;
		}
		currentSize++;
	}

	@Override
	public void addLast(E element) {
		SListNode<E> node = new SListNode<E>(element);

		if(this.isEmpty()) {
			head = node;
			tail = node;
		} else {
			tail.setNext(node);;
			tail = node;
		}
		currentSize++;
	}

	@Override
	public void add(int position, E element) throws InvalidPositionException {
		if(position < 0 || position > currentSize) {
			throw new InvalidPositionException();
		}

		if(position == 0) {
			this.addFirst(element);
		} else if (position == currentSize) {
			this.addLast(element);
		} else {
			SListNode<E> prevNode = this.getNode(position- 1);
			SListNode<E> node = new SListNode<E>(element, prevNode.getNext());

			prevNode.setNext(node);
			currentSize++;
		}
	}

	@Override
	public E removeFirst() throws NoElementException {
		if(currentSize == 0) {
			throw new NoElementException();
		}

		E elm = head.getElement();
		if (currentSize == 1) {
			head = null;
			tail = null;
		} else {
			SListNode<E> node = head.getNext();
			head = node;
		}

		currentSize--;
		return elm;
	}


	@Override
	public E removeLast() throws NoElementException {
		if(this.size() == 0) {
			throw new NoElementException();
		}

		E elm = tail.getElement();
		if(currentSize == 1) {
			head = null;
			tail = null;
		} else {
			SListNode<E> node = this.getNode(currentSize-2);
			node.setNext(null);
			tail = node;
		}
		currentSize--;
		return elm;
	}

	protected void removeMiddleNode(SListNode<E> prevNode, SListNode<E> nodeToRemove) {
		prevNode.setNext(nodeToRemove.getNext());
		currentSize--;
	}

	@Override
	public E remove(int position) throws InvalidPositionException {
		if(position < 0 || position >= currentSize) {
			throw new InvalidPositionException();
		}

		if(position == 0) {
			return this.removeFirst();
		}
		if(position == currentSize - 1) {
			return this.removeLast();
		}
		SListNode<E> prevNode = this.getNode(position - 1);
		SListNode<E> nodeToRemove = prevNode.getNext();
		E elm = nodeToRemove.getElement();		
		
		this.removeMiddleNode(prevNode, nodeToRemove);
		return elm;
	}

	@Override
	public boolean remove(E element) {
		SListNode<E> prevNode = this.findPrevMiddleNode(element);
		if(prevNode == null) {
			return false;
		} else {
			if(head.getElement().equals(element)) {
				this.removeFirst();
			} else if(tail.getElement().equals(element)) {
				this.removeLast();
			} else {
				this.removeMiddleNode(prevNode, prevNode.getNext());;
			}
			return true;
		}
	}

	/**
	 * Devolve o no que esta em position nesta SinglyLinkedList<E>.
	 * @param position - posicao do no a retornar
	 * @return - no em position
	 * @throws InvalidPositionException - se a posicao for invalida.
	 */
	private SListNode<E> getNode(int position) throws InvalidPositionException {
		if(position < 0 || position >= this.size()) {
			throw new InvalidPositionException();
		}

		if (position == this.size() - 1) {
			return tail;
		}

		SListNode<E> node = head;

		for (int i = 0; i < position; i++) {
			node = node.getNext();
		}
		return node;
	}

	/**
	 * Devolve o no anterior ao no que contem element no caso deste
	 * no existir, senao devolve null.
	 * @param element - elemento do no.
	 * @return - no anterior ao no que contem element.
	 */
	private SListNode<E> findPrevMiddleNode(E element) {
		SListNode<E> node = head;

		for(int i = 0; i < currentSize-1; i++) {
			if(node.getNext().getElement().equals(element)) {
				return node;
			}
			node = node.getNext();
		}
		return null;
	}

}
