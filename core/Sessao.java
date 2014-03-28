package ezBilhetes.core;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Representação de uma sessão. Contém um espectáculo e hora de início e de fim, assim como os
 * lugares ocupados e preços praticados. Pode ou não permitir reservas.
 * 
 * @author Fábio Oliveira
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
	 * Cria uma sessão com o espectáculo, horas de início e fim, preços e indicação de reservas fornecidos.
	 * 
	 * @param e espectáculo associado à sessão.
	 * @param inicio hora de início da sessão.
	 * @param fim hora de fim da sessão.
	 * @param precos preços praticados na sessão.
	 * @param reservas indicação da existência de reservas.
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
	 * Indicação da possibilidade ou não da realização de reservas para a sessão.
	 * 
	 * @return <code>true</code> caso sejam permitidas reservas.
	 */
	public final boolean permiteReservas() {
		return _reservas;
	}
	
	/**
	 * Retorna a hora de início da sessão.
	 * 
	 * @return hora de início da sessão.
	 */
	public final Date getInicio() {
		return _horaInicio;
	}
	
	/**
	 * Retorna a hora de fim da sessão.
	 * @return hora de fim da sessão.
	 */
	public final Date getFim() {
		return _horaFim;
	}
	
	/**
	 * Retorna o espectáculo associado à sessão.
	 * 
	 * @return espectáculo associado à sessão.
	 */
	public final Espectaculo getEspectaculo() {
		return _espectaculo;
	}
	
	/**
	 * Retorna o estado dos lugares seleccionados na sessão. 
	 * 
	 * @return lista com os lugares seleccionadas na sessão.
	 */
	public final List<Lugar> getEstadoLugares() {
		return _lugaresOcupados;
	}
	
	/**
	 * Afecta o estado de um lugar.
	 * 
	 * @param e {@link IEstado} com o qual se deve afectar o lugar.
	 * @param indice índice do lugar a afectar.
	 */
	public final void setEstadoLugar(IEstado e, int indice) {
		Lugar l = _lugaresOcupados.get(indice);
		l.setEstado(e);
		_lugaresOcupados.set(indice, l);
	}
	
	/**
	 * Adicionar um lugar ocupado à sessão.
	 * 
	 * @param l lugar a adicionar.
	 */
	public final void addLugarOcupado(Lugar l) {
		_lugaresOcupados.add(l);
	}
	
	/**
	 * Adiciona um preço à sessão.
	 * 
	 * @param p preço à sessão.
	 */
	public final void addPreco(Preco p) {
		_precos.add(p);
	}
	
	/**
	 * Retorna uma lista de preços associados à sessão.
	 * 
	 * @return lista de preços associados à sessão.
	 */
	public final List<Preco> getPrecos() {
		return _precos;
	}
	
	/**
	 * Retorna uma representação da sessão em {@link String}
	 * 
	 * @return representação da sessão no formato: espectaculo: horaInicio-horaFim.
	 */
	@Override
	public final String toString() {
		return _espectaculo.toString() +": " + Data.getHorasString(_horaInicio) + "-" + Data.getHorasString(_horaFim); 
	}
}
