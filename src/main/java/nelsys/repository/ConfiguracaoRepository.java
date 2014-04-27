package nelsys.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import nelsys.modelo.Configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConfiguracaoRepository {

	@Autowired
	DataSource dataSource;
	
	public List<Configuracao> lista() throws SQLException{
		create();
		Statement statement = dataSource.getConnection().createStatement();
		String query = "select * from Nsys_configuracaocomissao";
		ResultSet rs = statement.executeQuery(query);
		List<Configuracao> lista = new ArrayList<Configuracao>();
		Configuracao c;
		while(rs.next()){
			c = new Configuracao();
			c.setNmconfiguracao(rs.getString("nmconfiguracao"));
			c.setVlconfiguracao(rs.getString("vlconfiguracao"));
			c.setUsuario(rs.getString("usuario"));
			lista.add(c);
			
		}
		return lista;
	}
	public Configuracao configuracaoPorNmconfiguracao(String nmconfiguracao) throws SQLException{
		create();
		Statement statement = dataSource.getConnection().createStatement();
		String query = "select * from Nsys_configuracaocomissao where nmconfiguracao = '"+nmconfiguracao+"'";
		ResultSet rs = statement.executeQuery(query);
		Configuracao c = null;
		while(rs.next()){
			c = new Configuracao();
			c.setNmconfiguracao(rs.getString("nmconfiguracao"));
			c.setVlconfiguracao(rs.getString("vlconfiguracao"));
			c.setUsuario(rs.getString("usuario"));
		}
		return c;
	}
	public void create() throws SQLException{
		String query = "  IF object_id('Nsys_configuracaocomissao') IS NULL BEGIN   "+  
				"create table Nsys_configuracaocomissao "+
				"(nmconfiguracao nvarchar(255),vlconfiguracao nvarchar(255), usuario nvarchar(255)) end";
		PreparedStatement pp = dataSource.getConnection().prepareStatement(query);
		pp.execute();
	}
	public void insertorupdate(Configuracao c) throws SQLException{
		create();
		Statement st = dataSource.getConnection().createStatement();
		String queryA = "select * from Nsys_configuracaocomissao "+
		" where nmconfiguracao ='"+c.getNmconfiguracao()+"'";
		ResultSet rs = st.executeQuery(queryA);
		if(rs.next()){
			String update = "update Nsys_configuracaocomissao set vlconfiguracao ='"+c.getVlconfiguracao()+"' "+
					" where nmconfiguracao ='"+c.getNmconfiguracao()+"'";	
			PreparedStatement pp1 = dataSource.getConnection().prepareStatement(update);
			pp1.execute();
		}
		else {
			String query = "insert into Nsys_configuracaocomissao "+
				"(nmconfiguracao,vlconfiguracao,usuario) "+
				" VALUES "+
				"(?,?,?) ";
		PreparedStatement pp = dataSource.getConnection().prepareStatement(query);
		pp.setString(1, c.getNmconfiguracao());
		pp.setString(2, c.getVlconfiguracao());
		pp.setString(3, c.getUsuario());
		pp.execute();
		}
	}
	
}
