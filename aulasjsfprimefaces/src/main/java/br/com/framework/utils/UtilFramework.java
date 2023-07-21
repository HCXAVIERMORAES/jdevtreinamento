package br.com.framework.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * classe queirar ler o arquivo context
 * ser� responsavel por cadastrar o usuario q est� inserindo, excluindo e editando. Cria-se um log.
  */
@Component
public class UtilFramework implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 1�- criar uma thread local que ira carregar o usuario que esta utilizando o sistema e BD
	 * 2� - ter um metodo de sincronizar 
	 */

	private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>(); //carrega o codigo do usuario
	
	//por ser sincronizado e staico 2 metodos n�o acessam a base de dados ao mesmo tempo
	public synchronized static ThreadLocal<Long> getThreadLocal() {
		return threadLocal;
	}
	
}
