/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet.exceptions;

/**
 * Classe que representa a excessao que e invocada quando dois utilizadores nao sao contactos.
 */

@SuppressWarnings("serial")
public class ContactNotExists extends RuntimeException {

	public ContactNotExists() {
		super();
	}
	
}
