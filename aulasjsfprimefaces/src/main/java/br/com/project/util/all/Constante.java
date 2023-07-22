package br.com.project.util.all;

import java.io.Serializable;

/**
 * Classe responsavel por mandar as mesagens da apliação, com as de sucesso, etc.
 * Atributos serão staticos ou final, pois elespodemser acessadosde qualquer lugar. Por padrão são maiusculas
 * @author hphoe
 *
 */

public class Constante implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String SUCESSO = "sucesso";
	public static final String OPERACAO_REALIZADA_COM_SUCESSO = "Operação Realizada com Sucesso.";
	public static final String ERRO_NA_OPERACAO = "Não foi Possível Executar a Operação.";

}
