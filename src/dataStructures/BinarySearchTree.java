/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a interface OrderedDictionary<K extends Comparable<K>, V> atraves de uma 
 * arvore de pesquisa binaria.
 */


import contactNet.exceptions.NoElementException;

/**
 * BinarySearchTree implementation
 * @author AED team
 * @version 1.0
 * @param <K> Generic type Key, must extend comparable
 * @param <V> Generic type Value 
 */

public class BinarySearchTree<K extends Comparable<K>, V> implements OrderedDictionary<K, V> {                                                                   

	/**
	 * BST node implementation
	 * 
	 * @author AED team
	 * @version 1.0
	 *
	 * @param <K> Generic type Key
	 * @param <V> Generic type Value 
	 */
	static class BSTNode<K, V> {                                                                   

		//Entry stored in the node.
		private EntryClass<K, V> entry;                                      

		//(Pointer to) the parent node.
		private BSTNode<K, V> parent;

		//(Pointer to) the left child.
		private BSTNode<K, V> leftChild;

		//(Pointer to) the right child.
		private BSTNode<K, V> rightChild;


		/**
		 * Constructor for BST nodes
		 * 
		 * @param key to be stored in this BST tree node
		 * @param value to be stored in this BST tree node
		 * @param left sub-tree of this node
		 * @param right sub-tree of this node
		 */
		public BSTNode(K key, V value, BSTNode<K,V> parent, BSTNode<K,V> left, BSTNode<K,V> right) {                                                                
			entry = new EntryClass<K, V>(key, value);
			this.parent = parent;
			leftChild = left; 
			rightChild = right;                                      
		}

		/**
		 * Constructor for BST nodes
		 * 
		 * @param key to be stored in this BST tree node
		 * @param value to be stored in this BST tree node
		 */
		public BSTNode(K key, V value) {    
			this(key, value, null, null, null);
		}


		/**
		 * Returns the parent node of the current node.
		 * 
		 * @return
		 */
		public BSTNode<K, V> getParent() {    
			return parent;
		}

		/**
		 * Returns the left child node of the current node.
		 * 
		 * @return
		 */
		public BSTNode<K, V> getLeft() {    
			return leftChild;
		}

		/**
		 * Returns the right child node of the current node.
		 * 
		 * @return
		 */
		public BSTNode<K, V> getRight() {    
			return rightChild;
		}

		/**
		 * Returns the entry (key and value) of the current node.
		 * 
		 * @return
		 */
		public EntryClass<K, V> getEntry() {
			return entry;
		}

		/**
		 * Returns the key of the current node.
		 * 
		 * @return
		 */
		public K getKey() {
			return entry.getKey();
		}

		/**
		 * Returns the value of the current node.
		 * 
		 * @return
		 */
		public V getValue() {
			return entry.getValue();
		}

		/**
		 * Assigns a new entry (key and value) to the current BST node
		 *   
		 * @param newEntry
		 */
		public void setEntry(EntryClass<K, V> newEntry) {    
			entry = newEntry;
		}

		/**
		 * Sets the new value object of the current node.
		 * 
		 * @param newValue
		 */
		public void setValue(V newValue) {
			K key = entry.getKey();
			entry = new EntryClass<K,V>(key, newValue);
		}

		/**
		 * Sets the new left child node of this node
		 * 
		 * @param newLeft the new left child node of the current node
		 */
		public void setLeft(BSTNode<K, V> newLeft) {    
			leftChild = newLeft;
		}

		/**
		 * Sets the new right child node of this node
		 * 
		 * @param newLeft the new right child node of the current node
		 */
		public void setRight(BSTNode<K, V> newRight) {    
			rightChild = newRight;
		}

		/**
		 * Sets the new parent of this node
		 * 
		 * @param newParent the new parent of the current node
		 */
		public void setParent(BSTNode<K, V> newParent) {    
			parent = newParent;
		}

		/**
		 * Returns true iff the current node is internal.
		 */
		public boolean isInternal() {
			return !isLeaf();
		}

		/**
		 * Returns true iff the node is a leaf.
		 * 
		 * @return
		 */
		public boolean isLeaf() {    
			return (leftChild == null) && (rightChild == null);          
		}                                                                  

	}


	//The root of the tree.                                            
	protected BSTNode<K, V> root;                                

	//Number of nodes in the tree.                                  
	private int currentSize;                   


	/**
	 * Tree Constructor - creates an empty tree.
	 */
	public BinarySearchTree() {    
		root = null;
		currentSize = 0;
	}


	/**
	 * Returns the number of children of node.
	 *                         
	 * @param node 
	 * @return the number of children of node 
	 */
	protected int numChildren(BSTNode<K, V> node) {
		if((node == null) || (node.isLeaf())) {
			return 0;
		}
		if((this.hasLeftChild(node) && this.hasRightChild(node))) {
			return 2;
		}
		return 1;
	}

	@Override
	public boolean isEmpty() {    
		return root == null;
	}

	@Override
	public int size() {
		return currentSize;
	}

	/**
	 * Returns the node whose key is the specified key;
	 * or null if no such node exists.        
	 *                         
	 * @param node where the search starts 
	 * @param key to be found
	 * @return the found node, when the search is successful
	 */
	protected BSTNode<K, V> findNode(BSTNode<K, V> node, K key) {
		if (node == null) {
			return null;
		} else {
			int compResult = key.compareTo( node.getKey() );

			if (compResult == 0) {
				return node;
			} else if (compResult < 0) {
				return this.findNode(node.getLeft(), key);
			} else {
				return this.findNode(node.getRight(), key);
			}
		}                 
	}

	@Override
	public V find(K key){    
		BSTNode<K, V> node = this.findNode(root, key);

		if (node == null) {                                   
			return null;
		} else {                                                     
			return node.getValue();
		}
	}

	@Override
	public Entry<K, V> minEntry() throws NoElementException {                                                                   
		if (this.isEmpty()) {                              
			throw new NoElementException();           
		}

		return this.minNode(root).getEntry();                     
	}

	/**
	 * Returns the node with the largest key 
	 * in the tree rooted at the specified node.
	 * Requires: node != null.
	 * @param node that roots the tree
	 * @return node with the largest key in the tree
	 */
	protected BSTNode<K, V> minNode(BSTNode<K, V> node) {
		if (node.getLeft() == null) {         
			return node;
		} else {                                                     
			return this.minNode(node.getLeft());
		}
	}          

	@Override
	public Entry<K, V> maxEntry() throws NoElementException {                                                                   
		if (this.isEmpty()) {                              
			throw new NoElementException();           
		}

		return this.maxNode(root).getEntry();                    
	}

	/**
	 * Returns the node with the largest key 
	 * in the tree rooted at the specified node.
	 * Requires: node != null.
	 * @param node that roots the tree
	 * @return node with the largest key in the tree
	 */
	protected BSTNode<K, V> maxNode(BSTNode<K, V> node) {                                                                   
		if (node.getRight() == null) {                            
			return node;
		} else {                                                    
			return this.maxNode(node.getRight()); 
		}
	}                               

	public V insert( K key, V value ) {
		BSTNode<K, V> node = new BSTNode<K, V>(key, value);
		node = this.insertNode(node, key);
		if(node == null) {
			return null;
		}
		return node.getValue();
	}

	/**
	 * Insere node nesta BinarySearchTree e retorna null key ainda nao existir no dicionario.
	 * Caso key exista entao retorna node mas com a value do no que tinha a mesma chave que key.
	 * @param node - no a ser inserido
	 * @param key - chave de node
	 * @return - null se key nao existir no dicionario ou o value antigo do no com a chave igual a key
	 */
	protected BSTNode<K, V> insertNode(BSTNode<K, V> node, K key){
		V oldV = null;
		BSTNode<K, V> newNode = node;

		if (root == null) {//arvore estava vazia
			//caso especial  inserir raiz
			root = node;
		} else {	
			BSTNode<K, V> parent = findPlaceToInsert(root, key);  //metodo auxiliar para implementarem
			//findPlaceToInsert - parecido com o findNode mas devolve pai do novo no
			int compResult = key.compareTo(parent.getKey());

			if(compResult == 0) {
				oldV = parent.getValue();
				parent.setValue(node.getValue());
				node.setValue(oldV);
			} else {
				if(compResult < 0) {
					parent.setLeft(newNode);
				} else {
					parent.setRight(newNode);
				}
				newNode.setParent(parent);
			}
		}
		if(oldV == null) { 
			currentSize++;
			return null;
		}
		return node;
	}
	/**
	 * Encontra o pai de um novo no.
	 * @param node - no onde começa a pesquisa.
	 * @param key - chave do novo no.
	 * @return - no pai.
	 */
	private BSTNode<K, V> findPlaceToInsert(BSTNode<K, V> node, K key) {
		int compResult = key.compareTo( node.getKey() );

		if ( compResult < 0 ) {
			if(!this.hasLeftChild(node)) {
				return node;
			}
			return this.findPlaceToInsert(node.getLeft(), key);
		} else {
			if(!this.hasRightChild(node)) {
				return node;
			}
			return this.findPlaceToInsert(node.getRight(), key); 
		}
	}                 

	/**
	 * Verifica se um no tem filho esquerdo.
	 * @param node - no pai.
	 * @return - true se o no tiver filho esquerdo.
	 */
	protected boolean hasLeftChild(BSTNode<K, V> node) {
		if(node == null) {
			return false;
		}
		return node.leftChild != null; 
	}

	/**
	 * Verifica se um no tem filho direito.
	 * @param node - no pai.
	 * @return - true se o no tiver filho direito.
	 */
	protected boolean hasRightChild(BSTNode<K, V> node) {
		if(node == null) {
			return false;
		}
		return node.rightChild != null; 
	}

	//sugestao: implementar metodo auxiliary replaceParentWithChild(nodeToRemove, child) que poe
	//pai de noteToRemove a apontar para child (filho de nodeToRemove)
	public V remove (K key) {
		BSTNode<K, V> nodeRemoved = this.removeNode(key);

		if(nodeRemoved != null) {
			return nodeRemoved.getValue();
		}
		return null;
	}

	/**
	 * Remove node desta BinarySearchTree retornando esse no.
	 * @param node - no a ser inserido
	 * @param key - chave de node
	 * @return - o no removido
	 */
	protected BSTNode<K, V> removeNode(K key){
		BSTNode<K, V> nodeToRemove = findNode(root, key);

		if (nodeToRemove != null) {
			/*se nodeToRemove so tem um filho ou e folha
			 *   o pai fica a apontar para esse filho (ou para null) */
			if(nodeToRemove.isLeaf() || (this.numChildren(nodeToRemove) == 1)) {		
				if(this.hasLeftChild(nodeToRemove)) {
					this.replaceParentWithChild(nodeToRemove, nodeToRemove.leftChild);
				} else {
					this.replaceParentWithChild(nodeToRemove, nodeToRemove.rightChild);
				}
			} else {
				/*senao  //tem 2 filhos
				 *  procurar no substituto (na subarvore esq. ou na dir.)
				 *  remover no substituto
				 *  trocar entry do nodeToRemove pela entry do no substituto
				 *  (reparem que, caso ainda nao o tenham feito, e preciso 
				 *  implementar a classe EntryClass, e precisam do metodo setEntry)
				 */ 
				BSTNode<K, V> substitute = this.maxNode(nodeToRemove.leftChild);
				this.replaceParentWithChild(substitute, substitute.leftChild);
				nodeToRemove.setEntry(substitute.entry);
				nodeToRemove = substitute;
			}
			currentSize--;
			return nodeToRemove;
		} else {  //nao encontra no para remover
			return null;
		}
	}

	/**
	 * Poe pai de nodeToRemove a apontar para child (filho de nodeToRemove).
	 * @param nodeToRemove - no a remover.
	 * @param child - filho de nodeToRemove.
	 */
	private void replaceParentWithChild(BSTNode<K, V> nodeToRemove, BSTNode<K, V> child) {
		BSTNode<K, V> parent = nodeToRemove.parent;

		if(parent == null) {
			root = child;
		} else {
			int compResult = nodeToRemove.getKey().compareTo(parent.getKey());

			if(compResult < 0) {
				parent.leftChild = child;
			} else {
				parent.rightChild = child;
			}
		}
		if(child != null) {
			child.parent = parent;
		}
	}

	/**
	 * Returns an iterator of the entries in the dictionary 
	 * which preserves the key order relation.
	 * @return  key-order iterator of the entries in the dictionary
	 */
	public Iterator<Entry<K,V>> iterator() {
		//TODO: Original comentado para nao dar erro de compilacao.
		return new BSTKeyOrderIterator<K,V>(root, currentSize);
	}

	/**
	 * Devolve um iterador dos values do dicionario mantendo a relacao de ordem
	 * entre as chaves.
	 * @return - iterador de values pela relacao de ordem das chaves.
	 */
	public Iterator<V> values(){
		return new DictionaryValuesIterator<K, V>(this.iterator());
	}

}
