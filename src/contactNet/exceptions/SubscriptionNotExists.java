/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet.exceptions;

/**
 * Classe que representa a excessao que e invocada quando tenta se remover um utilizador 
 * de um grupo mas nao e um participante nesse grupo.
 */

@SuppressWarnings("serial")
public class SubscriptionNotExists extends RuntimeException {

	public SubscriptionNotExists() {
		super();
	}
	
}
