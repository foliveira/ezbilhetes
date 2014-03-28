package ezBilhetes.ui.admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * 
 * Classe abstracta representativa de um painel a adicionar ao módulo administrativo.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public abstract class GestPanel extends JComponent  {
	
	/**
	 * Janela mãe do painel.
	 */
	protected AdminScreen _owner;
	/**
	 * Painel principal, onde serão adicionados os componentes.
	 */
	protected JPanel _mainPanel;
	
	/**
	 * Cria um novo painel.
	 * 
	 * @param owner janela mãe do painel.
	 */
	public GestPanel(AdminScreen owner) {
		super();
		
		_owner = owner;
		_mainPanel = new JPanel(new FlowLayout());
		this.setLayout(new BorderLayout());
		this.add(_mainPanel, BorderLayout.CENTER);
		
		populate();
	}
	
	/**
	 * Método gancho para redefinição nas classes derivadas. Todos os controlos
	 * presentes no painel a criar devem ser inicializados e dispostos neste método.
	 */
	protected abstract void populate();
}
