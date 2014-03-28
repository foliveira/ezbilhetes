package ezBilhetes.core;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Representa��o de uma sess�o. Cont�m um espect�culo e hora de in�cio e de fim, assim como os
 * lugares ocupados e pre�os praticados. Pode ou n�o permitir reservas.
 * 
 * @author F�bio Oliveira
 * @version 1.0
 *
 * @see ezBilhetes.core.Espectaculo
 * @see ezBilhetes.core.Preco
 * @see ezBilhetes.core.Lugar
 */
public final class Sessao implements Serializable {
	private static final long serialVersionUID = 679724123156598889L;
	
	private boolean _reservas;
	private Espectaculo _espectaculo;
	private Date _horaInicio;
	private Date _horaFim;
	private List<Lugar> _lugaresOcupados;
	private List<Preco> _precos;
	
	/**
	 * Cria uma sess�o com o espect�culo, horas de in�cio e fim, pre�os e indica��o de reservas fornecidos.
	 * 
	 * @param e espect�culo associado � sess�o.
	 * @param inicio hora de in�cio da sess�o.
	 * @param fim hora de fim da sess�o.
	 * @param precos pre�os praticados na sess�o.
	 * @param reservas indica��o da exist�ncia de reservas.
	 */
	public Sessao(Espectaculo e, Date inicio, Date fim, Preco[] precos, boolean reservas) {
		_reservas = reservas;
		_espectaculo = e;
		_horaInicio = inicio;
		_horaFim = fim;
		
		_lugaresOcupados = new LinkedList<Lugar>();
		_precos = Arrays.asList(precos);
	}
	
	/**
	 * Indica��o da possibilidade ou n�o da realiza��o de reservas para a sess�o.
	 * 
	 * @return <code>true</code> caso sejam permitidas reservas.
	 */
	public final boolean permiteReservas() {
		return _reservas;
	}
	
	/**
	 * Retorna a hora de in�cio da sess�o.
	 * 
	 * @return hora de in�cio da sess�o.
	 */
	public final Date getInicio() {
		return _horaInicio;
	}
	
	/**
	 * Retorna a hora de fim da sess�o.
	 * @return hora de fim da sess�o.
	 */
	public final Date getFim() {
		return _horaFim;
	}
	
	/**
	 * Retorna o espect�culo associado � sess�o.
	 * 
	 * @return espect�culo associado � sess�o.
	 */
	public final Espectaculo getEspectaculo() {
		return _espectaculo;
	}
	
	/**
	 * Retorna o estado dos lugares seleccionados na sess�o. 
	 * 
	 * @return lista com os lugares seleccionadas na sess�o.
	 */
	public final List<Lugar> getEstadoLugares() {
		return _lugaresOcupados;
	}
	
	/**
	 * Afecta o estado de um lugar.
	 * 
	 * @param e {@link IEstado} com o qual se deve afectar o lugar.
	 * @param indice �ndice do lugar a afectar.
	 */
	public final void setEstadoLugar(IEstado e, int indice) {
		Lugar l = _lugaresOcupados.get(indice);
		l.setEstado(e);
		_lugaresOcupados.set(indice, l);
	}
	
	/**
	 * Adicionar um lugar ocupado � sess�o.
	 * 
	 * @param l lugar a adicionar.
	 */
	public final void addLugarOcupado(Lugar l) {
		_lugaresOcupados.add(l);
	}
	
	/**
	 * Adiciona um pre�o � sess�o.
	 * 
	 * @param p pre�o � sess�o.
	 */
	public final void addPreco(Preco p) {
		_precos.add(p);
	}
	
	/**
	 * Retorna uma lista de pre�os associados � sess�o.
	 * 
	 * @return lista de pre�os associados � sess�o.
	 */
	public final List<Preco> getPrecos() {
		return _precos;
	}
	
	/**
	 * Retorna uma representa��o da sess�o em {@link String}
	 * 
	 * @return representa��o da sess�o no formato: espectaculo: horaInicio-horaFim.
	 */
	@Override
	public final String toString() {
		return _espectaculo.toString() +": " + Data.getHorasString(_horaInicio) + "-" + Data.getHorasString(_horaFim); 
	}
}
