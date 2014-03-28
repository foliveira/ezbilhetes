package ezBilhetes.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Contém informação sobre todos os utilizadores registados na aplicação. Oferece funcionalidades para
 * adicionar, remover e modificar utilizadores, assim como verificar o registo de utilizadores e realizar a
 * autenticação no sistema.
 * 
 * @author Fábio Oliveira
 * @version 1.0
 */
public final class PasswordManager implements Serializable {
	private static final long serialVersionUID = 69122790169120672L;
	
	private static Dictionary< String, Integer> _tabelaAutenticacao;
	private static List<String> _utilizadores;
	
	static {
		_tabelaAutenticacao = new Hashtable<String, Integer>();
		_utilizadores = new LinkedList<String>();
	}
	
	/**
	 * Verifica se um utilizador se encontra registado no sistema.
	 * 
	 * @param user nome do utilizador a verificar.
	 * @return <code>true</code> caso o utilizador se encontre registado.
	 */
	public static final boolean hasUser(String user) {
		return _utilizadores.contains(user);
	}
	
	/**
	 * Adiciona um novo utilizador ao sistema.
	 * 
	 * @param user nome do utilizador a adicionar.
	 * @param password palavra-chave associada ao utilizador a adicionar.
	 */
	public static final void addUtilizador(String user, String password) {
		modifyPasswordUtilizador(user, password);
		_utilizadores.add(user);
	}
	
	/**
	 * Remove um utilizadore do sistema.
	 * 
	 * @param user nome do utilizador a remover.
	 */
	public static final void removeUtilizador(String user) {
		modifyPasswordUtilizador(user, null);
		_utilizadores.remove(user);
	}
	
	/**
	 * Modifica a palavra-chave de um utilizador do sistema.
	 * 
	 * @param user nome do utilizador.
	 * @param password nova palavra-chave associada ao utilizador.
	 */
	public static final void modifyPasswordUtilizador(String user, String password) {
		if(password != null)
			_tabelaAutenticacao.put(user, password.hashCode());
		else
			_tabelaAutenticacao.remove(user);
	}
	
	/**
	 * Verifica se uma palavra-chave se encontra associada a um utilizador.
	 * 
	 * @param user nome do utilizador.
	 * @param insertedPassword palavra-chave a verificar.
	 * @return <code>true</code> caso a palavra-chave corresponda à associada ao utilizador.
	 */
	public static final boolean verificarPassword(String user, String insertedPassword) {
		if(!_utilizadores.contains(user))
			return false;
		
		return _tabelaAutenticacao.get(user).equals(insertedPassword.hashCode()); 
	}
	
	/**
	 * Retorna uma lista com o nome de todos os utilizadores presentes no sistema.
	 * 
	 * @return lista com o nome de todos os utilizadores.
	 */
	public static final List<String> getUtilizadores() {
		return _utilizadores;
	}
	
	 private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		 out.writeUnshared(_tabelaAutenticacao);
		 out.writeUnshared(_utilizadores);
	 }

	 @SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		_tabelaAutenticacao = (Dictionary< String, Integer>)in.readUnshared(); 
		_utilizadores = (List<String>)in.readUnshared();
	 }
}
