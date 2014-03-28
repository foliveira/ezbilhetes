package ezBilhetes.core;

import java.io.Serializable;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 * 
 * Representa uma sala para um determinado dia. Contém todas as sessões associadas para uma data.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public final class SalaDia implements Serializable {
	private static final long serialVersionUID = 7157803534281121181L;
	
	private Date _data;
	private ListModel _sessoesDia;
	
	/**
	 * Cria um novo objecto <code>SalaDia</code> para a data fornecida.
	 * 
	 * @param data data associada à sala.
	 */
	public SalaDia(Date data) {
		_data = data;
		_sessoesDia = new DefaultListModel();
	}
	
	/**
	 * Retorna a data associadaà sala.
	 * 
	 * @return a data associada.
	 */
	public final Date getData() {
		return _data;
	}
	
	/**
	 * Retorna as sessões associadas à sala na data associada.
	 * 
	 * @return <code>ListModel</code> com todas as sessões para a data associada.
	 */
	public final ListModel getSessoes() {
		return _sessoesDia;
	}
}
