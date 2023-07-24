package br.com.project.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Classe de geração de relatorio (2ª classe).
 * Retorna um streamedContent(primeFaces) é um tipo de dados do primeFaces para poder retornar o download
 * dele pra web para poder baixar o dado 
 * @author hphoe
 *
 */
@SuppressWarnings("deprecation")
@Component
public class ReportUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//variaveis uteis na classe
	private static final String UNDERLINE = "_"; //Com ela se for mudar o simbolo é so mudar o valor sem prejudicar o codigo
	private static final String PONTO = ".";
	private static final String FOLDER_RELATORIO = "/relatorios";//pasta onde vai ficar os relatórios
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR"; //Carrega os caminhos dos subrelatorios
	private static final String EXTENSION_ODS = "ods"; //extensão usada na pasta do relatorio
	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_HTML = "html";
	private static final String EXTENSION_PDF = "pdf";
	private String SEPARATOR = File.separator; //tras o separador correto para  uso (/ ou \)
	private static final int RELATORIO_PDF = 1; //idica o tipode relatório
	private static final int RELATORIO_EXCEL = 2;
	private static final int RELATORIO_HTML = 3;
	private static final int RELATORIO_PLANILHA_OPEN_OFFICE = 4;
	private StreamedContent arquivoRetono = null; //retorno pra web
	private String caminhoArquivoRelatorio = null;
	private JRExporter tipoArquivoExportado = null; // Do jasper -> para gerar relatorios
	private String extensaoArquivoExportado = "";
	private String caminhoSubreport_dir = "";
	private File arquivoGerado = null;
	
	//metodo que gera relatorio, recebe uma lista generica. servindo para todos os relatorios
	public StreamedContent geraRelatorio(List<?> listDataBeanColletionReport,
			HashMap parametrosRelatorio, String nomeRelatorioJasper,
			String nomeRelatorioSaida, int tipoRelatorio) throws Exception {
		/*pacote jasper. Cria lista de CollectionDataSource de beans que carregam 
		 * os dados para o relatório*/
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDataBeanColletionReport);
		
		/**
		 * Fornece o caminho fisico até a pasta que contem os relatórios compilados .jasper
		 */
		FacesContext context = FacesContext.getCurrentInstance();
		context.responseComplete(); //resposta completa
		ServletContext scontext = (ServletContext) context.getExternalContext().getContext();//pega caminho do relatorio
		
		String caminhoRelatorio = scontext	.getRealPath(FOLDER_RELATORIO); //caminho da pasta de relatorios
		
		/*caminho sendo montado no servidor
		 * Ex.: C:/aplicacao/relatorios/rel_bairro.jasper*/ 
		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper +PONTO + "jasper");
		
		if(caminhoRelatorio == null 
				|| (caminhoRelatorio != null && caminhoRelatorio.isEmpty())
				|| !file.exists()) {
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIO).getPath();//retorna caminho rodando dentro do servidor
			SEPARATOR = "";			
		}
		
		/*caminho para imagem*/
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);
		
		/*caminho completo até o relatorio compilado indicado*/
		String caminhoArquivoJasper = caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + PONTO + "jasper";
		
		/*carregamento do relatorio indicado*/
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);
		
		/*Seta parametro SUBREPORT_DIR como caminho fisico para sub-reports*/
		caminhoSubreport_dir = caminhoRelatorio + SEPARATOR;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubreport_dir);
		
		/*carrega o arquivo compilado para memoria*/
		JasperPrint impressoraJasper = JasperFillManager.
				fillReport(relatorioJasper, parametrosRelatorio, jrbcds); //imprimir relatorio
		
		/*formato de exportação do relatório*/
		switch (tipoRelatorio) {
		case RELATORIO_PDF:
			tipoArquivoExportado = new JRPdfExporter();
			extensaoArquivoExportado = EXTENSION_PDF;			
			break;
		case RELATORIO_HTML:
			tipoArquivoExportado = new JRHtmlExporter();
			extensaoArquivoExportado = EXTENSION_HTML;			
			break;
		case RELATORIO_EXCEL:
			tipoArquivoExportado = new JRXlsExporter();
			extensaoArquivoExportado = EXTENSION_XLS;			
			break;
		case RELATORIO_PLANILHA_OPEN_OFFICE:
			tipoArquivoExportado = new JROdsExporter();
			extensaoArquivoExportado = EXTENSION_ODS;			
			break;
		default:
			tipoArquivoExportado = new JRPdfExporter();
			extensaoArquivoExportado = EXTENSION_PDF;
			break;
		}
		
		/*nome do relatorio de saida*/
		nomeRelatorioSaida += UNDERLINE + DateUtils.getDateAtualReportName();
		
		/*caminho relatório exportado*/
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + PONTO
				+ extensaoArquivoExportado;
		
		/*cria novo file exportado*/
		arquivoGerado = new File(caminhoArquivoRelatorio);
		
		/*Preparar a impressão*/
		tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		
		/*Nome do arqivo fisico  a ser impresso/exportado*/
		tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
		
		/*executa a exportação*/
		tipoArquivoExportado.exportReport();
		
		/*Remove o arquivo do servidor após ser feito o download pelo usuário*/
		arquivoGerado.deleteOnExit();
		
		/*Criar o inputstrean para ser usado pelo prime*/
		InputStream conteudoRelatorio = new FileInputStream(arquivoGerado);
		
		/*faz retorno  para a aplicação*/
		arquivoRetono = new DefaultStreamedContent(conteudoRelatorio,
				"application/" + extensaoArquivoExportado,
				nomeRelatorioSaida + PONTO + extensaoArquivoExportado);		
		
		/*fim*/
		return arquivoRetono;		
		
	}
	
}
