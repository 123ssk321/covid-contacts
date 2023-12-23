/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a interface Iterator<Entry<K, V>> permitindo iterar todos 
 * os elementos da tabela de dispersao sem nenhuma ordem definida.
 */


import contactNet.exceptions.NoSuchElementException;

@SuppressWarnings("serial")
public class HashTableIterator<K, V> implements Iterator<Entry<K, V>> {

	/*
	 * Vetor que representa a tabela que contem as listas de colisoes, ou seja, a tabela
	 * de dispersao aberta.
	 */
	private Dictionary<K, V>[] table;

	/*
	 * Iterador da lista de colisao atual.
	 */
	private Iterator<Entry<K, V>> currentListIterator;

	/*
	 * Posicao da lista de colisao atual.
	 */
	private int currentListIndex;


	/**
	 * Construtor da classe.
	 * Inicialisa as variaveis de instancia e coloca currentListIterator apontar para a primeira
	 * lista de colisao que vai ser iterada.
	 * @param table - tabela de dispersao aberta que vai ser iterada
	 */
	public HashTableIterator(Dictionary<K, V>[] table) {
		this.table = table;
		this.rewind();
	}

	@Override
	public boolean hasNext() {
		return (currentListIterator != null) && (currentListIterator.hasNext());
	}

	@Override
	public Entry<K,V> next() throws NoSuchElementException {
		if(!this.hasNext()) {
			throw new NoSuchElementException();
		}

		Entry<K, V> next = currentListIterator.next();
		if(!currentListIterator.hasNext()) {
			currentListIterator = this.nextListIterator(++currentListIndex);
		}
		return next;
	}

	@Override
	public void rewind() {
		currentListIterator = nextListIterator(0);
	}

	/**
	 * Devolve o iterador da proxima lista nao vazia na tabela a partir de start.
	 * @param start - posicao por onde se comeca a procurar o proximo iterador.
	 * @return - iterador da proxima lista nao vazia na tabela.
	 */
	private Iterator<Entry<K,V>> nextListIterator(int start){
		for(int i = start;i < table.length; i++) {
			if(!table[i].isEmpty()) {
				currentListIndex = i;
				return table[i].iterator();
			}
		}
		return null;
	}

}
