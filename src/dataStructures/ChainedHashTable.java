/*
 * Classe implementada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;

/**
 * Chained Hash table implementation
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key, must extend comparable
 * @param <V> Generic Value 
 */

public class ChainedHashTable<K extends Comparable<K>, V> extends HashTable<K,V> { 

	//The array of dictionaries.
	private Dictionary<K,V>[] table;


	/**
	 * Constructor of an empty chained hash table,
	 * with the specified initial capacity.
	 * Each position of the array is initialized to a new ordered list
	 * maxSize is initialized to the capacity.
	 * @param capacity defines the table capacity.
	 */
	@SuppressWarnings("unchecked")
	public ChainedHashTable(int capacity) {
		int arraySize = HashTable.nextPrime((int) (1.1 * capacity));
		// Compiler gives a warning.
		table = (Dictionary<K,V>[]) new Dictionary[arraySize];
		for (int i = 0; i < arraySize; i++)
			table[i] = new CollisionList<K,V>();
		maxSize = capacity;
		currentSize = 0;
	}                                      

	public ChainedHashTable() {
		this(DEFAULT_CAPACITY);
	}                                                                


	/**
	 * Returns the hash value of the specified key.
	 * @param key to be encoded
	 * @return hash value of the specified key
	 */
	protected int hash(K key) {
		return Math.abs(key.hashCode()) % table.length;
	}

	@Override
	public V find(K key) {
		return table[this.hash(key)].find(key);
	}

	@Override
	public V insert(K key, V value) {
		if (this.isFull()) {
			this.rehash();
		}
		//TODO: inserir novo elemento com key e value (ver comentarios
		//na interface de dicionario para saber o que deve ser devolvido 
		//no return
		V oldValue = table[this.hash(key)].insert(key, value); 		
		if(oldValue == null) {
			currentSize++;
		}
		return oldValue;
	}

	@Override
	public V remove(K key) {
		V value = table[this.hash(key)].remove(key);
		if(value != null) {
			currentSize--;
		} 
		return value;
	}

	@Override
	public Iterator<Entry<K,V>> iterator() {
		return new HashTableIterator<K, V>(table);
	}

	/**
	 * Devolve um iterador dos values do dicionario mantendo a relacao de ordem
	 * entre as chaves.
	 * @return - iterador de values pela relacao de ordem das chaves.
	 */
	public Iterator<V> values(){
		return new DictionaryValuesIterator<K, V>(this.iterator());
	}

	/**
	 * Metodo que aumenta a capacidade desta ChainedHashTable, ou seja, cria uma ChainedHashTable temporaria, 
	 * com o dobro do tamanho maximo da tabela atual insere todos os elementos da tabela 
	 * antiga na temporaria, e muda o apontador da tabela antiga para a temporaria.
	 */
	private void rehash() {
		super.maxSize = table.length*2;
		ChainedHashTable<K, V> tmp = new ChainedHashTable<K, V>(maxSize);
		Iterator<Entry<K, V>> it = this.iterator();

		while(it.hasNext()) {
			Entry<K, V> entry = it.next();
			tmp.insert(entry.getKey(), entry.getValue());
		}
		this.table = tmp.table;
	}

}
