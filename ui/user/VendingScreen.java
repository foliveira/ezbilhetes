package ezBilhetes.ui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import ezBilhetes.core.Sala;
import ezBilhetes.core.Sessao;
import ezBilhetes.ui.AppScreen;

/**
 * 
 * Janela correspondente ao módulo operacional. Possibilita a venda de bilhetes
 * para as diversas sessões pertencentes às salas existentes.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 *
 * @see ezBilhetes.ui.AppScreen
 */
public final class VendingScreen extends AppScreen {
	private static final long serialVersionUID = 8532957633442956459L;
	
	private JComboBox _salasCmb;
	private JList _sessoesList;
	private JScrollPane _scrollSala;
	private JDateChooser _calendario;
	private JButton _btnVenda;

	private Sala _salaActual;
	private Date _dataActual;

	/**
	 * Cria uma nova janela de venda.
	 */
	public VendingScreen() {
		super("Posto de Venda - ezBilhetes");
	}
	
	/**
	 * Coloca a janela visível caso o parâmetro seja <code>true</code>
	 * 
	 * @param visible caso seja <code>true</code> coloca a janela visível.
	 */
	@Override
	public final void setVisible(boolean visible) {
		if(AppScreen.getSalas().size() > 0 && _salasCmb.getItemListeners().length > 0)
			_salasCmb.getItemListeners()[0].itemStateChanged(new ItemEvent(_salasCmb, 0, _salasCmb.getItemAt(0), ItemEvent.SELECTED));
		
		super.setVisible(visible);
	}

	@Override
	protected final void populate() {
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints gbConstr = new GridBagConstraints();
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		JPanel rightPanel = new JPanel(gridBag);
		JLabel salasLbl = new JLabel("Salas");
		JLabel dataLbl = new JLabel("Data");
		JLabel sessoesLbl = new JLabel("Sessões");
		JScrollPane scrollList = new JScrollPane();
		_scrollSala = new JScrollPane();
		_sessoesList = new JList();
		_salasCmb = new JComboBox(new DefaultComboBoxModel(getSalas()));
		_calendario = new JDateChooser(new Date());
		_dataActual = _calendario.getDate();
		_btnVenda = new JButton("Efectuar Venda");

		scrollList.setViewportView(_sessoesList);
		scrollList.setPreferredSize(new Dimension(300,350));
		_scrollSala.setPreferredSize(new Dimension(this.getSize().width - 450, this.getSize().height - 75));
		_salasCmb.setPreferredSize(new Dimension(300, 25));
		_calendario.setPreferredSize(new Dimension(150, 25));
		_btnVenda.setPreferredSize(new Dimension(200, 100));
		
		_btnVenda.setBorder(null);
		_btnVenda.setEnabled(false);
		_sessoesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Venda"));

		_salasCmb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					_salaActual = (Sala)e.getItem();
					_salaActual.setAdminMode(false);
					_salaActual.setSessaoActual(null);
					_salaActual.resetEstadoLugares();
					_btnVenda.setEnabled(false);
					
					if(_salaActual.hasSessoesDia(_dataActual))
						_sessoesList.setModel(_salaActual.getSessoesDia(_dataActual));
					
					_scrollSala.setViewportView(_salaActual);
				}
			}
		});
		_sessoesList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Sessao s = (Sessao)_sessoesList.getSelectedValue();
				boolean cond = (s != null);

				_salaActual.resetEstadoLugares();
				_salaActual.setSessaoActual(s);
				_btnVenda.setEnabled(cond);
				if(cond)
					_salaActual.setEstadoLugares(s.getEstadoLugares());
			}
		});
		_calendario.addPropertyChangeListener("date", new PropertyChangeListener() {
			ListModel lm = new DefaultListModel();
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				_dataActual = (Date)evt.getNewValue();

				if(_salaActual.hasSessoesDia(_dataActual))
					_sessoesList.setModel(_salaActual.getSessoesDia(_dataActual));
				else
					_sessoesList.setModel(lm);
				
				_btnVenda.setEnabled(false);
				_salaActual.setSessaoActual(null);
				_salaActual.resetEstadoLugares();
			}
		});

		_btnVenda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double total = _salaActual.efectuarVenda();
				if(total != 0.0) {
					int escolha = JOptionPane.showConfirmDialog(VendingScreen.this, 
							String.format("Total a pagar: %.2f€.\nEfectuar Venda?", total), 
							"Confirmar Venda", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.INFORMATION_MESSAGE);
					
					if(escolha == 1)
						_salaActual.anularUltimaVenda();

					repaint();
				} 
				else
					JOptionPane.showMessageDialog(VendingScreen.this, "Não seleccionou bilhetes para compra", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		gbConstr.gridwidth=GridBagConstraints.REMAINDER;
		gbConstr.insets.set(20, 50, 0, 50);
		gridBag.setConstraints(salasLbl, gbConstr);
		gbConstr.insets.set(0, 50, 35, 50);
		gridBag.setConstraints(_salasCmb, gbConstr);

		gbConstr.insets.set(25, 50, 0, 50);
		gridBag.setConstraints(dataLbl, gbConstr);
		gbConstr.insets.set(0, 50, 10, 50);
		gridBag.setConstraints(_calendario, gbConstr);

		gbConstr.insets.set(10, 50, 0, 50);
		gridBag.setConstraints(sessoesLbl, gbConstr);
		gbConstr.insets.set(0, 50, 10, 50);
		gridBag.setConstraints(scrollList, gbConstr);
		gbConstr.insets.set(25, 0, 27, 0);
		gridBag.setConstraints(_btnVenda, gbConstr);

		rightPanel.add(salasLbl);
		rightPanel.add(_salasCmb);
		rightPanel.add(dataLbl);
		rightPanel.add(_calendario);
		rightPanel.add(sessoesLbl);
		rightPanel.add(scrollList);
		rightPanel.add(_btnVenda);

		this.add(_scrollSala);
		this.add(rightPanel);
	}	
}
