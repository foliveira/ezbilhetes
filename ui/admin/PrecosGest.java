package ezBilhetes.ui.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ezBilhetes.core.Estado;
import ezBilhetes.core.Preco;
import ezBilhetes.ui.AppScreen;

/**
 * 
 * Painel para gestão de preços.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public final class PrecosGest extends GestPanel {
	private static final long serialVersionUID = 5064340044371760253L;
	
	private JList _precosList;
	private JTextField _nomePreco;
	private JTextField _valorPreco;
	private JLabel _corPreco;
	private JButton _addBtn;
	private JButton _remBtn;
	private JButton _modBtn;
	private JLabel _corLivre;
	private JLabel _corReservado;
	private JLabel _corOcupado;
	
	private Dictionary<JLabel, Estado> _estados;

	/**
	 * Cria um novo painel.
	 * 
	 * @param owner Janela mãe do painel
	 */
	public PrecosGest(AdminScreen owner) {
		super(owner);
	}

	@Override
	protected final void populate() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constrs = new GridBagConstraints();
		Dimension corDim = new Dimension(20, 20);
		
		JPanel panelPrecos = new JPanel(new FlowLayout());
		JPanel precosEdit = new JPanel(layout);
		JPanel precosInfo = new JPanel();
		JPanel panelEstados = new JPanel(layout);
		
		JScrollPane precosScroll = new JScrollPane();
		_precosList = new JList(AppScreen.getPrecos());
		_nomePreco = new JTextField(20);
		_valorPreco = new JTextField(6);
		_corPreco = new JLabel();
		_addBtn = new JButton("Adicionar");
		_remBtn = new JButton("Remover");
		_modBtn = new JButton("Modificar");
		_corLivre = new JLabel();
		_corReservado = new JLabel();
		_corOcupado = new JLabel();
		
		_estados = new Hashtable<JLabel, Estado>();
		List<Estado> estds = AppScreen.getEstados();
		_estados.put(_corLivre, estds.get(0));
		_estados.put(_corReservado, estds.get(1));
		_estados.put(_corOcupado, estds.get(2));
		
		precosScroll.setViewportView(_precosList);
		precosScroll.setPreferredSize(new Dimension(200, 500));
		_corPreco.setPreferredSize(corDim);
		_corLivre.setPreferredSize(corDim);
		_corReservado.setPreferredSize(corDim);
		_corOcupado.setPreferredSize(corDim);
		
		_corPreco.setOpaque(true);
		_corLivre.setOpaque(true);
		_corReservado.setOpaque(true);
		_corOcupado.setOpaque(true);
		_corPreco.setBackground(Color.lightGray);
		_corLivre.setBackground(_estados.get(_corLivre).getCor());
		_corReservado.setBackground(_estados.get(_corReservado).getCor());
		_corOcupado.setBackground(_estados.get(_corOcupado).getCor());
		_precosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		_corPreco.setBorder(BorderFactory.createLineBorder(Color.black));
		_corLivre.setBorder(BorderFactory.createLineBorder(Color.black));
		_corReservado.setBorder(BorderFactory.createLineBorder(Color.black));
		_corOcupado.setBorder(BorderFactory.createLineBorder(Color.black));
		precosEdit.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Edição de Preços"));
		panelPrecos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Preços"));
		panelEstados.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Estados"));
		
		_precosList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Preco preco = (Preco)_precosList.getSelectedValue();
				
				_nomePreco.setText(preco.getNome());
				_valorPreco.setText(String.valueOf(preco.getValor()));
				_corPreco.setBackground(preco.getCor());
			}			
		});
		
		_addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = _nomePreco.getText();
				String valor = _valorPreco.getText();
				
				if(nome.equals("") || valor.equals("") || !valor.matches("^[+]?\\d+(\\.\\d+)?$")) {
					JOptionPane.showMessageDialog(PrecosGest.this, "Verifique os parâmetros do preço", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Preco p = new Preco(nome, _corPreco.getBackground(), Double.parseDouble(valor));
				DefaultListModel dlm = (DefaultListModel)_precosList.getModel();
				_nomePreco.setText(""); _valorPreco.setText(""); _corPreco.setBackground(Color.lightGray);
				dlm.addElement(p);
			}
		});	
		
		_remBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel dlm = (DefaultListModel)_precosList.getModel();
				int index = _precosList.getSelectedIndex();
				
				if(index != -1)
					dlm.removeElementAt(index);
				else
					JOptionPane.showMessageDialog(PrecosGest.this, "Nenhum preço seleccionado", "Erro", JOptionPane.ERROR_MESSAGE);
				
				_nomePreco.setText(""); _valorPreco.setText(""); _corPreco.setBackground(Color.lightGray);
			}
		});
		
		_modBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel dlm = (DefaultListModel)_precosList.getModel();
				int index = _precosList.getSelectedIndex();
				String nome = _nomePreco.getText();
				String valor = _valorPreco.getText();
				
				if(index != -1) {
					if(nome.equals("") || valor.equals("") || !valor.matches("^[+]?\\d+(\\.\\d+)?$")) {
						JOptionPane.showMessageDialog(PrecosGest.this, "Verifique os parâmetros do preço", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					Preco p = (Preco)dlm.get(index);
					p.set(nome, _corPreco.getBackground(), Double.parseDouble(valor));
					dlm.setElementAt(p, index);
				}
				else
					JOptionPane.showMessageDialog(PrecosGest.this, "Nenhum preço seleccionado", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		_corPreco.addMouseListener(new EditCoresListner());
		_corLivre.addMouseListener(new EditCoresListner());
		_corReservado.addMouseListener(new EditCoresListner());
		_corOcupado.addMouseListener(new EditCoresListner());
		
		constrs.gridwidth = GridBagConstraints.REMAINDER;
		constrs.insets.set(0, -88, 5, 0);
		layout.setConstraints(_nomePreco, constrs);
		constrs.insets.left = -200;
		layout.setConstraints(_valorPreco, constrs);
		constrs.insets.left = -233;
		layout.setConstraints(_corPreco, constrs);
		
		constrs.gridwidth = GridBagConstraints.BOTH;
		constrs.insets.set(20, 10, 10, 10);
		layout.setConstraints(_addBtn, constrs);
		layout.setConstraints(_remBtn, constrs);
		layout.setConstraints(_modBtn, constrs);
		
		constrs.insets.set(0, 5, 5, 10);
		layout.setConstraints(_corLivre, constrs);
		layout.setConstraints(_corReservado, constrs);
		layout.setConstraints(_corOcupado, constrs);
		
		precosEdit.add(new JLabel("Nome:"));
		precosEdit.add(_nomePreco);
		precosEdit.add(new JLabel("Valor:"));
		precosEdit.add(_valorPreco);
		precosEdit.add(new JLabel("Cor:"));
		precosEdit.add(_corPreco);
		precosEdit.add(_addBtn);
		precosEdit.add(_remBtn);
		precosEdit.add(_modBtn);
		
		precosInfo.add(precosScroll);
		
		panelEstados.add(new JLabel("Livre:"));
		panelEstados.add(_corLivre);
		panelEstados.add(new JLabel("Reservado:"));
		panelEstados.add(_corReservado);
		panelEstados.add(new JLabel("Ocupado:"));
		panelEstados.add(_corOcupado);
		
		panelPrecos.add(precosInfo);
		panelPrecos.add(precosEdit);
		
		_mainPanel.add(panelPrecos);
		_mainPanel.add(panelEstados);
	}
	
	private class EditCoresListner extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel target = (JLabel)e.getSource();
			Color cor = null;
			
			if(!_corPreco.equals(target) && !_corLivre.equals(target) && !_corReservado.equals(target) && !_corOcupado.equals(target))
				return;
			
			cor = JColorChooser.showDialog(PrecosGest.this, "Escolha de cor.", target.getBackground());
			if(cor == null)
				JOptionPane.showMessageDialog(PrecosGest.this, "Não seleccionou uma cor nova.", "Erro", JOptionPane.ERROR_MESSAGE);
			else {
				target.setBackground(cor);
				Estado es = _estados.get(target);
				if(es != null)
					es.setCor(cor);
			}
		}
	}
}
