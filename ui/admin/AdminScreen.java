package ezBilhetes.ui.admin;

import javax.swing.JTabbedPane;

import ezBilhetes.ui.AppScreen;

/**
 * 
 * Janela correspondente ao m�dulo administrativo. Possibilita a administra��o
 * da aplica��o, permitindo a cria��o, remo��o e altera��o de salas, espect�culos, 
 * pre�os, sess�es e utilizadores.
 * 
 * @author F�bio Oliveira
 * @version 1.0
 *
 * @see ezBilhetes.ui.AppScreen
 */
public final class AdminScreen extends AppScreen {
	private static final long serialVersionUID = -1680062482848407449L;
	
	private JTabbedPane _tabs;

	/**
	 * Cria um novo ecr� de administra��o.
	 */
	public AdminScreen() {
		super("Ecr� de administra��o - ezBilhetes");
	}
	
	@Override
	protected final void populate() {	
		_tabs = new JTabbedPane();
		
		_tabs.addTab("Salas", null, new SalasGest(this), "Permite adicionar e remover salas");
		_tabs.addTab("Espect�culos", null, new EspectaculosGest(this), "Permite realizar a gest�o dos espect�culos");
		_tabs.addTab("Pre�os", null, new PrecosGest(this), "Permite efectuar a gest�o de pre�os.");
		_tabs.addTab("Sess�es", null, new SessoesGest(this), "Permite realizar a gest�o de sess�es");
		_tabs.addTab("Utilizadores", null, new UsersGest(this), "Permite gerir utilizadores.");
		
		this.add(_tabs);
	}
}
