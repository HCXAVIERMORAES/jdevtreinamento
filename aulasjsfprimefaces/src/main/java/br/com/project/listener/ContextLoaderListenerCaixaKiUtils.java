package br.com.project.listener;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Classe que prove todo as informações do ambiente de execução do spring. 
 * è unica para todo o sistema (@ApplicationScoped).
 * @author hphoe
 *
 */

@ApplicationScoped
public class ContextLoaderListenerCaixaKiUtils extends ContextLoaderListener implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static WebApplicationContext getWac() {
		
		return WebApplicationContextUtils.getWebApplicationContext(
				getCurrentWebApplicationContext().getServletContext());
	}
	
	public static Object getBean(String idNomeBean) {
		
		return getWac().getBean(idNomeBean);
	}
	
	public static Object getBean (Class<?> classe) {
		
		return getWac().getBean(classe);
	}

}
