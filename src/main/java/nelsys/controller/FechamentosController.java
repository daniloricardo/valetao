package nelsys.controller;

import java.sql.SQLException;

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
		map.put("tabela", fechamentoRepository.listaFechados(id));
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
