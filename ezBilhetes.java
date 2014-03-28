package ezBilhetes;

import javax.swing.UIManager;

import ezBilhetes.ui.LoginScreen;

/**
 * 
 * Classe que cont�m o ponto de entrada da aplica��o.
 * 
 * @author F�bio Oliveira
 * @version 1.0
 * 
 * @see ezBilhetes.ui.LoginScreen
 */
public abstract class ezBilhetes {

	/**
	 * Ponto de entrada da aplica��o. <br>
	 * Lan�a uma janela de autentica��o de utilizador
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
