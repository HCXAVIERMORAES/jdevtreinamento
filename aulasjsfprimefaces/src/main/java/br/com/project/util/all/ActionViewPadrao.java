package br.com.project.util.all;

import java.io.Serializable;

import javax.annotation.PostConstruct;

/**
 * Aula 29.21 interface de crud ActionViewPadrão
 * Implementação da classe de interface, crud generica
 * @author hphoe
 *
 */

public interface ActionViewPadrao extends Serializable {
	
	abstract void limparLista() throws Exception;
	
	abstract String save() throws Exception;
	
	abstract void saveNoReturn() throws Exception;
	
	abstract void saveEdit() throws Exception;
	
	abstract void excluir() throws Exception;
	
	abstract String ativar() throws Exception;
	
	/**
	 * 
	 * @PosConstruct realiza inicializaçãao de métodos, valores ou variaveis.
	 * @throws Exception
	 */
	@PostConstruct
	abstract String novo() throws Exception;
	
	abstract String editar() throws Exception;
	
	abstract void setarVariaveisNulas() throws Exception;
	
	abstract void consultarEntidade() throws Exception;
	
	abstract void statusOperation(EstatusPercistencia a) throws Exception;
	
	abstract String redirecionarNewEntidade() throws Exception;
	
	abstract String redirecionarFindEntidade() throws Exception;
	
	abstract void addMsg(String msg) throws Exception;

}
