/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet.exceptions;

/**
 * Classe que representa a excessao que e invocada quando se tenta criar um grupo que tenha
 * um nome igual a outro grupo registado na aplicacao.
 */

@SuppressWarnings("serial")
public class GroupExists extends RuntimeException {

	public GroupExists() {
		super();
	}
	
}
