package ezBilhetes.ui.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ezBilhetes.core.Espectaculo;
import ezBilhetes.ui.AppScreen;

/**
 * 
 * Painel para gestão de espectáculos.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public final class EspectaculosGest extends GestPanel {
	private static final long serialVersionUID = -7436289179923705790L;
	
	private JList _espectaculosList;
	private JTextField _nome;
	private JTextField _classificacao;
	private JButton _addBtn;
	private JButton _remBtn;
	private JButton _modBtn;
	
	/**
	 * Cria um novo painel.
	 * 
	 * @param owner Janela mãe do painel
	 */
	public EspectaculosGest(AdminScreen owner) {
		super(owner);
	}
	
	@Override
	protected final void populate() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constrs = new GridBagConstraints();
		
		JPanel espectaculosPanel = new JPanel(new FlowLayout());
		JPanel editPanel = new JPanel(layout);
		
		JScrollPane scrollEsp = new JScrollPane();
		_espectaculosList = new JList(AppScreen.getEspectaculos());
		_nome = new JTextField(25);
		_classificacao = new JTextField(6);
		_addBtn = new JButton("Adicionar");
		_remBtn = new JButton("Remover");
		_modBtn = new JButton("Modificar");
		
		scrollEsp.setViewportView(_espectaculosList);
		scrollEsp.setPreferredSize(new Dimension(300, 500));
		_espectaculosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		espectaculosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Espectáculos"));
		editPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Edição de Espectáculos"));
		
		constrs.gridwidth = GridBagConstraints.REMAINDER;
		constrs.insets.set(0,-25, 10, 0);
		layout.setConstraints(_nome, constrs);
		constrs.insets.set(0, -177, 10, 0);
		layout.setConstraints(_classificacao, constrs);
		constrs.insets.set(20, 15, 20, 15);
		constrs.gridwidth = GridBagConstraints.BOTH;
		layout.setConstraints(_addBtn, constrs);
		layout.setConstraints(_remBtn, constrs);
		layout.setConstraints(_modBtn, constrs);
		
		_espectaculosList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Espectaculo es = (Espectaculo)_espectaculosList.getSelectedValue();
				
				_nome.setText(es.getNome()); 
				_classificacao.setText(es.getClassificacao());
			}
		});
		_addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = _nome.getText();
				String classificacao = _classificacao.getText();
				
				if(nome.equals("") || classificacao.equals("")) {
					JOptionPane.showMessageDialog(EspectaculosGest.this, "Verifique os parâmetros do espectáculo", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
					
				Espectaculo es = new Espectaculo(nome, classificacao);
				DefaultListModel dlm = (DefaultListModel)_espectaculosList.getModel();
				_nome.setText(""); _classificacao.setText("");
				dlm.addElement(es);
			}
		});
		_remBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel dlm = (DefaultListModel)_espectaculosList.getModel();
				int index = _espectaculosList.getSelectedIndex();
		
				if(index != -1)
					dlm.removeElementAt(index);
				else
					JOptionPane.showMessageDialog(EspectaculosGest.this, "Nenhum espectáculo seleccionado", "Erro", JOptionPane.ERROR_MESSAGE);
				
				_nome.setText(""); _classificacao.setText("");
			}
		});
		_modBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel dlm = (DefaultListModel)_espectaculosList.getModel();
				int index = _espectaculosList.getSelectedIndex();
				String nome = _nome.getText();
				String classificacao = _classificacao.getText();
				
				if(index != -1) {
					if(nome.equals("") || classificacao.equals("")) {
						JOptionPane.showMessageDialog(EspectaculosGest.this, "Verifique os parâmetros do espectáculo", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					Espectaculo es = (Espectaculo)dlm.get(index);
					es.set(nome, classificacao);
					dlm.setElementAt(es, index);
				}
				else
					JOptionPane.showMessageDialog(EspectaculosGest.this, "Nenhum espectáculo seleccionado", "Erro", JOptionPane.ERROR_MESSAGE);
			}			
		});
		
		editPanel.add(new JLabel("Nome:"));
		editPanel.add(_nome);
		editPanel.add(new JLabel("Classificação:"));
		editPanel.add(_classificacao);
		editPanel.add(_addBtn);
		editPanel.add(_remBtn);
		editPanel.add(_modBtn);
		
		espectaculosPanel.add(scrollEsp);
		espectaculosPanel.add(editPanel);
		
		_mainPanel.add(espectaculosPanel);
	}
}
