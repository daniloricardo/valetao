package nelsys.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import nelsys.modelo.TabelaComissao;
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
		System.out.println(idpessoa);
		String data = request.getParameter("data");
		System.out.println(data);
		
		String nmfuncao = funcaoRepository
				.findById(pessoaRepository.idfuncaoporidpessoa(idpessoa)).getNmfuncao();
		map.put("tabela",tabelaComissaoRepository.listaporvendedor(idpessoa, converte(data),nmfuncao));
		Double totalComissao = new Double(0);
		for(TabelaComissao t : tabelaComissaoRepository.listaporvendedor(idpessoa, converte(data),nmfuncao) ){
			totalComissao += (t.getVlcomissao()+t.getVladicional());
		}
		map.put("total", totalComissao);
		return "resultado/tabelacomissao";
	}
	public static String converte(String data){
		return data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2);
	}
}
