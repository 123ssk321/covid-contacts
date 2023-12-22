/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package dataStructures;


/*
 * Classe que implementa a interface OrderedSequenceClass contendo, por isso,
 * a implementacao de todos os metodos associados a uma sequencia ordenada de 
 * elementos.
 */


import contactNet.exceptions.InvalidPositionException;

public class OrderedSequenceClass<E extends Comparable<E>> implements OrderedSequence<E> {

	/*
	 * Colecao de elementos do tipo <E> ordenados.
	 * A estrutura de dados escolhida foi a lista duplamente ligada, pois, o numero de elementos que 
	 * a sequencia pode ter e ilimitado, logo escolha de uma estrutura dinamica e nao de uma 
	 * estrutura estatica como o vetor, onde o "custo" temporal de uma operacao de resize iria cobrir
	 * todas as desvantagens que a lista poderia ter em relacao ao vetor.
	 */
	private List<E> list;

	/**
	 * Construtor desta sequencia.
	 * Constroi um lista duplamente ligada.
	 */
	public OrderedSequenceClass() {
		list = new DoublyLinkedList<E>();
	}


	@Override
	public boolean remove(E element) {
		return list.remove(element);
	}

	@Override
	public boolean contains(E element) {
		return list.find(element) != -1;
	}

	@Override
	public E get(int pos) throws InvalidPositionException {
		return list.get(pos);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public void insert(E element) {
		Iterator<E> it = list.iterator();
		boolean inserted = false;
		int i = 0;
		
		if(list.isEmpty()) {
			list.addFirst(element);
		} else {
			while((it.hasNext()) && !inserted) {
				E elm = it.next();
				if(element.compareTo(elm) < 0) {
					list.add(i, element); 
					inserted = true;
				}
				i++;
			}
			if(!inserted) {
				list.addLast(element);
			}
		}
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

}
