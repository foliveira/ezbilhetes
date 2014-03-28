package ezBilhetes.ui.admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * 
 * Classe abstracta representativa de um painel a adicionar ao m�dulo administrativo.
 * 
 * @author F�bio Oliveira
 * @version 1.0
 */
public abstract class GestPanel extends JComponent  {
	
	/**
	 * Janela m�e do painel.
	 */
	protected AdminScreen _owner;
	/**
	 * Painel principal, onde ser�o adicionados os componentes.
	 */
	protected JPanel _mainPanel;
	
	/**
	 * Cria um novo painel.
	 * 
	 * @param owner janela m�e do painel.
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
	 * M�todo gancho para redefini��o nas classes derivadas. Todos os controlos
	 * presentes no painel a criar devem ser inicializados e dispostos neste m�todo.
	 */
	protected abstract void populate();
}
