package ezBilhetes.core;

import java.awt.Color;
import java.io.Serializable;

/**
 * 
 * Representa um estado de um {@link Lugar}. Tem um nome e uma cor associados.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 * 
 * @see ezBilhetes.core.Lugar
 * @see ezBilhetes.core.IEstado
 */
public final class Estado implements IEstado, Serializable {
	private static final long serialVersionUID = -1464237898131117819L;
	
	private Color _cor;
	private String _nome;
	
	/**
	 * Cria um novo estado com o nome e cor fornecidos.
	 * 
	 * @param nome nome do novo estado.
	 * @param cor cor do novo estado.
	 */
	public Estado(String nome, Color cor) {
		_cor = cor;
		_nome = nome;
	}
	
	/**
	 * Atribuí uma nova cor ao estado.
	 * 
	 * @param cor nova cor do estado.
	 */
	public final void setCor(Color cor) {
		_cor = cor;
	}
	
	@Override
	public final String getNome() {
		return _nome;
	}

	@Override
	public final Color getCor() {
		return _cor;
	}
	
	/**
	 * Compara dois estados de acordo com o seu nome e cor.
	 * 
	 * @param o objecto a comparar
	 * @return <code>true</code> caso o objecto a comparar seja do tipo <code>Estado</code> e tenha o mesmo nome e cor.
	 */
	@Override
	public final boolean equals(Object o) {
		if(o instanceof Estado) {
			Estado e = (Estado)o;
			return e.getNome().equals(_nome) && e.getCor().equals(_cor);
		}
		return false;
	}
}
