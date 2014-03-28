package ezBilhetes.core;

import java.awt.Color;
import java.io.Serializable;

/**
 * 
 * Representa um preços associado a um {@link Lugar}. Além de um nome e uma cor, tem um valor associado.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 * 
 * @see ezBilhetes.core.Lugar
 * @see ezBilhetes.core.IEstado
 */
public final class Preco implements IEstado, Serializable {
	private static final long serialVersionUID = 8824611058339547059L;
	
	private Color _cor;
	private String _nome;
	private double _valor;
	
	/**
	 * Cria um novo preço com um nome, cor e valor fornecidos.
	 * 
	 * @param nome nome do novo preço.
	 * @param cor cor do novo preço.
	 * @param valor valor do novo preço.
	 */
	public Preco(String nome, Color cor, double valor) {
		set(nome, cor, valor);
	}

	/**
	 * Afecta o preço com um novo nome, cor e valor.
	 * 
	 * @param nome novo nome do preço.
	 * @param cor nova cor a atribuir.
	 * @param valor novo valor do preço.
	 */
	public final void set(String nome, Color cor, double valor) {
		_nome = nome;
		_cor = cor;
		_valor = valor;
	}
	
	/**
	 * Retorna o valor do preço.
	 * 
	 * @return o valor do preço.
	 */
	public final double getValor() {
		return _valor;
	}
	
	@Override
	public final Color getCor() { 
		return _cor;
	}

	@Override
	public final String getNome() {
		return _nome;
	}

	/**
	 * Compara dois preços de acordo com o nome, cor e valor.
	 * 
	 * @param o objecto a comparar
	 * @return <code>true</code> caso o objecto a comparar seja do tipo <code>Preco</code> e tenha o mesmo nome, cor e valor.
	 */
	@Override
	public final boolean equals(Object o) {
		if(o instanceof Preco) {
			Preco p = (Preco)o;
			return p.getNome().equals(_nome) && p.getCor().equals(_cor) && p.getValor() == _valor;
		}
		return false;
	}
	
	/**
	 * Retorna a representação em {@link String} do  preço.
	 * 
	 * @return a representação do preço no formato: <code>nome [valor]</code>
	 */
	@Override
	public final String toString() {
		return String.format("%s [%.2f€]", _nome, _valor);
	}
}
