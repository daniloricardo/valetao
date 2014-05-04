package nelsys.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import nelsys.modelo.Regra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegraRepository {
	
	@Autowired
	DataSource dataSource;

	public void insere(List<Regra> lista) throws SQLException{
		PreparedStatement preparedStatement = null;
		for(Regra r : lista){
			String insert = "insert into Nsys_RegraComissao "+
		"(nmcampo,idgrupoproduto,nmgrupoproduto,percentual,vladicional,vlbonificacao) "+
					" values "+
		"('"+r.getNmcampo()+"','"+r.getIdgrupogrupo()+"','"+r.getNmgrupoproduto()+"',"+r.getPercentual()
		+","+r.getVladicional()+","+r.getVlbonificacao()+")";
		String update = "update Nsys_RegraComissao set percentual ="+r.getPercentual()+" "+
				"where nmcampo = '"+r.getNmcampo()+"' and idgrupoproduto = '"+r.getIdgrupogrupo()+"'";
			Statement statement = dataSource.getConnection().createStatement();
			ResultSet rs = statement.executeQuery("select * from Nsys_RegraComissao where nmcampo = '"+
			r.getNmcampo()+"' and idgrupoproduto = '"+r.getIdgrupogrupo()+"'");
			if(rs.next()){
				preparedStatement = 
						dataSource.getConnection().prepareStatement(update);
				preparedStatement.execute();
			}
			else {
				preparedStatement = 
						dataSource.getConnection().prepareStatement(insert);
				preparedStatement.execute();
			}
			
			
		}
		preparedStatement.close();
		dataSource.getConnection().close();
		
	}
	public Double percentualPorGrupoFuncao(String nmfuncao,String idgrupo) throws SQLException{
		String query = "select percentual from Nsys_RegraComissao "+
				" where nmcampo = ? and idgrupoproduto = ? ";
		PreparedStatement pp = dataSource.getConnection().prepareStatement(query);
		pp.setString(1, nmfuncao);
		pp.setString(2, idgrupo);
		ResultSet rs = pp.executeQuery();
		Double percentual = new Double(0);
		while(rs.next()){
			percentual = rs.getDouble("percentual");
		}
		pp.close();
		rs.close();
		dataSource.getConnection().close();
		return percentual;
	}
	public Double percentualPorGrupoFuncaoProduto(String idfuncao,String idgrupo,String idproduto) throws SQLException{
		String query = "select vlbonus from Nsys_bonusproduto "+
				" where idfuncao = ? and idgrupo = ? and idproduto = ? ";
		PreparedStatement pp = dataSource.getConnection().prepareStatement(query);
		pp.setString(1, idfuncao);
		pp.setString(2, idgrupo);
		pp.setString(3, idproduto);
		ResultSet rs = pp.executeQuery();
		Double vlbonus = new Double(0);
		while(rs.next()){
			vlbonus = rs.getDouble("vlbonus");
		}
		pp.close();
		dataSource.getConnection().close();
		rs.close();
		return vlbonus;
	}
	public void duplicar(String idfuncaoorigem,String idfuncaodestino,String idgrupo) throws SQLException{
		String query = "select * from Nsys_bonusproduto "+
				" where idfuncao = ? and idgrupo = ? ";
		PreparedStatement pp = dataSource.getConnection().prepareStatement(query);
		pp.setString(1, idfuncaodestino);
		pp.setString(2, idgrupo);
		ResultSet rs = pp.executeQuery();	
		PreparedStatement pp3 = dataSource.getConnection().prepareStatement(query);
		pp3.setString(1, idfuncaodestino);
		pp3.setString(2, idgrupo);
		ResultSet rs2 = pp3.executeQuery();	
		PreparedStatement pp2 = null;
		if(rs.next()){
			System.out.println("sim existe!");
			while(rs2.next()){
				System.out.println("percorrendo");
			String queryB = "update Nsys_bonusproduto "+
					" set vlbonus = ? "+
					" where idfuncao = ? "+
					" and idproduto = ?";
			pp2 = dataSource.getConnection().prepareStatement(queryB);
			pp2.setDouble(1, rs2.getDouble("vlbonus"));
			pp2.setString(2, idfuncaodestino);
			pp2.setString(3, rs2.getString("idproduto"));
			pp2.execute();
		}
		}
		else {
			System.out.println("nao existe");
			String queryC = "insert into Nsys_bonusproduto "+
			"(idfuncao,idgrupo,idproduto,vlbonus) "+
					"select ?,idgrupo,idproduto,vlbonus from Nsys_bonusproduto where idfuncao = ? and idgrupo = ? ";
			pp2 = dataSource.getConnection().prepareStatement(queryC);
			pp2.setString(1, idfuncaodestino);
			pp2.setString(2, idfuncaoorigem);
			pp2.setString(3, idgrupo);
			pp2.execute();
		}
		pp.close();
		pp2.close();
		pp3.close();
		rs.close();
		rs2.close();
		dataSource.getConnection().close();
	}
}
