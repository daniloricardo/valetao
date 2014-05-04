package nelsys.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import nelsys.modelo.Configuracao;
import nelsys.repository.ConfiguracaoRepository;
import nelsys.repository.GrupoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfiguracaoController {
	
	@Autowired
	ConfiguracaoRepository configuracaoRepository;
	@Autowired
	GrupoRepository grupoRepository;

	@RequestMapping("configuracao")
	public String configuracao(ModelMap map) throws SQLException{
		map.put("grupos", grupoRepository.lista());
		map.put("datacorte", configuracaoRepository.datacorte());
		map.put("grupoatual", configuracaoRepository.grupoatual());
		return "configuracao";
	}
	@RequestMapping("gravaconfiguracao")
	public String gravaconfiguracao(HttpServletRequest request) throws SQLException{
		
		String data = request.getParameter("datacorte");
		String idgrupo = request.getParameter("grupo");
		System.out.println(data);
		System.out.println("grupo: "+idgrupo);
		System.out.println("usuario: "+ request.getRemoteUser() );
		Configuracao c = new Configuracao();
		c.setUsuario(request.getRemoteUser());
		if(idgrupo != null && !idgrupo.equals("")){
		c.setNmconfiguracao("grupopercentual");
		c.setVlconfiguracao(idgrupo);
		configuracaoRepository.insertorupdate(c);
		
		}
		
		if(data != null && !data.equals("")){
			c.setNmconfiguracao("datacorte");
			c.setVlconfiguracao(data);
		configuracaoRepository.insertorupdate(c);
		}
		return "home";
	}
	public static String converte(String data){
		return data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2);
	}
}
