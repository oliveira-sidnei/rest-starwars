package br.com.starwars.dao;

public class UsuarioDAO {

	public static boolean validar(String username, String password) {
		
		return ("admin".equals(username) && "admin".equals(password));
	}

}
