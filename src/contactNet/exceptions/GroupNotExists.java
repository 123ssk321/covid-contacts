/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package contactNet.exceptions;

/**
 * Classe que representa a excessao que e invocada quando nao existe o grupo pedido.
 */

@SuppressWarnings("serial")
public class GroupNotExists extends RuntimeException {
	
	public GroupNotExists() {
		super();
	}

}
