package nelsys.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import nelsys.modelo.Configuracao;
import nelsys.repository.ConfiguracaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfiguracaoController {
	
	@Autowired
	ConfiguracaoRepository configuracaoRepository;

	@RequestMapping("configuracao")
	public String configuracao(){
		return "configuracao";
	}
	@RequestMapping("gravaconfiguracao")
	public String gravaconfiguracao(HttpServletRequest request) throws SQLException{
		
		String data = request.getParameter("datacorte");
		System.out.println(data);
		System.out.println("usuario: "+ request.getRemoteUser() );
		Configuracao c = new Configuracao();
		c.setNmconfiguracao("datacorte");
		c.setVlconfiguracao(converte(data));
		c.setUsuario(request.getRemoteUser());
		configuracaoRepository.insertorupdate(c);
		return "home";
	}
	public static String converte(String data){
		return data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2);
	}
}
