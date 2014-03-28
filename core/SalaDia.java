package ezBilhetes.core;

import java.io.Serializable;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 * 
 * Representa uma sala para um determinado dia. Cont�m todas as sess�es associadas para uma data.
 * 
 * @author F�bio Oliveira
 * @version 1.0
 */
public final class SalaDia implements Serializable {
	private static final long serialVersionUID = 7157803534281121181L;
	
	private Date _data;
	private ListModel _sessoesDia;
	
	/**
	 * Cria um novo objecto <code>SalaDia</code> para a data fornecida.
	 * 
	 * @param data data associada � sala.
	 */
	public SalaDia(Date data) {
		_data = data;
		_sessoesDia = new DefaultListModel();
	}
	
	/**
	 * Retorna a data associada� sala.
	 * 
	 * @return a data associada.
	 */
	public final Date getData() {
		return _data;
	}
	
	/**
	 * Retorna as sess�es associadas � sala na data associada.
	 * 
	 * @return <code>ListModel</code> com todas as sess�es para a data associada.
	 */
	public final ListModel getSessoes() {
		return _sessoesDia;
	}
}
