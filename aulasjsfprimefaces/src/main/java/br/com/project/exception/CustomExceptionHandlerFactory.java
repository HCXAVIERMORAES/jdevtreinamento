package br.com.project.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Classe que � uma fabrica de exce��es, ela cria uma objeto CustomExceptionHandler
 * �e declarado no arquivo de configura��es do projeto.
 * @author hphoe
 *
 */

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory{
	
	private ExceptionHandlerFactory parent;
	
	//construtor
	public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent	) {
		this.parent = parent;
	}
	
	//
	@Override
	public ExceptionHandler getExceptionHandler() {
		
		ExceptionHandler hander = new CustomExceptionHandler(parent.getExceptionHandler());
		return hander;// objeto de mesnsagem
	}

}
