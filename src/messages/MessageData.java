/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package messages;


/*
 * 
 * Interface que representa uma mensagem na aplicacao.
 */


public interface MessageData {
	
	/**
	 * Devolve a descricao desta mensagem.
	 * @return - descricao desta mensage.
	 */
	String description();
	
	/**
	 * Devolve o titulo desta mensagem.
	 * @return - titulo desta mensage.
	 */
	String title();
	
	/**
	 * Devolve o url desta mensagem.
	 * @return - url desta mensage.
	 */
	String url();

}
