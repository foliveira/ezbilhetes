package ezBilhetes.core;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *  
 * Classe auxiliar para tratamento de datas.<br>
 * Permite converter um objecto do tipo {@link java.util.Date} numa representa��o, 
 * do tipo <i>dd/MM/yyyy</i> ou <i>hh:mm</i>.
 * 
 * @author F�bio Oliveira
 * @version 1.0
 * 
 * @see java.util.Date
 */
public abstract class Data {
	
	private static Calendar _calendario = new GregorianCalendar();
	
	/**
	 * Apresenta uma data na sua representa��o hor�ria, no formato <i>hh:mm</i>
	 * @return a representa��o hor�ria da data no formato <i>hh:mm</i>
	 */
	public static final String getHorasString(Date data) {
		_calendario.setTime(data);
		
		String minuto = String.valueOf(_calendario.get(Calendar.MINUTE));
		if(minuto.length() == 1) {
			minuto = "0" + minuto;
		}
		
		return _calendario.get(Calendar.HOUR_OF_DAY) + ":" + minuto;
	}
	
	/**
	 * Converte uma data para uma representa��o no formato <i>dd/MM/yyyy</i>
	 * @return a representa��o da data no formato <i>dd/MM/yyyy</i>
	 */
	public static final String getDataString(Date data) {
		_calendario.setTime(data);
		
		int dia = _calendario.get(Calendar.DAY_OF_MONTH);
		int mes = _calendario.get(Calendar.MONTH)+1;
		int ano = _calendario.get(Calendar.YEAR);
		return dia + "/" + mes + "/" + ano;
	}
}
