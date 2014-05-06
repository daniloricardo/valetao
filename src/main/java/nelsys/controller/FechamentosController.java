package nelsys.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nelsys.modelo.TabelaComissao;
import nelsys.modelo.TabelaComissaoFechamento;
import nelsys.repository.FechamentoRepository;
import nelsys.repository.PessoaRepository;
import nelsys.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FechamentosController {
	
	@Autowired
	FechamentoRepository fechamentoRepository;
	@Autowired
	PessoaRepository pessoaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	@RequestMapping("fechamentos")
	public String fechamentos() throws SQLException{
		return "fechamentos";
	}
	@RequestMapping("fechamentosfiltro")
	public String fechamentosfiltro(ModelMap map, HttpServletRequest request) throws SQLException{
		String data = request.getParameter("data");
		String tipo = request.getParameter("tipo");
		System.out.println("ddata: "+data+ " tipo: "+tipo);
		
		List<TabelaComissaoFechamento> lista = fechamentoRepository.listapordata(data,tipo);
		for(TabelaComissaoFechamento t : lista ){
			System.out.println(t.getIdvendedor());
			t.setIdvendedor(pessoaRepository.findById(t.getIdvendedor()).getNmpessoa());
			String usuario = usuarioRepository.pegaId(t.getIdusuariofechamento(), "");
			t.setNmusuario(usuario);
		}
		map.put("fechamentos",lista );
		return "resultado/fechamentos";
		
		
	}
	@RequestMapping("imprime/{id}")
	public String imprime(ModelMap map,@PathVariable String id,HttpServletRequest request) throws SQLException{
		System.out.println(id);
		TabelaComissaoFechamento fechcomissao = fechamentoRepository.getFechamento(id);
		System.out.println("idVendedor: "+fechcomissao.getIdvendedor());
		fechcomissao.setIdvendedor(pessoaRepository.findById(fechcomissao.getIdvendedor()).getNmpessoa());
		String usuario = usuarioRepository.pegaId(fechcomissao.getIdusuariofechamento(), "");
		fechcomissao.setNmusuario(usuario);
		double totalcomissao = new Double(0);
		double totalbase = new Double(0);
		map.put("tabela", fechamentoRepository.listaFechados(id));
		map.put("fechamento", fechcomissao);
		for(TabelaComissao t : fechamentoRepository.listaFechados(id)){
			totalcomissao += ( t.getVlcomissao());
			totalbase += t.getVlitem();
		}
		map.put("total", totalcomissao);
		map.put("totalbase", totalbase);
		return "imprime";
	}
	@RequestMapping("reabre/{id}")
	public String reabre(ModelMap map,@PathVariable String id) throws SQLException{
		System.out.println(id);
		fechamentoRepository.deletafechamento(id);
		map.put("tabela", fechamentoRepository.listaFechados(id));
		return "redirect:/fechamentos";
	}
}
