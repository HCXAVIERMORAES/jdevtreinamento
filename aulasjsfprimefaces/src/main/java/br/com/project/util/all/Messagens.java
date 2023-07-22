package br.com.project.util.all;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Classe responsavelpor mostrar as mensagem da aplicação. FacesContext -> é a
 * classe que tem acesso a todas as informações do jsf do projeto. Atributo na
 * pagina jsf é chamado de msg (variavel)
 * 
 * @author hphoe
 *
 */

public abstract class Messagens extends FacesContext implements Serializable {

	private static final long serialVersionUID = 1L;

	// construtor padrão
	public Messagens() {
	}

	// metodo para retornar o FacesContext
	public static FacesContext getFacesContext() {

		return FacesContext.getCurrentInstance();
	}

	// metodo para definir se o FacesContext é valido
	private static boolean facesContextValido() {
		return getFacesContext() != null;
	}

	// metodo generico que recebe a mensagem como parametro
	public static void msg(String msg) {
		if (facesContextValido()) {
			getFacesContext().addMessage("msg", new FacesMessage(msg));
		}
	}

	// metodo que retorna mesagem do banco
	public static void responseOperation(EstatusPercistencia estatusPercistencia) {
		if (estatusPercistencia != null
				&& estatusPercistencia.equals(EstatusPercistencia.SUCESSO)) {// se receber
																			// mensagem de
																		   // sucesso do BD
			sucesso(); // reutilização de codigo
		} else if (estatusPercistencia != null 
				&& estatusPercistencia.equals(EstatusPercistencia.OBJETO_REFERENCIADO)) {//se receber mesagem 
																						//derestrição do BD
						 																						 
			msgSeverityFata(EstatusPercistencia.OBJETO_REFERENCIADO.toString());
			
		} else {
			erroNaOperacao();
		}
	}

	// metodo para passar messagem direto na operação usando a classe Constante
	public static void sucesso() {
		if (facesContextValido()) {
			msgSeverityInfor(Constante.OPERACAO_REALIZADA_COM_SUCESSO);
		}
	}

	// metodo para passar messagem direto na operação usando a classe Constante
	public static void erroNaOperacao() {
		if (facesContextValido()) {
			msgSeverityFata(Constante.ERRO_NA_OPERACAO); // unindo a classe constate
		}
	}

	// mensagem de warn
	public static void msgSeverityWarn(String msg) {

		if (facesContextValido()) {// 1ª validar se FacesContext é valiso
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg)); // se for,
																											// adicionar
																											// mensagem
		}
	}

	// mensagem d fatal
	public static void msgSeverityFata(String msg) {

		if (facesContextValido()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg));
		}
	}

	// mensagem d Error
	public static void msgSeverityError(String msg) {

		if (facesContextValido()) {// 1ª validar se FacesContext é valiso
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	// mensagem d Info
	public static void msgSeverityInfor(String msg) {

		if (facesContextValido()) {// 1ª validar se FacesContext é valiso
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
		}
	}

}
