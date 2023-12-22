/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a interface TwoWayIterator contendo por isso os metodos que permitem
 * a um iterador em duas direcoes: para frente a para tras.
 */


import dataStructures.DoublyLinkedList.DListNode;

import contactNet.exceptions.NoSuchElementException;

class DoublyLLIterator<E> implements TwoWayIterator<E> {

	/**
	 * Serial Version UID of the Class
	 */
    static final long serialVersionUID = 0L;


    /** 
     * Node with the first element in the iteration.
     */
    private DListNode<E> firstNode;

    /**
     * Node with the last element in the iteration.
     */
    private DListNode<E> lastNode;

    /**
     * Node with the next element in the iteration.
     */
    private DListNode<E> nextToReturn;

    /**
     * Node with the previous element in the iteration.
     */
    private DListNode<E> prevToReturn;


    /**
     * DoublyLLIterator constructor
     * @param first - Node with the first element of the iteration
     * @param last - Node with the last element of the iteration
     */
    public DoublyLLIterator(DListNode<E> first, DListNode<E> last) {
        firstNode = first;
        lastNode = last;
        nextToReturn = null;
        prevToReturn = null;
        this.rewind();
    }      

    
    @Override
    public void rewind() {
        nextToReturn = firstNode;
        prevToReturn = firstNode;
    }

    @Override
    public void fullForward() {
        prevToReturn = lastNode;
        nextToReturn = lastNode;
    }

    @Override
    public boolean hasNext() {
        return nextToReturn != null && nextToReturn.getElement() != null;
    }

    @Override
    public boolean hasPrevious() {
        return prevToReturn != null && prevToReturn.getElement() != null;
    }

    @Override
    public E next() throws NoSuchElementException {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        
        E elm = nextToReturn.getElement();
        prevToReturn = nextToReturn; 
        nextToReturn = nextToReturn.getNext();
        return elm;
    }

    @Override
    public E previous() throws NoSuchElementException {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        }
        
        E elm = prevToReturn.getElement();
        nextToReturn = prevToReturn;
        prevToReturn = prevToReturn.getPrevious();
        return elm;
    }

}
