package ezBilhetes.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * 
 * Representação de um lugar de uma sala. Contém um estado, podendo encontrar-se activo, ou não, e conhece
 * a sua posição. É também capaz de se desenhar dado um objecto do tipo {@link java.awt.Graphics}.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 * 
 * @see ezBilhetes.core.IEstado
 * @see ezBilhetes.core.Sala
 */
public final class Lugar implements Serializable {
	private static final long serialVersionUID = 7490562372204411072L;

	/**
	 * Tamanho associado a um lugar.
	 */
	public final static int SIZE = 10;
	
	private IEstado _estado;
	private Point _posicao;
	private boolean _activo;
	
	/**
	 * Cria um novo <code>Lugar</code> com a posição e {@link Estado} fornecido que se encontra activo.
	 * 
	 * @param fila número da fila a que o lugar pertence.
	 * @param coluna número da coluna a que o lugar pertence.
	 * @param estado {@link Estado} a atribuir ao lugar.
	 */
	public Lugar(int fila, int coluna, IEstado estado) {
		_posicao = new Point(fila, coluna);
		_activo = true;
		_estado = estado;
	}
	
	/**
	 * Cria um novo <code>Lugar</code> atribuindo-lhe o estado de outro lugar. O novo lugar encontra-se activo.
	 * 
	 * @param l lugar cujo estado será copiado.
	 */
	public Lugar(Lugar l) {
		this(l.getPosicao().x, l.getPosicao().y, l.getEstado());
	}
	
	/**
	 * Informa se o lugar se encontra activo
	 * 
	 * @return <code>true</code> caso o lugar se encontre activo.
	 */
	public final boolean isActivo() {
		return _activo;
	}
	
	/**
	 * Modifica a informação de actividade do lugar.
	 * 
	 * @param activo <code>true</code> para activar, <code>false</code> para desactivar o lugar.
	 */
	public final void setActivo(boolean activo) {
		_activo = activo;
	}
	
	/**
	 * Retorna a posição do lugar.
	 * 
	 * @return posição do lugar, encapsulada num objecto do tipo {@link java.awt.Point}.
	 */
	public final Point getPosicao() {
		return _posicao;
	}
	
	/**
	 * Muda o <code>IEstado</code> de um lugar.
	 * 
	 * @param e novo <code>IEstado</code> do lugar.
	 */
	public final void setEstado(IEstado e) {
		_estado = e;
	}
	
	/**
	 * Retorna o <code>IEstado</code> actual do objecto.
	 * 
	 * @return <code>IEstado</code> actual do lugar.
	 */
	public final IEstado getEstado() {
		return _estado;
	}
	
	/**
	 * Verifique se um ponto se encontra contido dentro dos limites do lugar.
	 * 
	 * @param p {@link java.awt.Point} a verificar se se encontra dentro do lugar.
	 * @return <code>true</code> caso o ponto se encontre dentro dos limites do lugar.
	 */
	public final boolean contains(Point p) {
		return (new Rectangle(_posicao.x * 2 * SIZE + SIZE, _posicao.y * 2 * SIZE + SIZE, SIZE, SIZE)).contains(p);
	}
	
	/**
	 * Desenha um estado de acordo com a sua disponibilidade, ou actividade.
	 * 
	 * @param g objecto do tipo {@link java.awt.Graphics} sobre o qual o lugar se desenha
	 * @see ezBilhetes.core.Lugar#isActivo()
	 */
	public final void drawDisponibilidadeLugares(Graphics g) {
		this.draw(g, true);
	}
	
	/**
	 * Desenha um lugar de acordo com o seu estado.
	 * 
	 * @param g objecto do tipo {@link java.awt.Graphics} sobre o qual o lugar se desenha
	 * @see ezBilhetes.core.Lugar#getEstado()
	 */
	public final void drawEstadoLugares(Graphics g) {
		this.draw(g, false);
	}
	
	/**
	 * Compara dois Lugares em relação à sua posição.
	 * 
	 * @param o o objecto a comparar
	 * @return <code>true</code> caso a posição dos dois objectos seja igual
	 */
	@Override
	public final boolean equals(Object o) {
		if(o instanceof Lugar) {
			Lugar l = (Lugar)o;
			return this._posicao.equals(l.getPosicao());
		}
		
		return false;
	}
	
	private final void draw(Graphics g, boolean drawActive) {
		if(drawActive)
			g.setColor(_activo ? Color.darkGray : Color.lightGray);
		else
			g.setColor(_estado.getCor());
		
		g.fill3DRect(_posicao.x * 2 * SIZE + SIZE, _posicao.y * 2 * SIZE + SIZE, SIZE, SIZE, true);
	}
}
