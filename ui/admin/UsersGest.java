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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ezBilhetes.core.PasswordManager;

/**
 * 
 * Painel para gestão de utilizadores.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public final class UsersGest extends GestPanel {
	private static final long serialVersionUID = 1512332147619997859L;
	
	private JList _usersList;
	private JTextField _nomeUser;
	private JPasswordField _passwordUser;
	private JPasswordField _confirmPassUser;
	private JButton _addBtn;
	private JButton _remBtn;
	private JButton _modUserBtn;
	private JPasswordField _passwordAdmin;
	private JPasswordField _confirmPassAdmin;
	private JButton _modAdminBtn;
	
	private DefaultListModel _dlm;
	
	/**
	 * Cria um novo painel.
	 * 
	 * @param owner Janela mãe do painel
	 */
	public UsersGest(AdminScreen owner) {
		super(owner);
	}

	/**
	 * Coloca o painel visivél conforme o parâmetro e actualiza a lista de utilizadores.
	 * 
	 * @param visible se <code>true</code> coloca o painel visivél.
	 */
	@Override
	public final void setVisible(boolean visible) {
		updateUsersList();
		super.setVisible(visible);
	}
	
	@Override
	protected final void populate() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		JPanel usersPanel = new JPanel(new FlowLayout());
		JPanel usersInfo = new JPanel(new FlowLayout());
		JPanel usersEdit = new JPanel(layout);
		JPanel adminEdit = new JPanel(layout);
		
		JScrollPane scrollUsers = new JScrollPane();
		
		_dlm = new DefaultListModel();
		_usersList = new JList(_dlm);
		_nomeUser = new JTextField(20);
		_passwordUser = new JPasswordField(20);
		 _confirmPassUser = new JPasswordField(20);
		 _addBtn = new JButton("Adicionar");
		 _remBtn = new JButton("Remover");
		 _modUserBtn = new JButton("Modificar");
		 _passwordAdmin = new JPasswordField(20);
		 _confirmPassAdmin = new JPasswordField(20);
		 _modAdminBtn = new JButton("Modificar");
		
		 _usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollUsers.setViewportView(_usersList);
		scrollUsers.setPreferredSize(new Dimension(150, 300));
		
		_passwordUser.setEchoChar('*');
		_confirmPassUser.setEchoChar('*');
		_passwordAdmin.setEchoChar('*');
		_confirmPassAdmin.setEchoChar('*');
		
		usersPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Utilizadores"));
		usersInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Editar Utilizador"));
		adminEdit.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Editar Administrador"));
		
		_addBtn.addActionListener(new UserGestListner());
		_modUserBtn.addActionListener(new UserGestListner());
		
		_usersList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				_nomeUser.setText((String)_usersList.getSelectedValue());
			}
		});
		_remBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String user = (String)_usersList.getSelectedValue();
				
				PasswordManager.removeUtilizador(user);
				_nomeUser.setText(""); _passwordUser.setText(""); _confirmPassUser.setText("");
				updateUsersList();
			}
		});
		_modAdminBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(_passwordAdmin.getPassword());
				String confirmPassword = new String(_confirmPassAdmin.getPassword());
				
				if(password.equals(confirmPassword)) {
					PasswordManager.modifyPasswordUtilizador("admin", password);
					_passwordAdmin.setText(""); _confirmPassAdmin.setText("");
					return;
				}
				
				JOptionPane.showMessageDialog(UsersGest.this, "As password não coincidem", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets.set(0, 5, 5, 0);
		layout.setConstraints(_nomeUser, constraints);
		layout.setConstraints(_passwordUser, constraints);
		layout.setConstraints(_confirmPassUser, constraints);
		constraints.gridwidth = GridBagConstraints.BOTH;
		constraints.insets.set(10, 5, 0, 0);
		layout.setConstraints(_addBtn, constraints);
		layout.setConstraints(_remBtn, constraints);
		layout.setConstraints(_modUserBtn, constraints);
		
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets.set(0, 5, 5, 0);
		layout.setConstraints(_passwordAdmin, constraints);
		layout.setConstraints(_confirmPassAdmin, constraints);
		constraints.gridwidth = GridBagConstraints.BOTH;
		constraints.insets.set(10, 0, 0, -200);
		layout.setConstraints(_modAdminBtn, constraints);
		
		usersEdit.add(new JLabel("Nome:"));
		usersEdit.add(_nomeUser);
		usersEdit.add(new JLabel("Password:"));
		usersEdit.add(_passwordUser);
		usersEdit.add(new JLabel("Confirmar:"));
		usersEdit.add(_confirmPassUser);
		usersEdit.add(_addBtn);
		usersEdit.add(_remBtn);
		usersEdit.add(_modUserBtn);
		
		adminEdit.add(new JLabel("Password:"));
		adminEdit.add(_passwordAdmin);
		adminEdit.add(new JLabel("Confirmar:"));
		adminEdit.add(_confirmPassAdmin);
		adminEdit.add(_modAdminBtn);
		
		usersInfo.add(scrollUsers);
		usersInfo.add(usersEdit);
		
		usersPanel.add(usersInfo);
		usersPanel.add(adminEdit);
		
		_mainPanel.add(usersPanel);
	}
	
	private final void updateUsersList() {
		_dlm.removeAllElements();
		
		for(String name : PasswordManager.getUtilizadores()) {
			if(!name.equals("admin"))
				_dlm.addElement(name);
		}
	}
	
	private class UserGestListner implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String user = _nomeUser.getText();
			String password = new String(_passwordUser.getPassword());
			String confPassword = new String(_confirmPassUser.getPassword());
			
			if(user.equals("") || password.equals("") || confPassword.equals("")) {
				JOptionPane.showMessageDialog(UsersGest.this, "Verifique todos os parâmetros.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(e.getSource().equals(_addBtn) && PasswordManager.hasUser(user)) {
				JOptionPane.showMessageDialog(UsersGest.this, "Utilizador já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!password.equals(confPassword)) {
				JOptionPane.showMessageDialog(UsersGest.this, "As passwords não coincidem.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(e.getSource().equals(_addBtn))
				PasswordManager.addUtilizador(user, password);
			else
				PasswordManager.modifyPasswordUtilizador(user, password);
			
			_nomeUser.setText(""); _passwordUser.setText(""); _confirmPassUser.setText("");
			updateUsersList();
		}
	}
}
