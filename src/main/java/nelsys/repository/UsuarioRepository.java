package nelsys.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

	@Autowired
	DataSource dataSource;
	
	public boolean verifica(String login) throws SQLException{
		Statement statement = dataSource.getConnection().createStatement();
		statement.executeQuery("select * from Usuario where NmLogin = '"+login+"'");
		ResultSet rs = statement.getResultSet();
		return rs.next();
		
	}
	public boolean criasenha(String login,String senha) throws SQLException{
		
		Statement statement = dataSource.getConnection().createStatement();
		statement.executeQuery("select * from users where username = '"+login+"'");
		ResultSet rs = statement.getResultSet();
		if(!rs.next()){
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String senhaMD5 = encoder.encodePassword(senha, null);
		String idUser = pegaId(login,"id");
		String insert2 =
				"insert into users (username,password,authority,idexterno) values ('"+login+"','"+senhaMD5+"','ROLE_ADMIN','"+idUser+"')";
		PreparedStatement preparedStatement2 = dataSource.getConnection()
				.prepareStatement(insert2);
		preparedStatement2.execute();
		preparedStatement2.close();
		statement.close();
		rs.close();
		dataSource.getConnection().close();
		return true;
		}
		else{
			return false;
		}
	}
	public void criaTabelaUsuario() throws SQLException{
		String insert = "IF object_id('users') IS  NULL BEGIN " +
				"create table users( "+
" username nvarchar(255) not null, "+
" password nvarchar(255) not null," +
" authority nvarchar(255) not null) END";
		PreparedStatement preparedStatement = dataSource.getConnection()
				.prepareStatement(insert);
		preparedStatement.execute();
	}
	public String pegaId(String login,String dado) throws SQLException{
		Statement statement = dataSource.getConnection().createStatement();
		if(dado.equals("id")){
		statement.executeQuery("select * from Usuario where NmLogin = '"+login+"'");
		}
		else{
			statement.executeQuery("select * from Usuario where idusuario = '"+login+"'");
		}
		ResultSet rs = statement.getResultSet();
		String id="";
		String nmusuario = "";
		while(rs.next()){
			id = rs.getString("idusuario");
			nmusuario = rs.getString("nmlogin");
		}
		statement.close();
		rs.close();
		dataSource.getConnection().close();
		if(dado.equals("id")){
		return id;
		}
		else{
			return nmusuario;
		}
	}
}
