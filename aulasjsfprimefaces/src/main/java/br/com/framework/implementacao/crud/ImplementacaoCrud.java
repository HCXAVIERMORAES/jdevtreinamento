package br.com.framework.implementacao.crud;

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
 * genericas implementar todos os metodo da interface.
 * Trabalhar com injeção de dependencia (@Component) e transação (@Transactional)
 * @author hphoe
 *
 * @param <T>
 */

@Component
@Transactional
public class ImplementacaoCrud<T> implements InterfaceCrud<T> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * primeiro pega-se a sessão do hibernate, deve ser unica (statico).
	 * Declarar objetos como o JDBC do spring,
	 * Injeção de dependencia usa-se Autowired
	 */
	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); //1ª
	
	@Autowired
	private JdbcTemplateImpl jdbcTemplate;
	
	@Autowired
	private SimpleJdbcTemplateImpl simpleJdbcTemplate;
	
	@Autowired
	private SimpleJdbcInsertImplents simpleJdbcInsert;
	
	//fora da aula
	@Autowired
	private SimpleJdbcClassImpl simpleJdbcClassImpl;
	
	//segundo a aula. 
	@Autowired
	private SimpleJdbcTemplateImpl simpleJdbcTemplateImpl;
	
	//Apenas usar os gets
		
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

	}

	@Override
	public void persist(T obj) throws Exception {
		

	}

	@Override
	public void saveOrUpdate(T obj) throws Exception {
		

	}

	@Override
	public void update(T obj) throws Exception {
		

	}

	@Override
	public void delete(T obj) throws Exception {
		

	}

	@Override
	public T merge(T obj) throws Exception {
		
		return null;
	}

	@Override
	public List<T> findlList(Class<T> objs) throws Exception {
		
		return null;
	}

	@Override
	public Object findById(Class<T> entidade, Long id) throws Exception {
		
		return null;
	}

	@Override
	public T findByPorId(Class<T> entidade, Long id) throws Exception {
		
		return null;
	}

	@Override
	public List<T> findListByQueryDinamica(String s) throws Exception {
		
		return null;
	}

	@Override
	public void executeUpdateQueryDinamica(String s) throws Exception {
		

	}

	@Override
	public void executeUpdateSQLDinamica(String s) throws Exception {
		

	}

	@Override
	public void clearSession() throws Exception {
		

	}

	@Override
	public void evict(Object objs) throws Exception {
		

	}

	@Override
	public Session getSession() throws Exception {
		
		return null;
	}

	@Override
	public List<?> getListSQLDinamico(String sql) throws Exception {
		
		return null;
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

	@Override
	public Long totalRegistro(String table) throws Exception {
		
		return null;
	}

	@Override
	public Query obterQuery(String query) throws Exception {
		
		return null;
	}

	@Override
	public List<T> findListByQueryDinamica(String query, int iniciaNovoRegistro, int maximoResultado) throws Exception {
		
		return null;
	}
	
	//validar sessão
	private void validaSessionFactory() {
		if(sessionFactory == null) {
			sessionFactory = HibernateUtil.getSessionFactory();
		}
		
		validaTransation();
	}
	
	//metodo de validação de tramsação
	private void validaTransation() {
		if(!sessionFactory.getCurrentSession().getTransaction().isActive()) {//se não tiver transação ativa
			sessionFactory.getCurrentSession().beginTransaction();//inica transação
		}		
		
	}
	
	private void commitProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().commit();
	}
	
	private void rollBackProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().rollback();
	}
	
}
