package nelsys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import nelsys.modelo.Pessoa;
import nelsys.repository.GrupoRepository;
import nelsys.repository.PessoaRepository;
import nelsys.repository.ProdutoRepository;

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
	public String teste(String nome,ModelMap map){
		System.out.println(nome);
		Pessoa p = pessoaRepository.findById(nome);
		map.put("pessoa", p);
		return "resultado/representantefuncionario";
		
	}
	@RequestMapping("grupo")
	public String grupo(ModelMap map){
		map.put("grupos", grupoRepository.lista());
		return "resultado/grupos";
	}
	@RequestMapping("grupobonus")
	public String grupobonus(ModelMap map){
		map.put("grupos", grupoRepository.lista());
		return "resultado/gruposelect";
	}
	@RequestMapping("produtos")
	public String produtos(ModelMap map,String id) throws SQLException{
		map.put("produtos", produtoRepository.listaporgrupo(id));
		return "resultado/tabelaproduto";
	}
}
