package br.com.project.util.all;

import java.io.Serializable;

/**
 * Classe responsavel por mandar as mesagens da aplia��o, com as de sucesso, etc.
 * Atributos ser�o staticos ou final, pois elespodemser acessadosde qualquer lugar. Por padr�o s�o maiusculas
 * @author hphoe
 *
 */

public class Constante implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String SUCESSO = "sucesso";
	public static final String OPERACAO_REALIZADA_COM_SUCESSO = "Opera��o Realizada com Sucesso.";
	public static final String ERRO_NA_OPERACAO = "N�o foi Poss�vel Executar a Opera��o.";

}
