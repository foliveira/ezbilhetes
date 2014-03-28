package ezBilhetes.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ezBilhetes.core.PasswordManager;
import ezBilhetes.ui.admin.AdminScreen;
import ezBilhetes.ui.user.VendingScreen;

/**
 * Ecrã de autenticação da aplicação. Exibe um ecrã de forma a permite aos  utilizadores da aplicação se possam autenticar e aceder aos módulos respectivos.
 * 
 * @author     Fábio Oliveira
 * @version     1.0
 */
public final class LoginScreen extends JFrame {
	private static final long serialVersionUID = -3194336012847403378L;
	
	private JButton _okBtn;
	private JButton _exitBtn;
	private JTextField _userTxt;
	private JPasswordField _passTxt;
	
	private AppScreen _admin;
	private AppScreen _vending;
	
	/**
	 * Cria um novo ecrã de autenticação.
	 */
	public LoginScreen() {
		super("ezBilhetes - Ecrã de Login");
		
		this.Populate();
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Coloca a janela de autenticação visível conforme o valor do parâmetro <code>visible</code>
	 * 
	 * @param visible <code>true</code> para colocar a janela visível <code>false</code> para a esconder.
	 */
	@Override
	public final void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible) {
			_userTxt.setText("");
			_passTxt.setText("");
			_userTxt.requestFocus();
		}
	}

	private final void Populate() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		JPanel inputPanel = new JPanel(layout);
		JPanel buttonPanel  = new JPanel(new FlowLayout());
		JPanel appPanel = new JPanel(new FlowLayout());
		
		_admin = new AdminScreen();
		_vending = new VendingScreen();
		_okBtn = new JButton("Confirmar");
		_exitBtn = new JButton("Sair");
		_userTxt = new JTextField(25);
		_passTxt = new JPasswordField(25);
		
		_passTxt.setEchoChar('*');
		this.getRootPane().setDefaultButton(_okBtn);

		_okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String user = LoginScreen.this._userTxt.getText();
				String password = new String(LoginScreen.this._passTxt.getPassword());
				
				if(PasswordManager.verificarPassword(user, password)) {
					LoginScreen.this.setVisible(false);
					if(user.equals("admin"))
						_admin.setVisible(LoginScreen.this, true);
					else 
						_vending.setVisible(LoginScreen.this, true);
				}
				else
					JOptionPane.showMessageDialog(LoginScreen.this, "Password Errada.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
		_exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginScreen.this.setVisible(false);
				AppScreen.serializeObjects();
				LoginScreen.this.dispose();
			}
		});

		appPanel.add(new JLabel(new ImageIcon("./res/ticket.png"), SwingConstants.LEFT));
		
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(_userTxt, constraints);		
		inputPanel.add(new JLabel("Utilizador:", SwingConstants.RIGHT));
		inputPanel.add(_userTxt);	
		inputPanel.add(new JLabel("Password:", SwingConstants.RIGHT));
		inputPanel.add(_passTxt);
		
		buttonPanel.add(_okBtn);
		buttonPanel.add(_exitBtn);
		
		this.add(appPanel, BorderLayout.NORTH);
		this.add(inputPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
}
