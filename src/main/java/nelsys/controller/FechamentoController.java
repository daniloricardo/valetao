package nelsys.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nelsys.modelo.TabelaComissao;
import nelsys.modelo.TabelaComissaoView;
import nelsys.repository.FechamentoRepository;
import nelsys.repository.FuncaoRepository;
import nelsys.repository.PessoaRepository;
import nelsys.repository.TabelaComissaoRepository;
import nelsys.repository.UsuarioRepository;

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
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@RequestMapping("fechamento")
	@ResponseBody
	public String fechamento(HttpServletRequest request) throws SQLException{
		String idpessoa =request.getParameter("idpessoa");
		String dataoriginal = request.getParameter("data");
		String data = converte(request.getParameter("data"));
		String idusuario = usuarioRepository.pegaId(request.getRemoteUser(),"id");
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
		List<TabelaComissaoView> lista =tabelaComissaoRepository.listaporvendedorView2(idpessoa, data,nmfuncao);
		int tamanho = lista.size();
		if(tamanho>0){
		int idfechamento  = fechamentoRepository.fecha(idpessoa,dataoriginal,idusuario);
		List<TabelaComissaoView> lista2 = new ArrayList<TabelaComissaoView>();
		for(TabelaComissaoView t : lista){
			t.setIdfechamento(idfechamento+"");
			t.setIdvendedor(idpessoa);
			t.setNmvendedor(nmvendedor);
			t.setDtemissao(converte(t.getDtemissao()));
			lista2.add(t);
		}
			fechamentoRepository.executa(lista2);
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
