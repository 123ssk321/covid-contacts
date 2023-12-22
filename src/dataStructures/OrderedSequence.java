/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Interface que representa uma sequencia de elementos
 * ordenada, de acordo com o metodo compareTo dos elementos.
 */


import contactNet.exceptions.InvalidPositionException;

public interface OrderedSequence<E extends Comparable<E>> {
	
	/**
	 * Remove um elemento desta sequencia, devolvendo true se a remocao
	 * do elemento foi bem sucedida.
	 * @param element - elemento a ser removido.
	 * @return - true se a remocao foi um sucesso.
	 */
    boolean remove(E element);
	
    /**
     * Verifica se element existe nesta sequencia.
     * @param element - elemento a ser verificada a existencia nesta sequencia
     * @return - true se element existir nesta sequencia.
     */
	boolean contains(E element);
	
	/**
	 * Verifica se a sequencia esta vazia.
	 * @return - true se a sequencia estiver vazia.
	 */
	boolean isEmpty();
	
	/**
	 * Devolve o elemento na posicao especificada desta sequencia.
	 * @param pos - posicao do elemento a ser retornado.
	 * @return - elemento em pos na sequencia.
	 * @throws InvalidPositionException - se pos nao for uma posicao valida na sequencia.
	 */
	E get(int pos) throws InvalidPositionException;
	
	/**
	 * Devolve um objeto do tipo Iterator que itera sobre todos os elementos
	 * desta sequencia.
	 * @return - um Iterator sobre os elementos desta sequencia.
	 */
	Iterator<E> iterator();
	
	/**
	 * Adiciona um elemento nesta sequencia na ordem correta definida pelo metodo compareTo
	 * do elemento
	 * @param element - elemento a ser adicionado nesta sequencia.
	 */
	void insert(E element);

}
