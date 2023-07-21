package br.com.project.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classe que atuar� nos campos de pesquisa da aplica��o, de forma generica, logoo abstract
 * @Target(value = ElementType.FIELD) -> Define onde vai a anota��o.
 * @Retention(value = RetentionPolicy.RUNTIME) -> Para maquina virtual
 * @author hphoe
 *
 */

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public abstract @interface IdentificaCampoPesquisa {
	
	//Op��es que ser�o fornecidas pela anota��o
	String descricaoCampo(); //descri��o do campo para a tela
	String campoConsulta(); //campo do banco
	int principal() default 10000; //posi��o que ira aparecer no combo
	

}
