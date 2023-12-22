/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet.exceptions;

/**
 * Classe que representa a excessao que e invocada quando um utilizador tenta entrar nu grupo
 * mas ja e um participante nesse grupo.
 */

@SuppressWarnings("serial")
public class SubscriptionExists extends RuntimeException {

	public SubscriptionExists() {
		super();
	}
	
}
