package nelsys.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import nelsys.modelo.Pessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaRepository {

	@Autowired
	EntityManager entityManager;
	@Autowired
	DataSource dataSource;
	
	
	@SuppressWarnings("unchecked")
	public List<Pessoa> findByLikeName(String nmpessoa){
		nmpessoa = "%"+nmpessoa+"%";
		return entityManager.createQuery("from Pessoa p where p.nmpessoa like :nmpessoa and" +
				" ( p.idpessoa in (select idpessoafuncionario from Funcionario ) or" +
				" p.idpessoa in (select idpessoarepresentante from Representante ) ) ")
				.setParameter("nmpessoa", nmpessoa)
				.getResultList();
	}
	public Pessoa findById(String idpessoa){
		return (Pessoa) entityManager.createQuery("from Pessoa p where p.idpessoa = :idpessoa ")
				.setParameter("idpessoa", idpessoa)
				.getSingleResult();
	}
	public String idfuncaoporidpessoa(String idpessoa) throws SQLException{
		Statement statement = dataSource.getConnection().createStatement();
		String categoria = "";
		String idfuncao = "";
		String query = ""+
				" select * from PessoaCategoria where IdPessoa = '"+idpessoa+"' "+
				" and (IdCategoria = '0000000004' or IdCategoria = '000000000R') ";
		ResultSet rs = statement.executeQuery(query);
		while(rs.next()){
			categoria = rs.getString("IdCategoria");
		}
		if(categoria != ""){
			if(categoria.equals("0000000004")){
				query = ""+
						"select * from Representante where IdPessoaRepresentante = '"+idpessoa+"'";
				rs = statement.executeQuery(query);
				while(rs.next()){
					idfuncao = rs.getString("idfuncao");
				}
			}
			if(categoria.equals("000000000R")){
				query = ""+
						"select * from Funcionario where IdPessoaFuncionario = '"+idpessoa+"'";
				rs = statement.executeQuery(query);
				while(rs.next()){
					idfuncao = rs.getString("idfuncao");
				}
			}
			
		}
		return idfuncao;
	}
}
