package br.com.project.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.utils.UtilFramework;
import br.com.project.listener.ContextLoaderListenerCaixaKiUtils;
import br.com.project.model.classes.Entidade;

/**
 * Classe responsavel por comit e rolback. Intercepta todas as requisições do sistema.
 * @WebFilter define classe como filter.
 * Para se trabalhar apenas com o jsp se extende apenas o Filter, mais para o spring se extend o DelegatingFilterProxy.
 * se sobreescreve o initFilterBean() e o doFilter()
 * ContextLoaderListenerCaixaKiUtils -> classe criada para o sistema
 * @author hphoe
 *
 */

@WebFilter(filterName = "conexaoFilter")
public class FilterOpenSessionInView extends DelegatingFilterProxy implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static SessionFactory sf; //coneta ao BD
	
	//executa qdo a aplicação está sendo inicializada, sendo executada uma única vez
	@Override
		protected void initFilterBean() throws ServletException {
		
		sf = HibernateUtil.getSessionFactory(); //inicia sessao	
		
		}
	
	
	//invocado para toda a requisição e resposta
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		//JDBC Spring
		BasicDataSource sprinbasicDataSource = (BasicDataSource)
				ContextLoaderListenerCaixaKiUtils.getBean("springDataSoruce");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(sprinbasicDataSource);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {			
			request.setCharacterEncoding("UTF-8");//para não ter poblemas de acentos
			
			//capitura usuario q fez a operção
			HttpServletRequest request2 = (HttpServletRequest) request;
			HttpSession sessao = request2.getSession();//sessao
			Entidade userLogadoSessao = (Entidade) sessao.getAttribute("userLogadoSessao");//usuario logado
			
			if(userLogadoSessao != null ) {
				UtilFramework.getThreadLocal().set(userLogadoSessao.getEnt_codigo());
			}
			
			sf.getCurrentSession().beginTransaction(); //inicia transação
		
		//antes de executar ação (Request)
		chain.doFilter(request, response); //executa ação no servidor
		//apos executar ação (Resposta)
		
		transactionManager.commit(status);//jdbc
		
		//hibernete
		if(sf.getCurrentSession().getTransaction().isActive()) {
			
			sf.getCurrentSession().flush(); //prepara sql no BD
			sf.getCurrentSession().getTransaction().commit();//comita
		}
		
		//fecha sessão
		if(sf.getCurrentSession().isOpen()) {
			sf.getCurrentSession().close();
		}
		
		//setar resposta
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		} catch (Exception e) {
			// se der erro
			transactionManager.rollback(status);
												
			e.printStackTrace();
			
			//hibernet
			if (sf.getCurrentSession().getTransaction().isActive() ) {
				sf.getCurrentSession().getTransaction().rollback();
			}
			
			if(sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().close();
			}
			
		} finally { //sempre é executado
			
			if(sf.getCurrentSession().isOpen()) {
				if(sf.getCurrentSession().beginTransaction().isActive()) {
					sf.getCurrentSession().flush();
					sf.getCurrentSession().clear();//limpa sessãp
				}
				
				if (sf.getCurrentSession().isOpen()) {
					sf.getCurrentSession().close();
				}
			}
			
		}
	}
	
	
	
	
	

}
