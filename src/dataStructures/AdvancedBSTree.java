/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/** 
 * Classe que implementa a interface OrderedDictionary<K extends Comparable<K>, V> atraves de uma 
 * arvore de pesquisa binaria onde e possivel alterar a estrutura da arvore atraves de rotacoes
 * (esquerda ou direita) nos seus nos.
 */


public class AdvancedBSTree <K extends Comparable<K>, V> extends BinarySearchTree<K,V>{

	// Metodos comuns a arvores binarias de pesquisa auto-equilibradas.
	// Operacoes basicas para alterar a forma da arvore tratando
	// de reduzir a sua altura.

	/**
	 * Performs a single right rotation rooted at Y node.
	 * Node X was a  left  child  of Y before the  rotation,  
	 * then Y becomes the right child of X after the rotation.
	 * @param y - root of the rotation
	 * @pre: Y has a left child
	 *    Y				X
	 *   /	turns into:	 \
	 *  X 				  Y
	 */
	protected void rotateRight( BSTNode<K,V> y){
		// a single rotation modifies a constant number of parent-child relationships, 
		// it can be implemented in O(1) time

		BSTNode<K, V> x = y.getLeft();
		BSTNode<K, V> z = y.getParent();
		x.setParent(z);
		if (this.isLeftChild(y, z)) {
			z.setLeft(x);
		} else if (this.isRightChild(y, z)){
			z.setRight(x);
		}
		y.setParent(x);
		y.setLeft(x.getRight());
		x.setRight(y);
		if(z == null) {
			root = x;
		}
	}

	/**
	 * Performs a single left rotation rooted at Y node.
	 * Node X was a  right  child  of Y before the  rotation,  
	 * then Y becomes the left child of X after the rotation.
	 * @param y - root of the rotation
	 * @pre: Y has a right child
	 *    Y				      X
	 *     \  turns into:	 /
	 *      X 				Y
	 */
	protected void rotateLeft( BSTNode<K,V> y){
		//  a single rotation modifies a constant number of parent-child relationships, 
		// it can be implemented in O(1) time

		BSTNode<K, V> x = y.getRight();
		BSTNode<K, V> z = y.getParent();
		x.setParent(z);
		if(this.isLeftChild(y, z)) {
			z.setLeft(x);
		} else if(this.isRightChild(y, z)){
			z.setRight(x);
		}
		y.setParent(x);
		y.setRight(x.getLeft());
		x.setLeft(y);
		if(z == null) {
			root = x;
		}
	}

	/** 
	 * Performs a tri-node restructuring (a single or double rotation).
	 * Assumes the nodes are in one of following configurations:
	 *
	 * @param x - a node that has both a parent y and a grandparent z
	 * <pre>
	 *          z=c       z=c        z=a         z=a
	 *         /  \      /  \       /  \        /  \
	 *       y=b  t4   y=a  t4    t1  y=c     t1  y=b
	 *      /  \      /  \           /  \         /  \
	 *    x=a  t3    t1 x=b        x=b  t4       t2 x=c
	 *   /  \          /  \       /  \             /  \
	 *  t1  t2        t2  t3     t2  t3           t3  t4
	 * </pre>
	 * @return the new root of the restructured subtree
	 */
	protected BSTNode<K,V> restructure (BSTNode<K,V> x) {
		// the modification of a tree T caused by a trinode restructuring operation
		// can be implemented through case analysis either as a single rotation or as a double rotation.
		// The double rotation arises when position x has the middle of the three relevant keys
		// and is first rotated above its parent y, and then above what was originally its grandparent z. 
		// In any of the cases, the trinode restructuring is completed with O(1)running time.
		// [Goodrich et al., 2015]
		//TODO
		BSTNode<K, V> y = x.getParent();
		BSTNode<K, V> z = y.getParent();
		if(this.isLeftChild(x, y) && this.isLeftChild(y, z)) {
			this.rotateRight(z);
			return y;
		} else if(this.isRightChild(x, y) && this.isRightChild(y, z)) {
			this.rotateLeft(z);
			return y;
		} else if(this.isRightChild(x, y) && this.isLeftChild(y, z)) {
			this.rotateLeft(y);
			this.rotateRight(z);
			return x;
		} else if(this.isLeftChild(x, y) && this.isRightChild(y, z)){
			this.rotateRight(y);
			this.rotateLeft(z);
			return x;
		}
		return null;
	}

	/**
	 * Verifica se um no e o filho esquerdo de outro no.
	 * @param child - o no filho.
	 * @param parent - no pai.
	 * @return - true se child for filho esquerdo de parent.
	 */
	private boolean isLeftChild(BSTNode<K, V> child, BSTNode<K, V> parent) {
		return super.hasLeftChild(parent) && (parent.getLeft().getKey().equals(child.getKey())); 
	}

	/**
	 * Verifica se um no e o filho direito de outro no.
	 * @param child - o node filho.
	 * @param parent - node pai.
	 * @return - true se child for filho direto de parent.
	 **/
	private boolean isRightChild(BSTNode<K, V> child, BSTNode<K, V> parent) {
		return super.hasRightChild(parent) && (parent.getRight().getKey().equals(child.getKey())); 
	}

}
