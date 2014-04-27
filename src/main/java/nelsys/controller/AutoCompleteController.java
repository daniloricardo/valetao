package nelsys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nelsys.modelo.Funcao;
import nelsys.modelo.GrupoProduto;
import nelsys.modelo.Pessoa;
import nelsys.modelo.Produto;
import nelsys.repository.FuncaoRepository;
import nelsys.repository.GrupoRepository;
import nelsys.repository.PessoaRepository;
import nelsys.repository.ProdutoRepository;
import nelsys.repository.RegraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AutoCompleteController {
	
	@Autowired
	PessoaRepository pessoaRepository;
	@Autowired
	GrupoRepository grupoRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	FuncaoRepository funcaoRepository;
	@Autowired
	RegraRepository regraRepository;
	
	@RequestMapping("consultarepresentante")
	public void autocompleterepresentante(String nome,HttpServletResponse response) throws IOException{
		PrintWriter writer = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		for(Pessoa p : pessoaRepository.findByLikeName(nome)){
			writer.write("<span class='dados' onclick=selecionarepfunc('"+p.getIdpessoa()+"')>"+p.getNmpessoa()+"</span><br/>");	
		}
	}
	@RequestMapping("resultadofiltrorepresentante")
	public String teste(String nome,ModelMap map) throws SQLException{
		System.out.println(nome);
		Pessoa p = null;
		p = pessoaRepository.findById(nome);
		p.setNmpessoa(p.getNmpessoa());
		String idfuncao =pessoaRepository.idfuncaoporidpessoa(p.getIdpessoa());
		
		Funcao f = funcaoRepository.findById(idfuncao);
		if(f != null){
		map.put("funcao", f);
		}
		
		map.put("pessoa", p);
		
		return "resultado/representantefuncionario";
		
	}
	@RequestMapping("grupo")
	public String grupo(ModelMap map, HttpServletRequest request) throws SQLException{
		String idfuncao = request.getParameter("idfuncao");
		Funcao f = funcaoRepository.findById(idfuncao);
		for(GrupoProduto g : grupoRepository.lista()){
			g.setPercentual(
				regraRepository.percentualPorGrupoFuncao(f.getNmfuncao(), g.getIdgrupoproduto())	
					);
		}
		map.put("grupos", grupoRepository.lista());
		return "resultado/grupos";
	}
	@RequestMapping("grupobonus")
	public String grupobonus(ModelMap map){
		map.put("grupos", grupoRepository.lista());
		return "resultado/gruposelect";
	}
	@RequestMapping("grupobonusduplica")
	public String grupobonusduplica(ModelMap map, HttpServletRequest request){
		String idfuncao = request.getParameter("idfuncao");
		System.out.println("idfuncao: "+idfuncao);
		map.put("grupos", grupoRepository.lista());
		map.put("funcoes", funcaoRepository.listaDestinos(idfuncao));
		return "resultado/gruposelectduplica";
	}
	@RequestMapping("duplicabonus")
	public String duplicabonus(ModelMap map, HttpServletRequest request) throws SQLException{
		String idfuncaoorigem = request.getParameter("nmfuncao");
		System.out.println("idfuncaoorigem: "+idfuncaoorigem);
		String idgrupo = request.getParameter("grupo");
		System.out.println("GRUPO: "+idgrupo);
		System.out.println("idfuncaoorigem: "+idfuncaoorigem);
		String idfuncaodestino = request.getParameter("funcaodestino");
		System.out.println("idfuncaodestino: "+idfuncaodestino);
		regraRepository.duplicar(idfuncaoorigem, idfuncaodestino,idgrupo);
		map.put("funcoes", funcaoRepository.lista());
		return "bonus";
	}
	@RequestMapping("produtos")
	public String produtos(ModelMap map,String id,HttpServletRequest request) throws SQLException{
		String idfuncao = request.getParameter("idfuncao");
		System.out.println(idfuncao);
		List<Produto> lista = new ArrayList<Produto>();
		for(Produto p : produtoRepository.listaporgrupo(id)){
			p.setVlbonus(
				regraRepository.
			percentualPorGrupoFuncaoProduto(idfuncao,id,p.getIdproduto()));
			lista.add(p);
		}
		map.put("produtos", lista);
		return "resultado/tabelaproduto";
	}
}
