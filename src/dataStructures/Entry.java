/*
 * Interface usada por Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Interface que representa uma entry com uma chave e um valor.
 */


public interface Entry<K,V> {

	//Returns the key in the entry
	K getKey();

	//Returns the value in the entry
	V getValue();

}
