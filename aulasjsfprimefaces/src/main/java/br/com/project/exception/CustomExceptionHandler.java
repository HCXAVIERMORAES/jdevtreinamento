package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;


import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

import br.com.framework.hibernate.session.HibernateUtil;

/**
 * A classe ExceptionHandlerWrapper � responsavel por interceptar os erros do sistema
 * @author hphoe
 *
 */

public class CustomExceptionHandler extends ExceptionHandlerWrapper{
	
	private ExceptionHandler wrapperd;	
	
	//coloca-se o contexto do JSF
	final FacesContext facesContext = FacesContext.getCurrentInstance();
	
	//obtendo o mapa da requisi��o, do FacesContext
	final Map<String, Object>  requestMap = facesContext.getExternalContext().getRequestMap();
	
	//estado atual da navega��o entre as paginas
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
	
	//construtor que pega o exceptionHandler
	public CustomExceptionHandler(ExceptionHandler execExceptionHandler) {
		this.wrapperd = execExceptionHandler;
	}
	
// sobreescreve o m�todo  ExceptionHandler que retorna a pilha de exce��es
	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd; //retorna a nossa classe de erro
	}
	
	/*sobreecreve o metodo Handle que � responsavel por manipular as exce��es do JSF
	 *metodo que manipula todo o nosso erro
	 * */	
	@Override
		public void handle() throws FacesException {
			//instanciar um objeto do tipo Iterable pois  pode ser uma lista de erros
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		
		//com Iterator usa-se o while
		while(iterator.hasNext()) {//eqto houver dados na lista fassa
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			
			//recuperar a exce��o do contexto
			Throwable exeption = context.getException();
			
			//trabalhando a exce��o
			try {
				//mesmo tratando as exe��es pode aver erros dos erros
				requestMap.put("exceptionMessage", exeption.getMessage());
				
				if(exeption != null && 
						exeption.getMessage() != null &&
						exeption.getMessage().indexOf("ConstraintViolationException") != -1) {
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_WARN, "Registro n�o pode ser removido por"
						+" estar associado.", ""));
					
				} else if(exeption != null &&
						exeption.getMessage() != null &&
						exeption.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1) {
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_ERROR,"Registro foi atualizado ou exclu�do por outro usu�rio."
							+" Consulte novamente.", ""));
					
				} else {
					// Avisa o usu�rio do erro
					FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage
							(FacesMessage.SEVERITY_FATAL,"O sistema se recuperou de um erro inesperado.",""));

					// Tranquiliza o usu�rio para que ele continue usando o sistema
					FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage
							(FacesMessage.SEVERITY_INFO,"Voc� pode continuar usando o sistema normalmente!",""));
					
					FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage
							(FacesMessage.SEVERITY_FATAL,"O erro foi causado por:\n"+exeption.getMessage(), ""));
					
					//primeFaces
					//esse alert apenas � exibio se a pagina n�o redirecionar
					RequestContext.getCurrentInstance().execute(
							"alert('O sistema se recuperou de um erro inesperado.')"); 
					
					RequestContext.getCurrentInstance().showMessageInDialog(
							 new FacesMessage(FacesMessage.SEVERITY_INFO, 
									 "Erro", "O sistema se recuperou de um erro inesperado.."));
					
					//Redireciona para pagina de erro
					navigationHandler.handleNavigation(facesContext, 
							null, "/error/error.jsf?faces-redirect=true&expired=true");
				}//fim do else
				
				//Renderiza  a pagina de erro e exibe mensagem
				facesContext.renderResponse(); //fim da resposta
				
				
			} finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if (sf.getCurrentSession().getTransaction().isActive()) {//se � ua trans�o ativa
					sf.getCurrentSession().getTransaction().rollback();					
				}
				//imprimir no console
				exeption.printStackTrace();
				
				iterator.remove();//remove o  objeto de exe��o trabalhado				
			}//fim finally			
		}//fim while
		
		//apos mmanipular todos os erros, finaliza-se a manipula��o
		getWrapped().handle();//finalizar
		
		}//fim handle
	
	

}
