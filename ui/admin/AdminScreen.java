package ezBilhetes.ui.admin;

import javax.swing.JTabbedPane;

import ezBilhetes.ui.AppScreen;

/**
 * 
 * Janela correspondente ao módulo administrativo. Possibilita a administração
 * da aplicação, permitindo a criação, remoção e alteração de salas, espectáculos, 
 * preços, sessões e utilizadores.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 *
 * @see ezBilhetes.ui.AppScreen
 */
public final class AdminScreen extends AppScreen {
	private static final long serialVersionUID = -1680062482848407449L;
	
	private JTabbedPane _tabs;

	/**
	 * Cria um novo ecrã de administração.
	 */
	public AdminScreen() {
		super("Ecrã de administração - ezBilhetes");
	}
	
	@Override
	protected final void populate() {	
		_tabs = new JTabbedPane();
		
		_tabs.addTab("Salas", null, new SalasGest(this), "Permite adicionar e remover salas");
		_tabs.addTab("Espectáculos", null, new EspectaculosGest(this), "Permite realizar a gestão dos espectáculos");
		_tabs.addTab("Preços", null, new PrecosGest(this), "Permite efectuar a gestão de preços.");
		_tabs.addTab("Sessões", null, new SessoesGest(this), "Permite realizar a gestão de sessões");
		_tabs.addTab("Utilizadores", null, new UsersGest(this), "Permite gerir utilizadores.");
		
		this.add(_tabs);
	}
}
