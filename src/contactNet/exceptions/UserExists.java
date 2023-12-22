/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet.exceptions;

/**
 * Classe que representa a excessao que e invocada quando um tenta se adicionar utilizador
 * ja existente no sistema.
 */
@SuppressWarnings("serial")
public class UserExists extends RuntimeException {

	public UserExists() {
		super();
	}

}
