package nelsys.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nelsys.modelo.TabelaComissao;
import nelsys.modelo.TabelaComissaoView;
import nelsys.repository.FuncaoRepository;
import nelsys.repository.PessoaRepository;
import nelsys.repository.TabelaComissaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProcessaController {
	
	@Autowired
	TabelaComissaoRepository tabelaComissaoRepository;
	@Autowired
	PessoaRepository pessoaRepository;
	@Autowired
	FuncaoRepository funcaoRepository;
	@RequestMapping("processa")
	public String processa(ModelMap map,HttpServletRequest request) throws SQLException{
		String idpessoa = request.getParameter("idpessoa");
		
		String data = request.getParameter("data");
		
		
		String nmfuncao = funcaoRepository
				.findById(pessoaRepository.idfuncaoporidpessoa(idpessoa)).getNmfuncao();
		List<TabelaComissaoView> lista = tabelaComissaoRepository.listaporvendedorView2(idpessoa, converte(data),nmfuncao);
		map.put("tabela",lista);
		Double totalComissao = new Double(0);
		Double totalBase = new Double(0);
		if(lista.size()>0){
		for(TabelaComissaoView t : lista ){
			totalComissao += (t.getVlcomissao());
			totalBase += (t.getVlitem());
		}
		}
		map.put("total", totalComissao);
		map.put("totalbase", totalBase);
		return "resultado/tabelacomissao";
	}
	public static String converte(String data){
		return data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2);
	}
}
