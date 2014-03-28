package ezBilhetes.ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ezBilhetes.core.Sala;
import ezBilhetes.ui.AppScreen;

/**
 * 
 * Painel para gestão de salas.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public final class SalasGest extends GestPanel {
	private static final long serialVersionUID = -4807150923772721855L;

	private Sala _salaActual;
	private JComboBox _salasCmb;
	private JTextField _nomeSala;
	private JTextField _colunasSala;
	private JTextField _filasSala;
	private JButton _addSala;
	private JButton _remSala;
	private JScrollPane _scrollSala;


	/**
	 * Cria um novo painel.
	 * 
	 * @param owner Janela mãe do painel
	 */
	public SalasGest(AdminScreen owner){

		super(owner);
	}

	/**
	 * Coloca o painel visível conforme o parâmetro <code>visible</code> e selecciona a
	 * primeira sala disponível.
	 * 
	 * @param visible se <code>true</code> coloca a janela visível.
	 */
	@Override
	public final void setVisible(boolean visible) {
		if(AppScreen.getSalas().size() > 0 && _salasCmb.getItemListeners().length > 0)
			_salasCmb.getItemListeners()[0].itemStateChanged(new ItemEvent(_salasCmb, 0, _salasCmb.getItemAt(0), ItemEvent.SELECTED));

		super.setVisible(visible);
	}

	@Override
	protected final void populate() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constrs = new GridBagConstraints();

		JPanel rightPanel = new JPanel(new GridLayout(3, 1));
		JPanel comboPanel = new JPanel(layout);
		JPanel infoPanel = new JPanel(layout);
		JPanel btnPanel = new JPanel(layout);

		_scrollSala = new JScrollPane();
		_salasCmb = new JComboBox(new DefaultComboBoxModel(AppScreen.getSalas()));
		_nomeSala = new JTextField(15);
		_colunasSala = new JTextField(10);
		_filasSala = new JTextField(10);
		_addSala = new JButton("Adicionar");
		_remSala = new JButton("Remover");

		_salasCmb.setPreferredSize(new Dimension(150, 25));

		comboPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Salas"));
		infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Informações"));
		btnPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Acções"));

		_addSala.addActionListener(new ActionListener() {
			static private final String POS_NUMBER = "^[+]?\\d+(\\.\\d+)?$";
			@Override
			public void actionPerformed(ActionEvent e) {
				if(_nomeSala.getText() != "" && _colunasSala.getText().matches(POS_NUMBER) && _filasSala.getText().matches(POS_NUMBER)) {
					Sala s = new Sala(_nomeSala.getText(), Integer.parseInt(_colunasSala.getText()), Integer.parseInt(_filasSala.getText()), AppScreen.getEstados());
					DefaultComboBoxModel dcmb = ((DefaultComboBoxModel)_salasCmb.getModel()); 

					if(dcmb.getIndexOf(s) == -1) {
						_nomeSala.setText(""); _colunasSala.setText(""); _filasSala.setText("");
						dcmb.addElement(s);
						_salasCmb.setSelectedItem(s);
					}
				} 
				else
					JOptionPane.showMessageDialog(null, "Verifique os parâmetros da sala", "Erro", JOptionPane.ERROR_MESSAGE);
			}			
		});
		_remSala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultComboBoxModel cbm = (DefaultComboBoxModel)_salasCmb.getModel();
				int index = _salasCmb.getSelectedIndex();

				if(index != -1)
					cbm.removeElementAt(index);
				else
					JOptionPane.showMessageDialog(SalasGest.this, "Nenhuma sala seleccionado", "Erro", JOptionPane.ERROR_MESSAGE);

				if(cbm.getSize() == 0) {
					_salaActual = null;
					_scrollSala.setViewportView(null);
				}
			}
		});
		_salasCmb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					_salaActual = (Sala)e.getItem();
					_salaActual.setAdminMode(true);
					_scrollSala.setViewportView(_salaActual);
				}
			}
		});

		constrs.gridwidth = GridBagConstraints.REMAINDER;
		constrs.insets.set(0, 5, 10, 5);
		layout.setConstraints(_nomeSala, constrs);
		constrs.insets.left = -35;
		layout.setConstraints(_colunasSala, constrs);
		layout.setConstraints(_filasSala, constrs);
		constrs.insets.set(-100, 0, 0, 0);
		layout.setConstraints(_salasCmb, constrs);
		constrs.insets.set(0, 0, 50, 0);
		layout.setConstraints(_addSala, constrs);

		comboPanel.add(_salasCmb);

		infoPanel.add(new JLabel("Nome:"));
		infoPanel.add(_nomeSala);
		infoPanel.add(new JLabel("Colunas:"));
		infoPanel.add(_colunasSala);
		infoPanel.add(new JLabel("Filas:"));
		infoPanel.add(_filasSala);

		btnPanel.add(_addSala);
		btnPanel.add(_remSala);

		rightPanel.add(comboPanel);
		rightPanel.add(infoPanel);
		rightPanel.add(btnPanel);

		_mainPanel.setLayout(new BorderLayout());
		_mainPanel.add(_scrollSala, BorderLayout.CENTER);
		_mainPanel.add(rightPanel, BorderLayout.EAST);
	}
}
