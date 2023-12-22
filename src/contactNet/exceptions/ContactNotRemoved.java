/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet.exceptions;

/**
 * Classe que representa a excessao que e invocada quando tenta se remover um contacto
 * mas os dois utilizadores tem logins identicos.
 */

@SuppressWarnings("serial")
public class ContactNotRemoved extends RuntimeException{
	
	public ContactNotRemoved() {
		super();
	}

}
