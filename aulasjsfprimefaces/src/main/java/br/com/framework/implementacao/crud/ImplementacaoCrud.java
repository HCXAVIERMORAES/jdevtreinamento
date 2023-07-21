package br.com.framework.implementacao.crud;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.interfac.crud.InterfaceCrud;

/**
 * deve implementar a interface criada e dizer q tbm vai trabalhar com classes
 * genericas implementar todos os metodo da interface. Trabalhar com injeção de
 * dependencia (@Component) e transação (@Transactional)
 * 
 * @author hphoe
 *
 * @param <T>
 */

@Component
@Transactional
public class ImplementacaoCrud<T> implements InterfaceCrud<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * primeiro pega-se a sessão do hibernate, deve ser unica (statico). Declarar
	 * objetos como o JDBC do spring, Injeção de dependencia usa-se Autowired
	 */
	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); // 1ª

	@Autowired
	private JdbcTemplateImpl jdbcTemplate;

	@Autowired
	private SimpleJdbcTemplateImpl simpleJdbcTemplate;

	@Autowired
	private SimpleJdbcInsertImplents simpleJdbcInsert;

	// fora da aula
	@Autowired
	private SimpleJdbcClassImpl simpleJdbcClassImpl;

	// segundo a aula.
	@Autowired
	private SimpleJdbcTemplateImpl simpleJdbcTemplateImpl;

	// Apenas usar os gets

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public SimpleJdbcTemplateImpl getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	public SimpleJdbcInsertImplents getSimpleJdbcInsert() {
		return simpleJdbcInsert;
	}

	public SimpleJdbcClassImpl getSimpleJdbcClassImpl() {
		return simpleJdbcClassImpl;
	}

	public SimpleJdbcTemplateImpl getSimpleJdbcTemplateImpl() {
		return simpleJdbcTemplateImpl;
	}

	// metodos da interface
	@Override
	public void save(T obj) throws Exception {
		validaSessionFactory(); // se sessão não for valida da problema
		sessionFactory.getCurrentSession().save(obj);
		executFlushSession();// roda o sql

	}

	@Override
	public void persist(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().persist(obj);
		executFlushSession();

	}

	@Override
	public void saveOrUpdate(T obj) throws Exception {
		// o metodo sabe identificar se o objeto já é ou não persistente no BD
		validaSessionFactory();
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
		executFlushSession();

	}

	@Override
	public void update(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().update(obj);
		executFlushSession();

	}

	@Override
	public void delete(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().delete(obj);
		executFlushSession();

	}

	// retorna o objeto
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T obj) throws Exception {
		validaSessionFactory();
		obj = (T) sessionFactory.getCurrentSession().merge(obj);
		executFlushSession();
		return obj;
	}

	// metodo para consultar uma lista de resultados, por isso não modifica nada logo não usa o executFlushSession();
	@Override
	public List<T> findlList(Class<T> entidade) throws Exception {
		validaSessionFactory();

		StringBuilder query = new StringBuilder(); // é mais rapido e não precisa ficar concatenando
		// append para adicionar texto, retorna nome da classe(entidade.getSimpleName())
		query.append(" select distinct(entity) from ").append(entidade.getSimpleName()).append(" entity ");

		List<T> lista = sessionFactory.getCurrentSession().createQuery(query.toString()).list();

		return lista;
	}

	//retorna o objeto
	@Override
	public Object findById(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();		
		Object obj = sessionFactory.getCurrentSession().load(getClass(), id);//carregamento
		return obj;
	}

	@Override
	public T findByPorId(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();		
		T obj = (T) sessionFactory.getCurrentSession().load(getClass(), id);//carregamento	
		return obj;
	}

	//consulta uma lista de dados passando uma query qualquer
	@Override
	public List<T> findListByQueryDinamica(String s) throws Exception {
		validaSessionFactory();
		List<T> lista = new ArrayList<T>();
	    lista = sessionFactory.getCurrentSession().createQuery(s).list();
		return lista;
	}

	//metodo de update dinamica logo usa-se tbm o metodo executFlushSession();
	@Override
	public void executeUpdateQueryDinamica(String s) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().createQuery(s).executeUpdate();
		executFlushSession();

	}

	@Override
	public void executeUpdateSQLDinamica(String s) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().createSQLQuery(s).executeUpdate();
		executFlushSession();

	}

	//fazer o hibernate limpar a sessão, qdo não se sabe qual objeto esta dando problema na memoria
	@Override
	public void clearSession() throws Exception {
		sessionFactory.getCurrentSession().clear();

	}

	//qdo se sabe qual objeto esta dando problema na memoria
	@Override
	public void evict(Object objs) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().evict(objs);//retira o objeto da sessao
	}

	//retorna a sessãodo hibernate para usar em algum outro metodo q não esteja implementado
	@Override
	public Session getSession() throws Exception {
		validaSessionFactory();
		return sessionFactory.getCurrentSession();
	}

	//retorna uma lista dinamica atraves do SQL
	@Override
	public List<?> getListSQLDinamico(String sql) throws Exception {
		validaSessionFactory();
		List<?> lista = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {

		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getjdJdbcTemplate() {

		return simpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getsimplejJdbcInsert() {

		return simpleJdbcInsert;
	}

	//não é preciso usar o hibernate podendo fazer de uma forma generica ganhando performace (usando jdbc)
	@Override
	public Long totalRegistro(String table) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(1) from ").append(table);//retorna um numero inteiro
		return jdbcTemplate.queryForLong(sql.toString());//faz a conversao
	}
	
	//sera importante na ora de fazer uma tabela generica de consulta (Query do hibernate. ira passar uma instrução ao hibernate
	@Override
	public Query obterQuery(String query) throws Exception {
		//usa-se se precissar de uma query, instrução preparada para usar no sistema
		validaSessionFactory();
		Query queryReturn = sessionFactory.getCurrentSession().createQuery(query.toString());
		return queryReturn; 
	}

	/**
	 * serve para carregamento por demanda do hibernate
	 * Realiza  consulta no Banco de dados, iniciando o carregamento a partir do registro
	 * passado no parametro -> iniciaNovoRegistro e obtendo maximo  de resultados 
	 * passados em -> maximoResultado
	 * @param query
	 * @param iniciaNovoRegistro 
	 * @param  maximoResultado 
	 * @return List<T>
	 * @throws Execption
	 */
	@Override
	public List<T> findListByQueryDinamica(String query, int iniciaNovoRegistro, int maximoResultado) throws Exception {
		validaSessionFactory();
		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createQuery(query)
				.setFirstResult(iniciaNovoRegistro).setMaxResults(maximoResultado).list();
		return lista;
	}

	// validar sessão
	private void validaSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateUtil.getSessionFactory();
		}

		validaTransation();
	}

	// metodo de validação de tramsação
	private void validaTransation() {
		if (!sessionFactory.getCurrentSession().getTransaction().isActive()) {// se não tiver transação ativa
			sessionFactory.getCurrentSession().beginTransaction();// inica transação
		}

	}

	private void commitProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().commit();
	}

	private void rollBackProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().rollback();
	}

	/**
	 * Roda instantaneamente o SQL no banco dados
	 */
	private void executFlushSession() {
		sessionFactory.getCurrentSession().flush();
	}
	
	//retornar um array de objeto, retorna uma lista generica de objetos genericos
		public List<Object[]> getListSQLDinamicaArray(String sql) throws Exception {
			validaSessionFactory();
			
			List<Object[]> lista = (List<Object[]>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
			return lista;
		}
	
}
