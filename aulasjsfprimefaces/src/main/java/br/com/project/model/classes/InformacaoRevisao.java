package br.com.project.model.classes;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import br.com.project.listener.CustomListerner;


/**
 * Tabela de revisão
 * @RevisionEntity tem que falar qual o listener que dispara o evento
 * @author hphoe
 *
 */

@Entity
@Table(name = "revinfo")
@RevisionEntity(CustomListerner.class)
public class InformacaoRevisao extends DefaultRevisionEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//chavee estrangeira
	@ManyToOne
	@ForeignKey(name="entidade_fk") //nome da chave estrangeira
	@JoinColumn(nullable = false, name="entidade")
	private Entidade entidade;
	
	//set e get
	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}
	
	public Entidade getEntidade() {
		return entidade;
	}

}
