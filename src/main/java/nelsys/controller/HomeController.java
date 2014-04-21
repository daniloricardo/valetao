package nelsys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import nelsys.modelo.BonusProduto;
import nelsys.modelo.Regra;
import nelsys.modelo.TabelaComissao;
import nelsys.repository.BonusProdutoRepository;
import nelsys.repository.EmpresaRepository;
import nelsys.repository.FuncaoRepository;
import nelsys.repository.GrupoRepository;
import nelsys.repository.RegraRepository;
import nelsys.repository.TabelaComissaoRepository;
import nelsys.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	FuncaoRepository funcaoRepository;
	@Autowired
	GrupoRepository grupoRepository;
	@Autowired
	RegraRepository regraRepository;
	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	TabelaComissaoRepository tabelaComissaoRepository;
	@Autowired
	BonusProdutoRepository bonusProdutoRepository;
	
	@RequestMapping("/")
	public String index(ModelMap map){
		map.put("empresas", empresaRepository.findAll());
		return "home";
	}
	@RequestMapping("login")
	public String login(){
		return "login";
	}
	@RequestMapping("regra")
	public String regra(ModelMap map){
		map.put("funcoes", funcaoRepository.lista());
		return "regra";
	}
	
	@RequestMapping("criasenha")
	public String criasenha(ModelMap map, HttpServletRequest request) throws SQLException{
		map.put("textoBotao", "Verificar");
		String nome = request.getParameter("nome");
		String senha = request.getParameter("senha");
		if(usuarioRepository.verifica(nome)){
			map.put("resultado", "Ok, Usuário Existe");
			map.put("login", nome);
			map.put("textoBotao", "Cadastrar Senha");
			System.out.println("senha: "+senha);
			System.out.println("nome: "+nome);
			if(senha != null && senha != "" && senha.length()>=6){
				usuarioRepository.criaTabelaUsuario();
				if(usuarioRepository.criasenha(nome, senha)){
					map.put("resultado", "Senha Criada com sucesso");
				}
				else{
					map.put("resultado", "A senha não foi criada ou já existe uma senha para esse usuário");
				}
			}
			else {
				map.put("resultado", "A Senha não pode ficar em branco deve ter no mínimo 6 caracteres");	
			}
			return "criasenha";
		}
		else{
		map.put("resultado", "Ops, Usuário Não Existe");
		return "criasenha";
		}
	}
	@RequestMapping("gravaregra")
	public String gravaregra(HttpServletRequest request,ModelMap map) throws SQLException{
		System.out.println("grava regra....");
		String idfuncao = request.getParameter("nmfuncao");
		System.out.println("idfuncao: "+idfuncao);
		String[] comissao = request.getParameterValues("comissao");
		System.out.println(comissao.length);
		int tamanho = comissao.length;
		List<String> comissaocomvalor = new ArrayList<String>();
		for(int i=0;i<tamanho;i++){
			if((comissao[i] != null) &&(comissao[i] != "") && (comissao[i] != "0")){
				comissaocomvalor.add(comissao[i]+"-"+i);
			}
		}
		List<Regra> listaregra = new ArrayList<Regra>();
		Regra regra;
		if(comissaocomvalor.size()>0){
			
			for(String s : comissaocomvalor){
				regra = new Regra();
				String[] quebra = s.split("-");
				int indice = Integer.parseInt(quebra[1]);
				regra.setNmcampo(funcaoRepository.findById(idfuncao).getNmfuncao());
				regra.setIdgrupogrupo(grupoRepository.findByIndice(indice).getIdgrupoproduto());
				regra.setNmgrupoproduto(grupoRepository.findByIndice(indice).getNmgrupoproduto());
				regra.setPercentual(new Double(quebra[0]));
				System.out.println(s +" Grupo: "+grupoRepository.findByIndice(indice).getNmgrupoproduto());
				listaregra.add(regra);
			}
			System.out.println("listaregra.size(): "+listaregra.size());
			regraRepository.insere(listaregra);
		}
		map.put("funcoes", funcaoRepository.lista());
		return "regra";
	}
	@RequestMapping("lancamento")
	public void lancamento(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException{
		
		System.out.println("tipo: "+request.getParameter("tipo"));
		System.out.println("idpessoa: "+request.getParameter("idpessoa"));
		System.out.println("data: "+request.getParameter("data"));
		System.out.println("historico: "+request.getParameter("historico"));
		System.out.println("valor: "+request.getParameter("valor"));
		System.out.println("empresa: "+request.getParameter("empresa"));
		String tipo = request.getParameter("tipo");
		String idpessoa = request.getParameter("idpessoa");
		String data = request.getParameter("data");
		String historico = request.getParameter("historico");
		Double valor = Double.parseDouble(request.getParameter("valor"));
		String empresa = request.getParameter("empresa");
		TabelaComissao t = new TabelaComissao();
		t.setTipo(tipo);
		t.setIdvendedor(idpessoa);
		t.setDtemissao(data);
		t.setHistorico(historico);
		if(tipo.equals("D")){
		t.setVlcomissao(valor*-1);
		}
		else {
			t.setVlcomissao(valor);
		}
		t.setCdempresa(empresa);
		tabelaComissaoRepository.insertLancamentoCD(t);
		PrintWriter writer = response.getWriter();
		writer.write("ok");
	}
	@RequestMapping("bonus")
	public String bonus(ModelMap map){
		map.put("funcoes", funcaoRepository.lista());
		return "bonus";
	}
	@RequestMapping("aplicabonus")
	public String aplicabonus(ModelMap map, HttpServletResponse response,HttpServletRequest request) throws SQLException{
		map.put("funcoes", funcaoRepository.lista());
		String idfuncao = request.getParameter("nmfuncao");
		System.out.println("idfuncao: "+idfuncao);
		String idgrupo = request.getParameter("grupo");
		System.out.println("idgrupo: "+idgrupo);
		String[] bonus = request.getParameterValues("bonus_produto");
		System.out.println("tamanho: "+bonus.length);
		BonusProduto b;
		List<BonusProduto> lista = new ArrayList<BonusProduto>();
		//cria a lista
		for(String s : bonus){
			
			if(s.length()>11){
				System.out.println(s);
				String[] partes = s.split("_");
				Double vlbonus = Double.parseDouble(partes[0].replace(",","."));
				String idproduto = partes[1];
				b = new BonusProduto();
				b.setIdfuncao(idfuncao);
				b.setIdgrupo(idgrupo);
				b.setIdproduto(idproduto);
				b.setVlbonus(vlbonus);
				lista.add(b);
			}
		}
		bonusProdutoRepository.insertorupdate(lista);
		return "bonus";
	}
}
