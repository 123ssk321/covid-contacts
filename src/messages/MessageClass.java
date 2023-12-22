/*
 * @author Bruno Carmo n:57418 e Sahil Kumar n:57449
 */

package messages;


/*
 * Classe que implementa a interface MessageData, contendo por isso
 * todos os metodos e variaveis associados a acoes que possam vir a ser feitas
 * numa mensagem na aplicacao. 
 */


public class MessageClass implements MessageData {

	/*
	 * Descricao desta mensagem.
	 */
	private String description;
	
	/*
	 * Titulo desta mensagem.
	 */
	private String title;
	
	/*
	 * Url desta mensagem.
	 */
	private String url;
	
	
	/**
	 * Construtor da classe.
	 * Controi uma mensagem inicialisando as variaveis de intancias com os parametros
	 * de mesma designacao.
	 * @param title - titulo da mensagem.
	 * @param description - description desta mensagem.
	 * @param url - url desta mensagem.
	 */
	public MessageClass(String title, String description, String url) {
		this.title = title;
		this.description = description;
		this.url = url;
	}
	
	
	@Override
	public String description() {
		return description;
	}

	@Override
	public String title() {
		return title;
	}

	@Override
	public String url() {
		return url;
	}

}
