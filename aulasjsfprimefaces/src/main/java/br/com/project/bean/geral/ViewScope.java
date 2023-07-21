package br.com.project.bean.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

/**
 * 1ª Classe responsavel pelo scope de view do projeto. O scopo será gerenciado pelo spring e não pelo JSF, logo
 * se deve fazer uma implementação propria do scopo de view (os dados permanecem na tela até ser salvo),
 * logo deve ser implementado pelo org.springframework.beans.factory.config.Scope (q é uma interface spring).
 * Deve-se reimplementar cada metodo dela.	
 * @author hphoe
 *
 */
//anotação que retira a lampada amarela da classe
@SuppressWarnings("unchecked")
public class ViewScope implements Scope, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//para fazer o metodo remove primeiro deve-se fazer a variavel a seguir
	public static final String VIEW_SCOPE_CALLBACKS = "ViewScope.callBacks"; //valor definido dentro do spring (encontra-se na documentação)
	
	
	//Retorna a instancia da tela, da sesção (scope)
	@Override
	public Object get(String name, ObjectFactory<?> ObjectFactory) {
		
		Object instance = getViewMap().get(name);
		 if(instance == null) {
			 instance = ObjectFactory.getObject();
			 getViewMap().put(name, instance);//adiciona
			 
		 }
		return instance;
	}

	
	//metodo que carrega o ID que identifica os scope
	@Override
	public String getConversationId() {
		FacesContext facesContext =  FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
		// id da seção e id dos componentes	do jsf	
		return facesRequestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
	}
	
	//metodo de  registro da destruição de scope
	@Override
	public void registerDestructionCallback(String name, Runnable runnable) {
		Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
		
		if(callbacks != null) {
			callbacks.put(VIEW_SCOPE_CALLBACKS, runnable);
		}
		
	}

	//metodo de remoção do scope. Runnable -> executa por baixo dos panos
	
	@Override
	public Object remove(String name) {
		Object instance = getViewMap().remove(name);//remove e retorna objeto removido
		
		if(instance != null) {
			Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			if(callBacks != null) {
				callBacks.remove(name);
			}
		}
		return instance;
	}

	//metodo que resolve referencia dos objetos do scope
	@Override
	public Object resolveContextualObject(String name) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes =	new FacesRequestAttributes(facesContext);
		
		return facesRequestAttributes.resolveReference(name);
	}
	
	/**
	 * 1ª construir um metodo que retorne view e Map
	 * getViewRoot() -> retorna o componente raiz que está associado a essa solicitação (ou seja, request)
	 * getViewMap() -> Retorna um Map que atua como a interface para o armazenamento de dados. é uma lista
	 * @return
	 */
	private Map<String, Object> getViewMap() {
		
		return FacesContext.getCurrentInstance() != null ?
				FacesContext.getCurrentInstance().getViewRoot().getViewMap() : new HashMap<String, Object>();
		
	}

}
