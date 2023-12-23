/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a interface Iterator<Entry<K,V>> permitindo que se itere 
 * apenas os values das entries de um dicionario pela relacao 
 * de ordem das chaves dos seu nos. 
 */


import contactNet.exceptions.NoSuchElementException;

@SuppressWarnings("serial")
public class DictionaryValuesIterator<K, V> implements Iterator<V> {
	
	/*
	 * Iterador de entries cujas values vao ser iteradas por este DictionaryValuesIterator.
	 */
	private Iterator<Entry<K, V>> it;
	
	
	/**
	 * Construtor da classe.
	 * Associa o iterador de entries deste DictionaryValuesIterator a iterator que contem 
	 * as entries cujas values vao ser iteradas, ou seja, inicialisa a variavel do tipo
	 * Iterator<Entry<K,V>> com iterator.
	 * Inicia este DictionaryValuesIterator na primeira posicao a iterar.
	 * @param iterator - iterador cujas values vao ser iteradas.
	 */
	public DictionaryValuesIterator(Iterator<Entry<K, V>> iterator) {
		it = iterator;
		it.rewind();
	}

	
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public V next() throws NoSuchElementException {
		Entry<K, V> entry = it.next();
		return entry.getValue();
	}

	@Override
	public void rewind() {
		it.rewind();
	}

}
