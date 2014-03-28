package ezBilhetes.core;

import java.awt.Color;
import java.io.Serializable;

/**
 * 
 * Interface para defini��o de estados associados a lugares. Deste modo cada estado ter� que conter no m�nimo
 * uma nome e uma cor associados para poder ser atribu�do a um {@link Lugar}.
 * 
 * @author F�bio Oliveira
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
