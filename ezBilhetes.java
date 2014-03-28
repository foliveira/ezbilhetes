package ezBilhetes;

import javax.swing.UIManager;

import ezBilhetes.ui.LoginScreen;

/**
 * 
 * Classe que contém o ponto de entrada da aplicação.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 * 
 * @see ezBilhetes.ui.LoginScreen
 */
public abstract class ezBilhetes {

	/**
	 * Ponto de entrada da aplicação. <br>
	 * Lança uma janela de autenticação de utilizador
	 * 
	 * @param args Argumentos passados pela linha de comandos.
	 */
	public static void main(String[] args) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {}
			
			LoginScreen login = new LoginScreen();
			login.setVisible(true);
	}
}
