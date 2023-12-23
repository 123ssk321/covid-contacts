/*
 * Classe usada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;

import contactNet.exceptions.NoElementException;
import contactNet.exceptions.OutOfCapacityException;

/**
 * Stack Array Implementation 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */

public class StackInArray<E> implements Stack<E> {

	/**
	 * Serial Version UID of the Class
	 */
	static final long serialVersionUID = 0L;


	/**
	 *  Default capacity of the stack.
	 */
	public static final int DEFAULT_CAPACITY = 1000;

	/**
	 * Memory of the stack: an array.
	 */
	private E[] array;                                     

	/**
	 * Index of the element at the top of the stack.
	 */
	private int top;                                       

	/**
	 * Creates a Stack on an array with capacity <code> capacity</code>
	 * @param capacity - maximum capacity of the stack
	 */
	@SuppressWarnings("unchecked")
	public StackInArray(int capacity) {  
		// Compiler gives a warning.
		array = (E[]) new Object[capacity];
		top = -1;
	}


	/**
	 * Creates a Stack on an array with capacity DEFAULT_CAPACITY
	 */
	public StackInArray() {  
		this(DEFAULT_CAPACITY);
	}

	
	@Override
	public boolean isEmpty() {  
		return top == -1;
	}

	/**
	 *  Returns true iff the stack cannot contain more elements.
	 * @return true iff the stack is full, false otherwise
	 */
	public boolean isFull() {  
		return this.size() == array.length; 
	}

	@Override
	public int size() {  
		return top + 1;
	}


	/* 
	 * @see dataStructures.Stack#top()
	 */
	@Override
	public E top() throws NoElementException {  
		if (this.isEmpty())
			throw new NoElementException();

		return array[top];
	}


	/**
	 *  Inserts the specified <code>element</code> onto the top of the stack.
	 * @param element element to be inserted onto the stack
	 * @throws FullStackException when stack is full
	 */
	public void push(E element) throws OutOfCapacityException {  
		if (this.isFull())
			throw new OutOfCapacityException();

		top++; 
		array[top] = element;
	}


	@Override
	public E pop( ) throws NoElementException {  
		if (this.isEmpty())
			throw new NoElementException();

		E element = array[top];
		array[top] = null;    // For garbage collection.
		top--;
		return element;
	}                                                 

}
