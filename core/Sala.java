package ezBilhetes.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JComponent;
import javax.swing.ListModel;

/**
 * 
 * Representa uma sala. Contêm os lugares presentes na mesma, assim como um nome, estados associados
 * e conjunto de salas dia, que contêm as sessões para um certo dia.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 *
 * @see ezBilhetes.core.Lugar
 * @see ezBilhetes.core.Sessao
 * @see ezBilhetes.core.SalaDia
 * @see ezBilhetes.core.Estado
 */
public final class Sala extends JComponent {
	private static final long serialVersionUID = -7354550164504492544L;
	
	private boolean _adminMode;
	private String _nome;
	private Lugar[][] _lugares;
	private Sessao _sessaoActual;
	private List<SalaDia> _salasDia;
	private List<Estado> _estados;
	private List<Lugar> _vendas;

	/**
	 * Cria uma nova sala contendo um nome, número de filas e colunas e estados associados.
	 * 
	 * @param nome nome da sala.
	 * @param filas número de filas de lugares na sala.
	 * @param colunas número de colunas de lugares na sala.
	 * @param estados estados associados à sala.
	 */
	public Sala(String nome, int filas, int colunas, List<Estado> estados) {
		_nome = nome;
		_estados = estados;
		
		_lugares = new Lugar[filas][colunas];
		_salasDia = new LinkedList<SalaDia>();
		_vendas = new LinkedList<Lugar>();

		this.setPreferredSize(new Dimension(filas * 2 * Lugar.SIZE + 2*Lugar.SIZE, colunas*2*Lugar.SIZE + 5*Lugar.SIZE));
		this.addMouseListener(new LugaresListner());
		
		this.populate();
	}

	/**
	 * Coloca a sala em modo administrativo.
	 * 
	 * @param mode <code>true</code> para colocar a sala em modo administrativo.
	 */
	public final void setAdminMode(boolean mode) {
		_adminMode = mode;
	}

	/**
	 * Informa se a sala se encontra em modo administrativo.
	 * 
	 * @return <code>true</code> se a sala está em modo administrativo.
	 */
	public final boolean isAdminMode() {
		return _adminMode;
	}

	/**
	 * Adiciona uma nova sala dia à sala.
	 * 
	 * @param sd sala dia a adicionar à sala.
	 */
	public final void addSalaDia(SalaDia sd) {
		_salasDia.add(sd);
	}

	/**
	 * Retorna todas as salas dia presentes na sala.
	 * 
	 * @return lista de salas dia da sala.
	 */
	public final List<SalaDia> getSalasDia() {
		return _salasDia;
	}
	
	/**
	 * Retorna os estados associados à sala.
	 * 
	 * @return lista de estados associados.
	 */
	public final List<Estado> getEstados() {
		return _estados;
	}

	/**
	 * Verifica se existem sessões para um determinado dia.
	 * 
	 * @param dia objecto {@link java.util.Date} representativo do dia a consultar.
	 * @return <code>true</code> caso existam sessões para o dia fornecido..
	 */
	public final boolean hasSessoesDia(Date dia) {
		return (getSessoesDia(dia) != null);
	}
	
	/**
	 * Retorna sessões para uma determinado dia.
	 * 
	 * @param dia objecto {@link java.util.Date} representativo do dia a consultar.
	 * @return {@link javax.swing.ListModel} contendo todas as sessões para o dia fornecido ou
	 * <code>null</code> caso não existam sessões.
	 */
	public final ListModel getSessoesDia(Date dia) {		
		for(SalaDia sd : _salasDia) {
			if(Data.getDataString(dia).equals(Data.getDataString(sd.getData()))) {
				return sd.getSessoes();
			}
		}

		return null;
	}

	/**
	 * Altera o estado dos lugares da sala pelo estado dos lugares fornecidos.
	 * 
	 * @param lugares lista de lugares com estado a atribuir aos lugares da sala.
	 */
	public final void setEstadoLugares(List<Lugar> lugares) {
		for(Lugar l : lugares) {
			Point p = l.getPosicao();
			_lugares[p.x][p.y].setEstado(l.getEstado());
		}
	}

	/**
	 * Coloca todos os lugares da sala no estado <i>Livre</i>.
	 */
	public final void resetEstadoLugares() {
		for(Lugar[] la : _lugares) {
			for(Lugar l : la) {
				l.setEstado(_estados.get(0));
			}
		}
		repaint();
	}

	/**
	 * Coloca como sessão actual a sessão fornecida.
	 * 
	 * @param s nova sessão actual.
	 */
	public final void setSessaoActual(Sessao s) {
		_sessaoActual = s;
	}
	
	/**
	 * Retorna a sessão actual da sala.
	 * 
	 * @return a sessão actual da sala.
	 */
	public final Sessao getSessaoActual() {
		return _sessaoActual;
	}

	/**
	 * Efectua uma venda de bilhetes para a sessão actual.
	 * Percorre todos os lugares que foram seleccionados e calcula o valor total.
	 * 
	 * @return valor total da venda efectuada.
	 */
	public final double efectuarVenda() {
		double total = 0.0;
		List<Lugar> lugares = _sessaoActual.getEstadoLugares();
		_vendas.clear();
		
		for(Lugar l : lugares) {
			if(l.getEstado() instanceof Preco) {
				Preco p = (Preco)l.getEstado();
				total += p.getValor();
				_vendas.add(new Lugar(l));
				_lugares[l.getPosicao().x][l.getPosicao().y].setEstado(_estados.get(_estados.size()-1));
				l.setEstado(_estados.get(_estados.size() - 1));
			}
		}
		
		return total;
	}
	
	/**
	 * Anula a última venda, colocando todos os lugares vendidos no estado livre.
	 */
	public final void anularUltimaVenda() {
		List<Lugar> lugares = _sessaoActual.getEstadoLugares();
		
		for(Lugar l : _vendas) {
			_lugares[l.getPosicao().x][l.getPosicao().y].setEstado(_estados.get(0));
			lugares.remove(l);
		}
		_vendas.clear();
	}
	
	/**
	 * Compara duas salas de acordo com o seu nome.
	 * 
	 * @return <code>true</code> caso as duas salas tenham o mesmo nome.
	 */
	@Override
	public final boolean equals(Object o) {
		if(o instanceof Sala) {
			Sala s = (Sala)o;
			return s.toString().equals(_nome);
		}
		return false;
	}

	/**
	 * Desenha uma sala desenhando todos os seus lugares. 
	 * 
	 * @param g objecto do tipo {@link java.awt.Graphics} sobre o qual o lugar se desenha
	 */
	@Override
	public final void paintComponent(Graphics g) {
		for(int i = 0; i < _lugares.length; ++i) {
			for(int j = 0; j < _lugares[0].length; ++j) {
				if(_adminMode)
					_lugares[i][j].drawDisponibilidadeLugares(g);
				else {
					if(_lugares[i][j].isActivo())
						_lugares[i][j].drawEstadoLugares(g);
				}
			}
		}

		g.setColor(Color.black);
		g.drawLine(Lugar.SIZE/2,  _lugares[0].length*2*Lugar.SIZE +Lugar.SIZE, 
				_lugares.length*2*Lugar.SIZE, _lugares[0].length*2*Lugar.SIZE +Lugar.SIZE);		
		g.drawString("Frente",(_lugares.length*2*Lugar.SIZE - 3*Lugar.SIZE)/2, _lugares[0].length*2*Lugar.SIZE + 3*Lugar.SIZE);
	}

	/**
	 * Retorna o nome da sala.
	 * 
	 * @return o nome da sala.
	 */
	@Override
	public final String toString() {
		return _nome;
	}

	private final void populate() {
		for(int i = 0; i < _lugares.length; ++i) {
			for(int j = 0; j < _lugares[0].length; ++j) {
				_lugares[i][j] = new Lugar(i, j, _estados.get(0));
			}
		}
	}

	private class LugaresListner extends MouseAdapter implements Serializable {
		private static final long serialVersionUID = -3823211543936371183L;
		@Override
		public void mouseClicked(MouseEvent e) {
			if(!Sala.this._adminMode && (Sala.this._sessaoActual == null))
				return;

			int x = (e.getX() - Lugar.SIZE)/(2*Lugar.SIZE);
			int y = (e.getY() - Lugar.SIZE)/(2*Lugar.SIZE);
			Lugar lugar = _lugares[x][y];

			if(lugar.contains(e.getPoint())) {
				if(Sala.this._adminMode) {
					_lugares[x][y].setActivo(!lugar.isActivo());
				} else {
					if(!lugar.isActivo() || lugar.getEstado().equals(_estados.get(_estados.size()-1)))
						return;
					
					int mod = e.getModifiers();
					
					if(mod == InputEvent.BUTTON1_MASK) {
						ListIterator<Preco> it = _sessaoActual.getPrecos().listIterator();
						while(it.hasNext() && !it.next().equals(lugar.getEstado()));
						_lugares[x][y].setEstado(it.hasNext() ? it.next() : _sessaoActual.getPrecos().listIterator().next());
					} else if(mod == InputEvent.BUTTON2_MASK || mod == InputEvent.BUTTON3_MASK) {
						ListIterator<Estado> it = _estados.subList(0, _estados.size() - (_sessaoActual.permiteReservas() ? 1 : 2)).listIterator();
						while(it.hasNext() && !it.next().equals(lugar.getEstado()));
						_lugares[x][y].setEstado(it.hasNext() ? it.next() : _estados.listIterator().next());
					}

					if(_sessaoActual.getEstadoLugares().contains(_lugares[x][y])) {
						int i = _sessaoActual.getEstadoLugares().indexOf(_lugares[x][y]);
						_sessaoActual.setEstadoLugar(_lugares[x][y].getEstado(), i);
					}
					else
						_sessaoActual.addLugarOcupado(new Lugar(_lugares[x][y]));
				}
				Sala.this.repaint();
			}
		}
	}
}
