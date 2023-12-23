/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a interface Iterator<Entry<K,V>> permitindo que se itere 
 * as entries de uma arvore de pesquisa binaria pela relacao de ordem das 
 * chaves dos seu nos. 
 */


import contactNet.exceptions.NoSuchElementException;
import dataStructures.BinarySearchTree.BSTNode;

@SuppressWarnings("serial")
public class BSTKeyOrderIterator<K, V> implements Iterator<Entry<K, V>> {

	/*
	 * Raiz da arvore binaria de pesquisa.
	 */
	private BSTNode<K, V> root;
	
	/*
	 * Pilha que contem as entries a serem iteradas.
	 * A estrutura de dados escolhida foi a pilha em vetor pois permite o numero de elementos 
	 * colocados na pilha sera sempre inferior a size, nao necessitando de uma estrutura
	 * dinamica e gastando menos memoria tambem.
	 */
	private Stack<BSTNode<K, V>> nextEntries;
	
	
	/**
	 * Construtor da classe.
	 * Inicialisa as variaveis de instancia e coloca em ultimo na pilha o primeiro 
	 * elemento a devolver no next.
	 * @param root - raiz da arvore que vai ser iterada
	 * @param size - numero de elementos da arvore que vai ser iterada
	 */
	public BSTKeyOrderIterator(BSTNode<K, V> root, int size) {
		this.root = root;
		nextEntries = new StackInArray<BSTNode<K,V>>(size);
		this.rewind();
	}
	
	
	@Override
	public boolean hasNext() {
		return !nextEntries.isEmpty();
	}

	@Override
	public Entry<K, V> next() throws NoSuchElementException {
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
		
		BSTNode<K, V> node = nextEntries.pop();
		if(node.getRight() != null) {
			goLeftFrom(node.getRight());
		}
		return node.getEntry();
	}

	@Override
	public void rewind() {
		while(!nextEntries.isEmpty()) {
			nextEntries.pop();
		}
		goLeftFrom(root);
	}
	
	/**
	 * Insere node e todos os nos a esquerda do node na pilha.
	 * @param node - node a ser inserida na pilha.
	 */
	private void goLeftFrom(BSTNode<K, V> node) {
		if (node.getLeft() == null) {
			nextEntries.push(node);
		} else {
			nextEntries.push(node);
			this.goLeftFrom((node.getLeft()));
		}
	}
	
}
