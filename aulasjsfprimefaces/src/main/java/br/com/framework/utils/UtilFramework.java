package br.com.framework.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * classe queirar ler o arquivo context
 * será responsavel por cadastrar o usuario q está inserindo, excluindo e editando. Cria-se um log.
  */
@Component
public class UtilFramework implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 1ª- criar uma thread local que ira carregar o usuario que esta utilizando o sistema e BD
	 * 2ª - ter um metodo de sincronizar 
	 */

	private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>(); //carrega o codigo do usuario
	
	//por ser sincronizado e staico 2 metodos não acessam a base de dados ao mesmo tempo
	public synchronized static ThreadLocal<Long> getThreadLocal() {
		return threadLocal;
	}
	
}
