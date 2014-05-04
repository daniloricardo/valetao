package nelsys.controller;

import java.sql.SQLException;

import nelsys.modelo.TabelaComissao;
import nelsys.modelo.TabelaComissaoFechamento;
import nelsys.repository.FechamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FechamentosController {
	
	@Autowired
	FechamentoRepository fechamentoRepository;

	@RequestMapping("fechamentos")
	public String fechamentos(ModelMap map){
		map.put("fechamentos", fechamentoRepository.lista());
		return "fechamentos";
	}
	@RequestMapping("imprime/{id}")
	public String imprime(ModelMap map,@PathVariable String id) throws SQLException{
		System.out.println(id);
		TabelaComissaoFechamento fechcomissao = fechamentoRepository.getFechamento(id);
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
