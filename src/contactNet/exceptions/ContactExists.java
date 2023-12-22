/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet.exceptions;

/**
 * Classe que representa a excessao que e invocada quando dois utilizadores ja sao contactos.
 */

@SuppressWarnings("serial")
public class ContactExists extends RuntimeException {

	public ContactExists() {
		super();
	}
	
}
