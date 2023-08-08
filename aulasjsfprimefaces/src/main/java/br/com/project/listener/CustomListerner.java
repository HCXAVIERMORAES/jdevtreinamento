package br.com.project.listener;

import java.io.Serializable;

import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Service;

import br.com.framework.utils.UtilFramework;
import br.com.project.model.classes.Entidade;
import br.com.project.model.classes.InformacaoRevisao;

//Deve ser amarrado a classe InformacaoRevisao

@Service
public class CustomListerner implements RevisionListener, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public void newRevision(Object revisionEntity) {
		
		InformacaoRevisao informacaoRevisao = (InformacaoRevisao) revisionEntity;
		
		//pegando id do usuario
		Long codUser = UtilFramework.getThreadLocal().get();
		
		Entidade entidade = new Entidade();
		if(codUser != null && codUser != 0L) {
			entidade.setEnt_codigo(codUser);
			informacaoRevisao.setEntidade(entidade);
		}
		
	}

}
