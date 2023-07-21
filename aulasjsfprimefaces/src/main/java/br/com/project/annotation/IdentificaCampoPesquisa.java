package br.com.project.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classe que atuará nos campos de pesquisa da aplicação, de forma generica, logoo abstract
 * @Target(value = ElementType.FIELD) -> Define onde vai a anotação.
 * @Retention(value = RetentionPolicy.RUNTIME) -> Para maquina virtual
 * @author hphoe
 *
 */

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public abstract @interface IdentificaCampoPesquisa {
	
	//Opções que serão fornecidas pela anotação
	String descricaoCampo(); //descrição do campo para a tela
	String campoConsulta(); //campo do banco
	int principal() default 10000; //posição que ira aparecer no combo
	

}
