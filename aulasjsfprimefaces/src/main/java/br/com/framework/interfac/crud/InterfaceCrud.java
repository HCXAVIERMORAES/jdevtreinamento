package br.com.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * classe generica, InterfaceCrud<T>, pois irá trabalhar com qualque objeto 
 * @author hphoe
 *
 * @param <T> , é um padrão java para classes genericas
 */

@Component //para injeção de dependencia do spring
@Transactional //para transação com Banco Dados
public interface InterfaceCrud<T> extends Serializable {
	
	//rotinas padrões
	
	//salva os dados
	void save(T obj) throws Exception; //lanca exessões para cima, fora do sistema
	
	
	void persist(T obj) throws Exception;
	
	//salva ou atualiza
	void saveOrUpdate(T obj) throws Exception;
	
	//atualiza dados
	void update(T obj) throws Exception;
	
	//deleta dados
	void delete(T obj) throws Exception;
	
	//merge - salva ou atualiza e retorna qualquer objeto
	T merge (T obj) throws Exception;
	
	//carrega alista de dados de determinada classe
	List<T> findlList(Class<T> objs) throws Exception;
	
	//metodo para buscar um objeto qualquer
	Object findById(Class<T> entidade, Long id) throws Exception;
	
	//outro metodo porem retornando um T
	T findByPorId(Class<T> entidade, Long id) throws Exception;
	
	//metodo para fazer o carregamento de classe passando uma query dinamica
	List<T> findListByQueryDinamica(String s) throws Exception;
	
	//executar update de query dinamica. executa update com HQL
	void executeUpdateQueryDinamica(String s) throws Exception;
	
	//executar update de SQL dinamica. executa update com SQL puro
	void executeUpdateSQLDinamica(String s ) throws Exception;
	
	//fazer  o hibernate limpar sua sessão
	void clearSession() throws Exception;
	
	//retirar objeto da sessão do hibernate
	void evict (Object objs) throws Exception;
	
	//retorna uma sessão dentro da interface
	Session getSession() throws Exception;
	
	List<?> getListSQLDinamico (String sql) throws Exception;
	
	//3 metodo pra trablhar com jdbc Spring
	JdbcTemplate getJdbcTemplate();
	
	SimpleJdbcTemplate getjdJdbcTemplate();
	
	SimpleJdbcInsert getsimplejJdbcInsert();
	
	//total de registro, recebendo uma tabela
	Long totalRegistro(String table) throws Exception;
	
	Query obterQuery(String query) throws Exception;
	
	//carregamento dinamico com jsf e primefaces
	List<T> findListByQueryDinamica(String query, int iniciaNovoRegistro, int maximoResultado) throws Exception;

}
