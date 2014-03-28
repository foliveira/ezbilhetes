package ezBilhetes.ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JSpinnerDateEditor;

import ezBilhetes.core.Espectaculo;
import ezBilhetes.core.Preco;
import ezBilhetes.core.Sala;
import ezBilhetes.core.SalaDia;
import ezBilhetes.core.Sessao;
import ezBilhetes.ui.AppScreen;

/**
 * 
 * Painel para gestão de sessões.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public final class SessoesGest extends GestPanel {
	private static final long serialVersionUID = 1684434652818308706L;
	
	private JComboBox _salasCmb;
	private JCalendar _calendario;
	private JList _espectaculosList;
	private JList _precosList;
	private JSpinnerDateEditor _horaInicio;
	private JSpinnerDateEditor _horaFim;
	private JCheckBox _reservasChk;
	private JList _sessoesList;
	private JButton _addBtn;
	private JButton _remBtn;
	
	private Sala _salaActual;
	private Date _dataActual;
	private ListModel lm = new DefaultListModel();
	
	/**
	 * Cria um novo painel.
	 * 
	 * @param owner Janela mãe do painel
	 */
	public SessoesGest(AdminScreen owner) {
		super(owner);
	}
	
	/**
	 * Coloca o painel visível conforme o valor do parâmetro e actualiza a lista de 
	 * espectáculos e preços bem como a caixa de selecção de salas.
	 * 
	 * @param visible
	 */
	@Override
	public final void setVisible(boolean visible) {		
		_salasCmb.setModel(new DefaultComboBoxModel(AppScreen.getSalas()));
		_espectaculosList.setModel(AppScreen.getEspectaculos());
		_precosList.setModel(AppScreen.getPrecos());
		
		if(AppScreen.getSalas().size() > 0 && _salasCmb.getItemListeners().length > 0)
			_salasCmb.getItemListeners()[0].itemStateChanged(new ItemEvent(_salasCmb, 0, _salasCmb.getItemAt(0), ItemEvent.SELECTED));
		
		super.setVisible(visible);
	}

	@Override
	protected final void populate() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		JPanel sessoesPanel = new JPanel(new BorderLayout());
		JPanel leftPanel = new JPanel(layout);
		JPanel centerPanel = new JPanel(layout);
		JPanel rightPanel = new JPanel(layout);
		
		JLabel salasLbl = new JLabel("Salas");
		JLabel calendarioLbl = new JLabel("Calendário");
		JLabel espectaculosLbl = new JLabel("Espectáculos");
		JLabel precosLbl = new JLabel("Preços");
		JLabel inicioLbl = new JLabel("Hora Início");
		JLabel fimLbl = new JLabel("Hora Fim");
		JLabel sessoesLbl = new JLabel("Sessões");
		
		JScrollPane espectaculosScroll = new JScrollPane();
		JScrollPane precosScroll = new JScrollPane();
		JScrollPane sessoesScroll = new JScrollPane();
		
		_salasCmb = new JComboBox();
		_calendario = new JCalendar(new Date());
		_espectaculosList = new JList();
		_precosList = new JList();
		_horaInicio = new JSpinnerDateEditor();
		_horaFim = new JSpinnerDateEditor();
		_reservasChk = new JCheckBox("Reservas");
		_sessoesList = new JList();
		_addBtn = new JButton("Adicionar");
		_remBtn = new JButton("Remover");
		
		_dataActual = _calendario.getDate();
		_horaInicio.setDate(new Date(0));
		_horaFim.setDate(new Date(3600000));
		_salasCmb.setModel(new DefaultComboBoxModel(AppScreen.getSalas()));
		_espectaculosList.setModel(AppScreen.getEspectaculos());
		_precosList.setModel(AppScreen.getPrecos());
		_espectaculosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_sessoesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_precosList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		_horaInicio.setDateFormatString("HH:mm");
		_horaFim.setDateFormatString("HH:mm");
		sessoesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Sessões"));
		
		_salasCmb.setPreferredSize(new Dimension(250, 25));
		espectaculosScroll.setPreferredSize(new Dimension(250, 300));
		precosScroll.setPreferredSize(new Dimension(250, 200));
		sessoesScroll.setPreferredSize(new Dimension(250, 400));
		
		espectaculosScroll.setViewportView(_espectaculosList);
		precosScroll.setViewportView(_precosList);
		sessoesScroll.setViewportView(_sessoesList);
		
		_horaInicio.addPropertyChangeListener("date", new PropertyChangeListener() {
			Date offset = new Date();
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Date newDate = (Date)evt.getNewValue();
				offset.setTime(((Date)evt.getOldValue()).getTime() + 3601000);
				
				if(!newDate.after(offset) && offset.after(_horaFim.getDate()))
					_horaFim.setDate(newDate);
			}
		});
		
		_salasCmb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					_salaActual = (Sala)_salasCmb.getSelectedItem();
					SessoesGest.this.selectSessoes();
				}
			}
		});
		_calendario.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				_dataActual = _calendario.getDate();
				SessoesGest.this.selectSessoes();
			}
		});
		_addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date inicio = _horaInicio.getDate();
				Date fim = _horaFim.getDate();
				Espectaculo es = (Espectaculo)_espectaculosList.getSelectedValue();
				List<Object> precos = Arrays.asList(_precosList.getSelectedValues());
				
				if(_salaActual == null || es == null || precos.size() == 0) {
					JOptionPane.showMessageDialog(SessoesGest.this, "Algumas das escolhas encontram-se vazias", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!_salaActual.hasSessoesDia(_dataActual))
					_salaActual.addSalaDia(new SalaDia(_calendario.getDate()));
				
				DefaultListModel dlm = (DefaultListModel)_salaActual.getSessoesDia(_dataActual);
				
				if(checkColisoesHorarios(inicio, fim, dlm))
					JOptionPane.showMessageDialog(SessoesGest.this, "Verifique o horário da sessão.", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					dlm.addElement(new Sessao(es, inicio, fim, (Preco[])precos.toArray(new Preco[precos.size()]), _reservasChk.isSelected()));
					_sessoesList.setModel(_salaActual.getSessoesDia(_dataActual));
				}
			}
		});
		_remBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(_salaActual == null || !_salaActual.hasSessoesDia(_dataActual)) {
					JOptionPane.showMessageDialog(SessoesGest.this, "Não existem sessões a remover.", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				DefaultListModel dlm = (DefaultListModel)_sessoesList.getModel();
				int index = _sessoesList.getSelectedIndex();
				
				if(index != -1)
					dlm.removeElementAt(index);
				else
					JOptionPane.showMessageDialog(SessoesGest.this, "Não seleccionou nenhuma sessão.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets.set(20, 75, 0, 110);
		layout.setConstraints(salasLbl, constraints);
		constraints.insets.set(5, 75, 100, 100);
		layout.setConstraints(_salasCmb, constraints);
		constraints.insets.set(100, 75, 0, 110);
		layout.setConstraints(calendarioLbl, constraints);
		constraints.insets.set(5, 75, 50, 100);
		layout.setConstraints(_calendario, constraints);
		
		constraints.insets.set(10, 20, 0, 120);
		layout.setConstraints(espectaculosLbl, constraints);
		constraints.insets.set(5, 20, 15, 120);
		layout.setConstraints(espectaculosScroll, constraints);
		constraints.insets.set(5, 20, 0, 120);
		layout.setConstraints(precosLbl, constraints);
		constraints.insets.set(5, 20, 15, 120);
		layout.setConstraints(precosScroll, constraints);
		constraints.gridwidth = GridBagConstraints.BOTH;
		constraints.insets.set(5, 40, 0, 0);
		layout.setConstraints(inicioLbl, constraints);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets.set(5, -95, 0, 100);
		layout.setConstraints(fimLbl, constraints);
		constraints.gridwidth = GridBagConstraints.BOTH;
		constraints.insets.set(0, 40, 45, 0);
		layout.setConstraints(_horaInicio, constraints);
		constraints.insets.set(0, 25, 45, 0);
		layout.setConstraints(_horaFim, constraints);
		constraints.insets.set(0, -85, 45, 0);
		layout.setConstraints(_reservasChk, constraints);
		
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets.set(10, 20, 0, 75);
		layout.setConstraints(sessoesLbl, constraints);
		constraints.insets.set(5, 20, 0, 75);
		layout.setConstraints(sessoesScroll, constraints);
		constraints.gridwidth = GridBagConstraints.BOTH;
		constraints.insets.set(35, 50, 0, 0);
		layout.setConstraints(_addBtn, constraints);
		constraints.insets.set(35, 0, 0, 75);
		layout.setConstraints(_remBtn, constraints);
		
		leftPanel.add(salasLbl);
		leftPanel.add(_salasCmb);
		leftPanel.add(calendarioLbl);
		leftPanel.add(_calendario);
		
		centerPanel.add(espectaculosLbl);
		centerPanel.add(espectaculosScroll);
		centerPanel.add(precosLbl);
		centerPanel.add(precosScroll);
		centerPanel.add(inicioLbl);
		centerPanel.add(fimLbl);
		centerPanel.add(_horaInicio);
		centerPanel.add(_horaFim);
		centerPanel.add(_reservasChk);
		
		rightPanel.add(sessoesLbl);
		rightPanel.add(sessoesScroll);
		rightPanel.add(_addBtn);
		rightPanel.add(_remBtn);
		
		sessoesPanel.add(leftPanel, BorderLayout.WEST);
		sessoesPanel.add(centerPanel, BorderLayout.CENTER);
		sessoesPanel.add(rightPanel, BorderLayout.EAST);
		
		_mainPanel.add(sessoesPanel);
	}
	
	private final void selectSessoes() {	
		if(_salaActual == null)
			return;
		
		if(_salaActual.hasSessoesDia(_dataActual))
			_sessoesList.setModel(_salaActual.getSessoesDia(_dataActual));
		else
			_sessoesList.setModel(lm);
	}
	
	private final boolean checkColisoesHorarios(Date inicio, Date fim, DefaultListModel sessoes) {
		Sessao s;
		Date sInicio, sFim;
		
		if(inicio.after(fim) || inicio.equals(fim))
			return true;
		
		for(int i = 0; i < sessoes.getSize(); ++i) {
			s = (Sessao)sessoes.elementAt(i);
			sInicio = s.getInicio();
			sFim = s.getFim();
			
			if((inicio.after(sInicio) && fim.before(sFim) || inicio.before(sFim)) && (fim.after(sInicio)))
				return true;
			
			if(inicio.equals(sInicio) && fim.equals(sFim))
				return true;
		}
		
		return false;
	}
}
