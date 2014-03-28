package ezBilhetes.core;

import java.io.Serializable;

/**
 * 
 * Classe representativa de um espect�culo. Cont�m o nome e classifica��o atribu�da a um espect�culo.
 * 
 * @author F�bio Oliveira
 * @version 1.0
 */
public final class Espectaculo implements Serializable {
	private static final long serialVersionUID = -3933856061362622930L;
	
	private String _nome;
	private String _classificacao;
	
	/**
	 * Cria um novo espect�culo com o <code>nome</code> e <code>classificacao</code> fornecidos.
	 * 
	 * @param nome nome do espect�culo a criar.
	 * @param classificacao classifica��o atribu�da ao espect�culo.
	 */
	public Espectaculo(String nome, String classificacao) {
		set(nome, classificacao);
	}
	
	/**
	 * Retorna o nome do espect�culo.
	 * 
	 * @return o nome do espect�culo.
	 */
	public final String getNome() {
		return _nome;
	}
	
	/**
	 * Retorna a classifica��o do espect�culo.
	 * 
	 * @return a classifica��o do espect�culo.
	 */
	public final String getClassificacao() {
		return _classificacao;
	}
	
	/**
	 * Modifica um espect�culo com o <code>nome</code> e <code>classificacao</code> passados como par�metros.
	 * 
	 * @param nome  novo nome do espect�culo.
	 * @param classificacao nova classifica��o do espect�culo.
	 */
	public final void set(String nome, String classificacao) {
		_nome = nome;
		_classificacao = classificacao;
	}

	/**
	 * Retorna uma representa��o em {@link String} do objecto.
	 * 
	 * @return representa��o em {@link String} do objecto na forma: <code>nome [classificacao]</code>.
	 */
	@Override
	public final String toString() {
		return String.format("%s [%s]", _nome, _classificacao); 
	}
}
