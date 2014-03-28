package ezBilhetes.ui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import ezBilhetes.core.Estado;
import ezBilhetes.core.PasswordManager;
import ezBilhetes.core.Sala;

/**
 * Uma classe abstracta representativa de janelas da aplica��o.
 * 
 * @author   F�bio Oliveira
 * @version   1.0
 */
@SuppressWarnings("unchecked")
public abstract class AppScreen extends JFrame {

	@SuppressWarnings("unused")
	private static PasswordManager _vault;

	private static ListModel _precos;
	private static ListModel _espectaculos;
	private static Vector<Sala> _salas;
	private static List<Estado> _estados;

	private JFrame _owner;
	private JMenuItem _fecharItem;
	private JMenuItem _sobreItem;
	
	static {
		FileInputStream fis = null;
		ObjectInputStream in = null;

		try {
			fis = new FileInputStream("ezbilhetes.stt");
			in = new ObjectInputStream(fis);

			_salas = (Vector<Sala>)in.readObject();
			_estados = (List<Estado>)in.readObject();
			_espectaculos = (ListModel)in.readObject();
			_precos = (ListModel)in.readObject();
			_vault = (PasswordManager)in.readObject();

			in.close();
		}
		catch(IOException ex) {
			createDefaultObjects();

		}
		catch(ClassNotFoundException ex) {
			createDefaultObjects();
		}
	}

	/**
	 * Cria uma nova janela.
	 * 
	 * @param name
	 */
	protected AppScreen(String name) {
		super(name);

		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.createMenus();
		this.populate();
	}

	/**
	 * Coloca a janela vis�vel e atribu�-lhe uma janela m�e.
	 * 
	 * @param owner janela m�e da janela actual.
	 * @param visible se <code>true</code> coloca a janela vis�vel.
	 */
	protected final void setVisible(JFrame owner, boolean visible) {
		_owner = owner;
		setVisible(visible);
	}

	/**
	 * Retorna as salas presentes na aplica��o.
	 * 
	 * @return vector com as salas presentes na aplica��o.
	 */
	public static final Vector<Sala> getSalas() {
		return _salas;
	}

	/**
	 * Retorna todos os pre�os presentes na aplica��o.
	 * 
	 * @return <code>ListModel</code> com todos os pre�os da aplica��o.
	 * @see javax.swing.ListModel
	 */
	public static final ListModel getPrecos() {
		return _precos;
	}

	/**
	 * Retorna todos os espect�culos presentes na aplica��o.
	 * 
	 * @return <code>ListModel</code> com todos os espect�culos da aplica��o.
	 * @see javax.swing.ListModel 
	 */
	public static final ListModel getEspectaculos() {
		return _espectaculos;
	}

	/**
	 * Retorna todos os estados presentes na aplica��o.
	 * 
	 * @return lista com todos os estados da aplica��o.
	 */
	public static final List<Estado> getEstados() {
		return _estados;
	}

	/**
	 * Serializa os objectos representativos do estado da aplica��o.
	 * O ficheiro resultante da serializa��o tem o nome: <code>ezbilhetes.stt</code>. 
	 * 
	 * @see #getSalas()
	 * @see #getPrecos()
	 * @see #getEspectaculos()
	 * @see #getEstados()
	 */
	public static final void serializeObjects() {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		try {
			fos = new FileOutputStream("ezbilhetes.stt");
			out = new ObjectOutputStream(fos);

			out.writeObject(_salas);
			out.writeObject(_estados);
			out.writeObject(_espectaculos);
			out.writeObject(_precos);
			out.writeObject(_vault);

			out.close();
		}
		catch(IOException ex) {}
	}
	
	private static final void createDefaultObjects() {
		_precos = new DefaultListModel();
		_espectaculos = new DefaultListModel();
		 _salas = new Vector<Sala>();
		 _estados = new LinkedList<Estado>();
		 _vault = new PasswordManager();
		 
		_estados.add(new Estado("Livre", Color.green));
		_estados.add(new Estado("Reservado", Color.yellow));
		_estados.add(new Estado("Ocupado", Color.red));
		
		PasswordManager.addUtilizador("admin", "password");
	}

	/**
	 * Disp�e a aplica��o, escondendo a janela e colocando vis�vel a sua janela m�e
	 * 
	 * @see #setVisible(JFrame, boolean)
	 */
	@Override
	public final void dispose() {
		this.setVisible(false);
		_owner.setVisible(true);
		super.dispose();
	}

	/**
	 * M�todo gancho para redefini��o nas classes derivadas. Todos os controlos
	 * presentes na janela a criar devem ser inicializados e dispostos neste m�todo.
	 */
	protected abstract void populate();
	
	private final void createMenus() {
		JMenuBar menu = new JMenuBar();
		JMenuItem aplicacaoMenu = new JMenu("Aplica��o");
		JMenuItem ajudaMenu = new JMenu("Ajuda");

		_fecharItem = new JMenuItem("Fechar");
		_sobreItem = new JMenuItem("Sobre");

		_fecharItem.addActionListener(new MenuListner());
		_sobreItem.addActionListener(new MenuListner());

		aplicacaoMenu.add(_fecharItem);
		ajudaMenu.add(_sobreItem);

		menu.add(aplicacaoMenu);
		menu.add(ajudaMenu);

		this.setJMenuBar(menu);
	}

	private class MenuListner implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(_fecharItem))
				AppScreen.this.dispose();
			else
				JOptionPane.showMessageDialog(AppScreen.this, "Posto de Venda - ezBilhetes");
		}
	}
}
