/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a interface OrderedDictionary<K extends Comparable<K>, V> atraves de uma 
 * arvore de pesquisa binaria sempre equilibrada.
 */


public class AVLTree <K extends Comparable<K>,V> extends AdvancedBSTree<K,V> {

	static class AVLNode<K,V> extends BSTNode<K,V> {
		// Height of the node
		protected int height;

		/**
		 * Constructor for AVL nodes
		 * 
		 * @param key to be stored in this BST tree node
		 * @param value to be stored in this BST tree node
		 */
		public AVLNode(K key, V value) {
			super(key, value);
			height = 1;
		}

		/**
		 * Constructor for AVL nodes
		 * 
		 * @param key to be stored in this AVL tree node
		 * @param value to be stored in this AVL tree node
		 * @param left sub-tree of this node
		 * @param right sub-tree of this node
		 */
		public AVLNode(K key, V value, AVLNode<K,V> parent, AVLNode<K,V> left, AVLNode<K,V> right){ 
			super(key, value, parent, left, right);
			height = 1 + Math.max(getHeight(left),getHeight(right));
		}

		/**
		 * Devolve a altura de um no.
		 * @param node - no.
		 * @return - altura de node.
		 */
		protected int getHeight(AVLNode<K,V> node) {
			//precisamos deste metodo porque node pode ser null
			if (node==null)
				return 0;
			return node.getHeight();
		}

		/**
		 * Devolve a altura deste no.
		 * @param node - no.
		 * @return - altura do no.
		 */
		public int getHeight() {
			return height;
		}

		/**
		 * Verifica se este no esta balanceado.
		 * @return - true se o no estiver balanceado.
		 */
		public boolean isBalance() {
			int dif= getHeight((AVLNode<K,V>)this.getLeft()) - getHeight((AVLNode<K,V>)this.getRight());
			return dif==0 ||dif==-1 ||dif ==1;
		}

		/**
		 * Atualiza a altura deste no.
		 * @return - altura do no.
		 */
		public int setHeight() {
			height= 1 + Math.max(getHeight((AVLNode<K,V>)this.getLeft()), getHeight((AVLNode<K,V>)this.getRight()));
			return height;
		}

		/** 
		 * Return the child with greater height
		 */
		protected AVLNode<K,V> tallerChild()  {
			AVLNode<K,V> leftChild = (AVLNode<K,V>) this.getLeft();
			AVLNode<K,V> rightChild = (AVLNode<K,V>) this.getRight();
			int leftChildHeight  = getHeight((AVLNode<K,V>) leftChild);
			int rightChildHeight = getHeight((AVLNode<K,V>) rightChild);

			if (leftChildHeight > rightChildHeight)
				return leftChild;
			else if (leftChildHeight < rightChildHeight)
				return rightChild;
			return rightChild;
		}
	}

	
	/**
	 * Tree Constructor - creates an empty AVL tree.
	 */
	public AVLTree() {
		super();
	}

	
	/**  
	 * Rebalance method called by insert and remove.  Traverses the path from 
	 * zPos to the root. For each node encountered, we recompute its height 
	 * and perform a trinode restructuring if it's unbalanced.
	 * the rebalance is completed with O(log n)running time
	 */
	protected void rebalance(AVLNode<K,V> zPos) {
		boolean heightChanged = false;
		int oldHeight = 0;
		int newHeight = 0;
		if(zPos.isInternal())
			zPos.setHeight();
		
		while ((zPos != null) && !heightChanged) {  // traverse up the tree towards the root
			oldHeight = zPos.getHeight();
			zPos.setHeight();
			newHeight = zPos.getHeight();
			heightChanged = oldHeight != newHeight;
			//so o no nao balanceado necessita de ser balanceado logo para o ciclo depois de balancar esse no
			if (!zPos.isBalance()) { 
				//perform a trinode restructuring at zPos's tallest grandchild
				//If yPos (tallerChild(zPos)) denote the child of zPos with greater height. 
				//Finally, let xPos be the child of yPos with greater height
				AVLNode<K,V> xPos = zPos.tallerChild().tallerChild();
				zPos = (AVLNode<K, V>) restructure(xPos); // tri-node restructure (from parent class)
				//note that zPos now may be a different node (the new root of the tri-node)

				// recompute heights for these 3 nodes
				((AVLNode<K, V>) zPos.getLeft()).setHeight();  
				((AVLNode<K, V>) zPos.getRight()).setHeight();
				zPos.setHeight();
				heightChanged = true;
			}
			zPos = (AVLNode<K, V>) zPos.getParent();
		}
	} 

	@Override
	public V insert(K key, V value) {
		//TODO
		AVLNode<K,V> newNode = new AVLNode<K, V>(key, value);
		V oldV = null;		
		BSTNode<K, V> oldNode = super.insertNode(newNode, key);

		if(oldNode != null) {
			oldV = oldNode.getValue();
		}
		if(oldV == null) { 
			rebalance(newNode);// rebalance up from the inserted node
			//rebalance checks if it needs to call restructure
		}
		return oldV;
	}             

	@Override
	public V remove(K key) {
		// TODO
		BSTNode<K,V> nodeRemoved= super.removeNode(key); //no removido
		V valueToReturn =null;

		if(nodeRemoved != null) {
			valueToReturn = nodeRemoved.getValue();
			if(nodeRemoved.getParent() != null) {   //(if find(key)==null)
				AVLNode<K,V> node = (AVLNode<K, V>) nodeRemoved.getParent(); // father of node where the key was deleted
				rebalance(node);
			} // rebalance up from the node
		}
		return valueToReturn;
	}

}
