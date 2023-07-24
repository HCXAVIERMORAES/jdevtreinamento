package br.com.project.report.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import br.com.project.util.all.BeanViewAbstract;

/**
 * Classe responsavel por injetar dados na classe ReportUtil, que gerar os relatorios.
 * Ela recebe os dados e repassa ao ReportUtil
 * @author hphoe
 *
 */

@Component
public abstract class BeanReportView extends BeanViewAbstract{

	private static final long serialVersionUID = 1L;
	
	protected StreamedContent arquivoReport;
	protected int tipoRelatorio;
	protected List<?> listDataBeanCollectionReport;
	protected HashMap<Object, Object> parametrosRelatorio;
	protected String nomeRelatorioJasper = "default";
	protected String nomeRelatrioSaida =  "default";
	
	@Resource //para inje��o de dependencia tbm pode-se usar o Autowired
	private ReportUtil reportUtil;
	
	//construtor inica-se com lista de parametro
	public BeanReportView() {
		parametrosRelatorio = new HashMap<Object, Object>();
		listDataBeanCollectionReport = new ArrayList<>();
		
	}
	
	//retorno para inje��o de dependencia
	public ReportUtil getReportUtil() {
		return reportUtil;
	}
	
	public void setReportUtil(ReportUtil reportUtil) {
		this.reportUtil = reportUtil;
	}
	
	//metodo que retorna arquivo para web
	public StreamedContent getArquivoReport() throws Exception {
		return getReportUtil().geraRelatorio(getListDataBeanCollectionReport(),
				getParametrosRelatorio(), getNomeRelatorioJasper(), 
				getNomeRelatrioSaida(), getTipoRelatorio());
	}
	
	
//set e get
	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public List<?> getListDataBeanCollectionReport() {
		return listDataBeanCollectionReport;
	}

	public void setListDataBeanCollectionReport(List<?> listDataBeanCollectionReport) {
		this.listDataBeanCollectionReport = listDataBeanCollectionReport;
	}

	public HashMap<Object, Object> getParametrosRelatorio() {
		return parametrosRelatorio;
	}

	public void setParametrosRelatorio(HashMap<Object, Object> parametrosRelatorio) {
		this.parametrosRelatorio = parametrosRelatorio;
	}

	public String getNomeRelatorioJasper() {
		return nomeRelatorioJasper;
	}

	public void setNomeRelatorioJasper(String nomeRelatorioJasper) {
		
		if(nomeRelatorioJasper == null || nomeRelatorioJasper.isEmpty()) {
			nomeRelatorioJasper ="default";
		}
		
		this.nomeRelatorioJasper = nomeRelatorioJasper;
	}

	public String getNomeRelatrioSaida() {
		return nomeRelatrioSaida;
	}

	public void setNomeRelatrioSaida(String nomeRelatrioSaida) {
		if(nomeRelatrioSaida == null || nomeRelatrioSaida.isEmpty()) {
			nomeRelatrioSaida = "default";
		}
		this.nomeRelatrioSaida = nomeRelatrioSaida;
	}	
	
	
}
