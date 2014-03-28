package ezBilhetes.core;

import java.awt.Color;
import java.io.Serializable;

/**
 * 
 * Interface para definição de estados associados a lugares. Deste modo cada estado terá que conter no mínimo
 * uma nome e uma cor associados para poder ser atribuído a um {@link Lugar}.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public interface IEstado extends Serializable {
	/**
	 * Retorna o nome associado ao objecto que implementa <code>IEstado</code>.
	 * 
	 * @return o nome associado ao obejcto.
	 */
	String getNome();
	
	/**
	 * Retorna a cor associada ao objecto que implementa <code>IEstado</code>.
	 * 
	 * @return a cor associada ao objecto.
	 */
	Color getCor();
}
