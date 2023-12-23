/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a interface Entry<K,V>. 
 */


public class EntryClass<K, V> implements Entry<K, V> {

	/*
	 * Chave desta EntryClass.
	 */
	private K key;

	/*
	 * Valor desta EntryClass.
	 */
	private V value;


	/**
	 * Construtor da classe.
	 * Inicialisa as variaveis de instancia da classe.
	 * @param key - chave desta EntryClass.
	 * @param value - valor desta EntryClass.
	 */
	public EntryClass(K key, V value) {
		this.key = key;
		this.value = value;
	}


	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

}
