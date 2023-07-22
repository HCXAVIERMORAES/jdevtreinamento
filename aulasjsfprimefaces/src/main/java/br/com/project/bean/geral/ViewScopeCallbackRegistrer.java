package br.com.project.bean.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructViewMapEvent;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;

/**
 * 2ª Classe para trabalhar com scope. Irá interagir coma 1ª (ViewScope) atraves
 * do arqivo de configuração facesconfig. Esta classe servi para fazer a
 * execução dos Runnable da 1ª classe. Toda vez que o scope for creado ou
 * destruido passar por esta classe. UIViewRoot -> raiz de componete
 * A classe deve ser registrada no arquivo facesConfig
 * @author hphoe
 *
 */
@SuppressWarnings("unchecked")
public class ViewScopeCallbackRegistrer implements ViewMapListener, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isListenerForSource(Object source) {

		return source instanceof UIViewRoot; // se for raiz de isntancia de componentes
	}

	// Define controle de contexto do viewScope antes e apos ser destruido
	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		// fica sempre ouvindo os eventos do scope
		if (event instanceof PostConstructViewMapEvent) {
			PostConstructViewMapEvent viewMapEvent = (PostConstructViewMapEvent) event;
			UIViewRoot uiViewRoot = (UIViewRoot) viewMapEvent.getComponent();
			uiViewRoot.getViewMap().put(ViewScope.VIEW_SCOPE_CALLBACKS, new HashMap<String, Runnable>());// inicia uma
																											// lista
																											// nova

		} else if (event instanceof PreDestroyViewMapEvent) {
			PreDestroyViewMapEvent viewMapEvent = (PreDestroyViewMapEvent) event;
			UIViewRoot viewRoot = (UIViewRoot) viewMapEvent.getComponent();
			
			Map<String, Runnable> callbacks = (Map<String, Runnable>) viewRoot.getViewMap()
					.get(ViewScope.VIEW_SCOPE_CALLBACKS);

			if (callbacks != null) {// se existir obj para serem destruidos
				for (Runnable c : callbacks.values()) { // pega obj (c) e todos os valores
					c.run(); // executa
				}
				callbacks.clear(); // limpa toda a lista de Map de componente
			}
		}

	}

}
