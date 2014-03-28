package ezBilhetes.core;

import java.io.Serializable;

/**
 * 
 * Classe representativa de um espectáculo. Contém o nome e classificação atribuída a um espectáculo.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public final class Espectaculo implements Serializable {
	private static final long serialVersionUID = -3933856061362622930L;
	
	private String _nome;
	private String _classificacao;
	
	/**
	 * Cria um novo espectáculo com o <code>nome</code> e <code>classificacao</code> fornecidos.
	 * 
	 * @param nome nome do espectáculo a criar.
	 * @param classificacao classificação atribuída ao espectáculo.
	 */
	public Espectaculo(String nome, String classificacao) {
		set(nome, classificacao);
	}
	
	/**
	 * Retorna o nome do espectáculo.
	 * 
	 * @return o nome do espectáculo.
	 */
	public final String getNome() {
		return _nome;
	}
	
	/**
	 * Retorna a classificação do espectáculo.
	 * 
	 * @return a classificação do espectáculo.
	 */
	public final String getClassificacao() {
		return _classificacao;
	}
	
	/**
	 * Modifica um espectáculo com o <code>nome</code> e <code>classificacao</code> passados como parâmetros.
	 * 
	 * @param nome  novo nome do espectáculo.
	 * @param classificacao nova classificação do espectáculo.
	 */
	public final void set(String nome, String classificacao) {
		_nome = nome;
		_classificacao = classificacao;
	}

	/**
	 * Retorna uma representação em {@link String} do objecto.
	 * 
	 * @return representação em {@link String} do objecto na forma: <code>nome [classificacao]</code>.
	 */
	@Override
	public final String toString() {
		return String.format("%s [%s]", _nome, _classificacao); 
	}
}
