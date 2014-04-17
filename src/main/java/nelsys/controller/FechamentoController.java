package nelsys.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nelsys.modelo.TabelaComissao;
import nelsys.repository.FechamentoRepository;
import nelsys.repository.FuncaoRepository;
import nelsys.repository.PessoaRepository;
import nelsys.repository.TabelaComissaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FechamentoController {
	@Autowired
	FuncaoRepository funcaoRepository;
	@Autowired
	TabelaComissaoRepository tabelaComissaoRepository;
	@Autowired
	PessoaRepository pessoaRepository;
	@Autowired
	FechamentoRepository fechamentoRepository;
	
	@RequestMapping("fechamento")
	@ResponseBody
	public String fechamento(HttpServletRequest request) throws SQLException{
		String idpessoa =request.getParameter("idpessoa");
		String dataoriginal = request.getParameter("data");
		String data = converte(request.getParameter("data"));
		String nmfuncao = "";
		String nmvendedor = pessoaRepository
				.findById(idpessoa).getNmpessoa();
		try{
		nmfuncao = funcaoRepository
				.findById(pessoaRepository.idfuncaoporidpessoa(idpessoa)).getNmfuncao();
		}
		catch(Exception e){
			return "O Vendedor deve ter uma Função. Verifique!";
		}
		int tamanho = tabelaComissaoRepository.listaporvendedor(idpessoa, data,nmfuncao).size();
		if(tamanho>0){
		int idfechamento  = fechamentoRepository.fecha(nmvendedor,dataoriginal);
		List<TabelaComissao> lista = new ArrayList<TabelaComissao>();
		for(TabelaComissao t : tabelaComissaoRepository.listaporvendedor(idpessoa, data,nmfuncao)){
			t.setIdfechamento(idfechamento+"");
			t.setIdvendedor(idpessoa);
			t.setVendedor(nmvendedor);
			t.setDtemissao(converte(t.getDtemissao()));
			lista.add(t);
		}
			fechamentoRepository.executa(lista);
			return "Fechamento Realizado com Sucesso!";
			
		}
		else{
			return "Não foram encontrados lançamentos com os parametros informados!";
		}
		
		
	}
	public static String converte(String data){
		return data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2);
	}
}
